# dictionary format for each ride's data
# timestamp,park_name,land_name,ride_id,ride_name,wait_time,is_open,last_updated
# {
#    "timestamp": datetime,
#    "land": str,
#    "ride_id": int,
#    "ride_name": str,
#    "wait_time": int,
#    "is_open": bool,
#    "last_updated": datetime
# }
import csv
from datetime import datetime, timezone
from zoneinfo import ZoneInfo
from collections import defaultdict
import json

def parse_timestamp(ts):
    dt = datetime.fromisoformat(ts)

    # assume UTC
    dt = dt.replace(tzinfo=timezone.utc)

    # convert to EST/EDT
    return dt.astimezone(ZoneInfo("America/New_York"))

def is_valid_hour(dt):
    return 8 <= dt.hour <= 22

def load_data(file_path):
    rides = {}

    with open(file_path, newline='', encoding='utf-8') as f:
        reader = csv.reader(f)

        for row in reader:
            timestamp, park, land, ride_id, ride_name, wait_time, is_open, last_updated = row

            ride_id = int(ride_id)
            wait_time = int(wait_time)
            is_open = is_open == "True"

            record = {
                "timestamp": parse_timestamp(timestamp),
                "land": land,
                "ride_name": ride_name,
                "wait_time": wait_time,
                "is_open": is_open,
                "last_updated": last_updated
            }

            if ride_id not in rides:
                rides[ride_id] = []

            rides[ride_id].append(record)

    return rides

def compute_hourly_averages(rides, ride_id):
    hourly = defaultdict(list)

    for r in rides.get(ride_id, []):
        if (
            r["is_open"]
            and r["wait_time"] > 0
            and is_valid_hour(r["timestamp"])
        ):
            hour = r["timestamp"].hour
            hourly[hour].append(r["wait_time"])

    return {
        str(hour): sum(times)/len(times)
        for hour, times in hourly.items()
    }

def best_hour_to_ride(rides, ride_id):
    hourly_avg = compute_hourly_averages(rides, ride_id)

    if not hourly_avg:
        return None

    return int(min(hourly_avg, key=hourly_avg.get))

def worst_hour_to_ride(rides, ride_id):
    hourly_avg = compute_hourly_averages(rides, ride_id)

    if not hourly_avg:
        return None

    return int(max(hourly_avg, key=hourly_avg.get))

def compute_weights(summary):
    valid_avgs = [
        v["avg_wait"]
        for v in summary.values()
        if v["avg_wait"] is not None
    ]

    if not valid_avgs:
        return summary

    min_avg = min(valid_avgs)
    max_avg = max(valid_avgs)

    for ride_id, data in summary.items():
        avg = data["avg_wait"]

        if avg is None:
            data["weight"] = 0
        else:
            # Normalize (0–1)
            if max_avg == min_avg:
                data["weight"] = 1
            else:
                data["weight"] = (avg - min_avg) / (max_avg - min_avg)

    return summary

def sort_data(rides):
    for ride_id in rides:
        rides[ride_id].sort(key=lambda x: x["timestamp"])

    return rides

def get_lowest_nonzero_wait(rides, ride_id):
    waits = [
        r["wait_time"]
        for r in rides.get(ride_id, [])
        if r["wait_time"] > 0 and r["is_open"]
    ]

    return min(waits) if waits else None

def get_average_wait(rides, ride_id):
    waits = [
        r["wait_time"]
        for r in rides.get(ride_id, [])
        if r["is_open"]
    ]

    return sum(waits) / len(waits) if waits else None

def best_hour_to_ride(rides, ride_id):
    hourly = defaultdict(list)

    for r in rides.get(ride_id, []):
        if r["wait_time"] > 0 and r["is_open"]:
            hour = r["timestamp"].hour
            hourly[hour].append(r["wait_time"])

    avg_by_hour = {
        h: sum(v)/len(v)
        for h, v in hourly.items()
    }

    return min(avg_by_hour, key=avg_by_hour.get) if avg_by_hour else None

def build_summary(rides):
    summary = {}

    for ride_id in rides:
        avg_wait = get_average_wait(rides, ride_id)

        if avg_wait is None or avg_wait == 0:
            continue  # skip closed rides entirely

        summary[ride_id] = {
            "min_wait": get_lowest_nonzero_wait(rides, ride_id),
            "avg_wait": avg_wait,
            "best_hour": best_hour_to_ride(rides, ride_id),
            "hourly_avg": compute_hourly_averages(rides, ride_id)
        }

    summary = compute_weights(summary)

    return summary

def save_summary(summary, filename="summary.json"):
    filename = r"C:\Users\Administrator\OneDrive\Desktop\personal_projects\projects\themeparkguide\data\summary.json"
    with open(filename, "w") as f:
        json.dump(summary, f, indent=2)

def build_ride_map(rides):
    ride_map = {}

    for ride_id, records in rides.items():
        if records:
            ride_map[ride_id] = records[0]["ride_name"]

    return ride_map

def main():
    file_path = r"C:\Users\Administrator\OneDrive\Desktop\personal_projects\projects\themeparkguide\data\ioa_wait_times.csv"
    
    rides = load_data(file_path)
    sorted_rides = sort_data(rides)
    summary = build_summary(sorted_rides)

    print(f"Processed {len(summary)} rides")

    save_summary(summary)

if __name__ == "__main__":
    main()