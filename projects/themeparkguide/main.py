from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles
from fastapi.responses import FileResponse

from rideAlgorithm import simulate_day
from transformCSVtoJSON import load_data, sort_data, build_summary, build_ride_map

app = FastAPI()

# Serve static frontend
app.mount("/static", StaticFiles(directory="static"), name="static")

CSV_FILE = r"C:\Users\Administrator\OneDrive\Desktop\personal_projects\projects\themeparkguide\data\ioa_wait_times.csv"

summary = {}
ride_map = {}

@app.on_event("startup")
def startup_event():
    global rides, ride_map, summary

    rides = load_data(CSV_FILE)
    ride_map = build_ride_map(rides)

    sorted_rides = sort_data(rides)
    summary = build_summary(sorted_rides)

    print(f"Loaded {len(summary)} rides into memory")

@app.get("/")
def serve_home():
    return FileResponse("static/index.html")

@app.get("/rides")
def get_rides():
    return rides


@app.get("/summary")
def get_summary():
    return summary

# ===== API ROUTES =====

@app.get("/api/ride_map")
def get_ride_map():
    return ride_map

@app.get("/api/waits")
def get_wait_times():
    result = {}
    for ride_id, data in summary.items():
        result[ride_id] = data.get("average_wait", {})
    return result


@app.get("/api/path/{mode}")
def get_path(mode: str):
    path = simulate_day(summary, ride_map, mode)
    return path


@app.get("/api/throughput")
def get_throughput():
    rides = []

    for ride_id, data in summary.items():
        avg_wait = sum(data.get("average_wait", {}).values()) / max(len(data.get("average_wait", {})), 1)

        rides.append({
            "ride_id": ride_id,
            "ride_name": ride_map.get(ride_id, "Unknown"),
            "avg_wait": avg_wait
        })

    most = sorted(rides, key=lambda x: x["avg_wait"])[:5]
    least = sorted(rides, key=lambda x: x["avg_wait"], reverse=True)[:5]

    return {
        "most_throughput": most,
        "least_throughput": least
    }

@app.get("/api/rides_per_day")
def rides_per_day():
    result = []

    for ride_id, data in summary.items():
        avg_wait = data.get("avg_wait", 0)

        if not avg_wait:
            continue

        minutes_open = 14 * 60
        cycle_time = avg_wait + 3
        rides_possible = int(minutes_open / cycle_time)

        result.append({
            "ride_id": ride_id,
            "ride_name": ride_map.get(int(ride_id), "Unknown"),
            "avg_wait": avg_wait,
            "rides_per_day": rides_possible
        })

    # split groups
    sorted_by_fast = sorted(result, key=lambda x: x["avg_wait"])
    sorted_by_slow = sorted(result, key=lambda x: x["avg_wait"], reverse=True)

    return {
        "fastest": sorted_by_fast[:5],
        "slowest": sorted_by_slow[:5]
    }