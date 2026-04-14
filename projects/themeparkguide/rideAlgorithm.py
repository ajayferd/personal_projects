import csv
from datetime import datetime
from collections import defaultdict
import json

def load_summary(file_path):
    with open(file_path, "r") as f:
        return json.load(f)
    
def get_wait_at_hour(ride_data, hour):
    hourly = ride_data["hourly_avg"]

    if str(hour) in hourly:
        return hourly[str(hour)]

    # find closest earlier hour
    earlier_hours = sorted(
        [int(h) for h in hourly.keys() if int(h) <= hour]
    )

    if earlier_hours:
        return hourly[str(earlier_hours[-1])]

    # fallback: closest future hour
    later_hours = sorted(
        [int(h) for h in hourly.keys() if int(h) > hour]
    )

    if later_hours:
        return hourly[str(later_hours[0])]

    return None

def get_current_hour(current_time):
    return int(current_time // 60)

def score_optimal(ride, wait, visited):
    if wait is None:
        return float('inf')
    
    revisit_penalty = 15 if ride in visited else 0

    return wait + revisit_penalty

def score_weighted_optimal(ride, wait, visited, weight):
    if wait is None:
        return float('inf')

    weight_bonus = weight * 75
    revisit_penalty = 25 if ride in visited else 0

    # balance wait vs popularity
    return wait - weight_bonus + revisit_penalty

def score_worst(ride, wait, visited):
    if ride in visited or wait is None:
        return float('-inf')
    
    return wait  # higher is worse → we maximize

def score_single_ride_max(target_ride, ride, wait):
    if ride != target_ride or wait is None:
        return float('inf')
    
    return wait

def score_single_ride_min(target_ride, ride, wait):
    if ride != target_ride or wait is None:
        return float('-inf')
    
    return wait

def get_first_available_hour(summary):
    hours = set()

    for data in summary.values():
        hours.update(int(h) for h in data["hourly_avg"].keys())

    return min(hours) if hours else 8


def simulate_day(summary, ride_map, mode, target_ride=None, use_lookahead=False):
    start_hour = get_first_available_hour(summary)
    current_time = start_hour * 60  # start at the first available hour
    end_time = 22 * 60

    # print(f"[DEBUG] Starting simulation: mode={mode}")
    visited = set()
    path = []
    total_experiences = 0
    total_wait_time = 0

    while current_time < end_time:
        hour = get_current_hour(current_time)
        # print(f"\n[DEBUG] Current hour: {hour}")
        best_ride = None
        best_score = None


        unvisited_rides = any(r not in visited for r in summary.keys())
        for ride_id, data in summary.items():
            wait = get_wait_at_hour(data, hour)
            
            if wait is None:
                continue
            
            if unvisited_rides and ride_id in visited:
                continue

            if mode == "optimal":
                score = score_optimal(ride_id, wait, visited)

            elif mode == "weighted_optimal":
                weight = data.get("weight", 0)
                base_score = score_weighted_optimal(ride_id, wait, visited, weight)

                if use_lookahead:
                    future_score = simulate_future(
                        summary,
                        current_time + wait,
                        visited,
                        horizon_minutes=120  # 2 hours
                    )

                    # Blend current + future
                    score = base_score + (future_score * 0.50)
                else:
                    score = base_score

            elif mode == "worst":
                score = score_worst(ride_id, wait, visited)

            elif mode == "single_max":
                score = score_single_ride_max(target_ride, ride_id, wait)

            elif mode == "single_min":
                score = score_single_ride_min(target_ride, ride_id, wait)

            else:
                continue

            if score == float('inf'):
                continue

            if best_score is None:
                best_score = score
                best_ride = ride_id
            else:
                if mode in ["optimal", "weighted_optimal", "single_max"]:
                    if score < best_score:
                        best_score = score
                        best_ride = ride_id
                else:  
                    if score > best_score:
                        best_score = score
                        best_ride = ride_id

        if best_ride is None:
            current_time = (hour + 1) * 60
            continue

        wait = get_wait_at_hour(summary[best_ride], hour)
        total_wait_time += wait

        if wait is None:
            break

        ride_name = ride_map.get(best_ride, "Unknown Ride")
        total_experiences += 1
        # Add to path
        path.append({
            "ride_id": best_ride,
            "ride_name": ride_name,
            "hour": hour,
            "wait": wait,
            "total rides": total_experiences,
            "cumulative wait": total_wait_time
        })

        visited.add(best_ride)

        # Advance time
        current_time += wait + 5  # +5 min buffer
        

    return path

def simulate_single_ride_day(summary, ride_id, mode="max"):
    current_time = 8 * 60
    end_time = 22 * 60

    rides_count = 0
    timeline = []

    while current_time < end_time:
        hour = int(current_time // 60)

        wait = summary[ride_id]["hourly_avg"].get(str(hour))

        if wait is None:
            # skip to next hour if ride unavailable
            current_time = (hour + 1) * 60
            continue

        # For "min", we intentionally choose worst-case wait
        if mode == "min":
            wait = max(summary[ride_id]["hourly_avg"].values())

        ride_time = wait + 5  # buffer

        # Stop if we can't finish before park close
        if current_time + ride_time > end_time:
            break

        timeline.append({
            "hour": hour,
            "wait": wait
        })

        current_time += ride_time
        rides_count += 1

    return rides_count, timeline

def analyze_all_rides(summary):
    results = {}

    for ride_id in summary:
        max_count, _ = simulate_single_ride_day(summary, ride_id, mode="max")
        min_count, _ = simulate_single_ride_day(summary, ride_id, mode="min")

        results[ride_id] = {
            "max_rides": max_count,
            "min_rides": min_count
        }

    return results

def rank_rides_by_throughput(results, mode="max"):
    if mode == "max":
        # Highest rides first
        return sorted(
            results.items(),
            key=lambda x: x[1]["max_rides"],
            reverse=True
        )

    elif mode == "min":
        # Lowest rides first
        return sorted(
            results.items(),
            key=lambda x: x[1]["min_rides"],
            reverse=False
        )

    else:
        raise ValueError("mode must be 'max' or 'min'")


def build_ride_name_map(file_path):
    ride_map = {}

    with open(file_path, newline='', encoding='utf-8') as f:
        reader = csv.reader(f)

        for row in reader:
            if not row or len(row) != 8:
                continue

            _, _, _, ride_id, ride_name, _, _, _ = row
            ride_id = str(ride_id)

            if "Single Rider" in ride_name:
                continue

            if ride_id not in ride_map:
                ride_map[ride_id] = ride_name

    return ride_map

def filter_summary(summary, ride_map):
    return {
        ride_id: data
        for ride_id, data in summary.items()
        if ride_id in ride_map
    }

def normalize_weights(summary):
    weights = [data.get("weight", 0) for data in summary.values()]
    max_w = max(weights) if weights else 1

    for data in summary.values():
        data["weight"] = data.get("weight", 0) / max_w

def simulate_future(summary, start_time, visited, horizon_minutes=180):
    current_time = start_time
    end_time = start_time + horizon_minutes

    temp_visited = visited.copy()
    total_score = 0

    while current_time < end_time:
        hour = int(current_time // 60)

        best_ride = None
        best_score = None

        for ride_id, data in summary.items():
            wait = get_wait_at_hour(data, hour)

            if wait is None:
                continue

            weight = data.get("weight", 0)

            # same scoring idea as weighted optimal
            score = wait - (weight * 20)

            if best_score is None or score < best_score:
                best_score = score
                best_ride = ride_id

        if best_ride is None:
            current_time = (hour + 1) * 60
            continue

        wait = get_wait_at_hour(summary[best_ride], hour)
    
        total_score += best_score
        temp_visited.add(best_ride)

        current_time += wait + 5

    return total_score


def main():
    file_path = r"C:\Users\Administrator\OneDrive\Desktop\personal_projects\projects\themeparkguide\data\summary.json"
    csv_path = r"C:\Users\Administrator\OneDrive\Desktop\personal_projects\projects\themeparkguide\data\ioa_wait_times.csv"
    mapping_path = r"C:\Users\Administrator\OneDrive\Desktop\personal_projects\projects\themeparkguide\data\ride_map.json"

    ride_map = build_ride_name_map(csv_path)
    summary = load_summary(file_path)

    summary = filter_summary(summary, ride_map)
    normalize_weights(summary)

    with open(mapping_path, "w") as f:
        json.dump(ride_map, f, indent=2)

    print("\n===== WEIGHTED OPTIMAL PATH =====")
    single_weighted = simulate_day(summary, ride_map, mode="weighted_optimal")
    for step in single_weighted:
        print(step)

    print("\n===== WEIGHTED OPTIMAL PATH (LOOKAHEAD ON) =====")
    optimal_future_path = simulate_day(summary, ride_map, mode="weighted_optimal",use_lookahead=True)
    for step in optimal_future_path:
        print(step)

    print("\n===== OPTIMAL PATH =====")
    optimal_path = simulate_day(summary, ride_map, mode="optimal")
    for step in optimal_path:
        print(step)

    print("\n===== WORST PATH =====")
    worst_path = simulate_day(summary, ride_map, mode="worst")
    for step in worst_path:
        print(step)

       
    print("\n===== THROUGHPUT ANALYSIS =====")
    results = analyze_all_rides(summary)

    top_rides = rank_rides_by_throughput(results, mode="max")

    print("\nTop 5 rides by max rides:")
    for ride_id, data in top_rides[:5]:
        name = ride_map.get(ride_id, "Unknown Ride")
        print(f"Ride {name} ({ride_id}): {data['max_rides']} rides")

    worst_rides = rank_rides_by_throughput(results, mode="min")

    print("\nWorst 5 rides:")
    for ride_id, data in worst_rides[:5]:
        name = ride_map.get(ride_id, "Unknown Ride")
        print(f"Ride {name} ({ride_id}): {data['min_rides']} rides")

if __name__ == "__main__":
    main()