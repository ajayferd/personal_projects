const output = document.getElementById("output");

let waitChart;
let throughputChart;

/* =========================
   LIVE CLOCK + DATE
========================= */

function updateTime() {
    const now = new Date();

    document.getElementById("clock").textContent =
        now.toLocaleTimeString();

    document.getElementById("date").textContent =
        now.toDateString();
}

setInterval(updateTime, 1000);
updateTime();

/* =========================
   API CALLS
========================= */

async function loadPath(mode) {
    const container = document.getElementById("pathContainer");
    container.innerHTML = "Loading path...";

    const res = await fetch(`/api/path/${mode}`);
    const data = await res.json();

    renderPathSteps(data, mode);
}

async function loadRidesPerDay() {
    const res = await fetch("/api/rides_per_day");
    const data = await res.json();

    const container = document.getElementById("ridesPerDay");

    container.innerHTML = data.slice(0, 10).map(r => `
        <div style="padding:8px; border-bottom:1px solid #eee;">
            <b>${r.ride_name}</b><br/>
            ${r.rides_per_day} rides/day
        </div>
    `).join("");
}

loadRidesPerDay();

/* =========================
   CHART.JS RENDERING
========================= */

function renderThroughputChart(data) {
    const ctx = document.getElementById("throughputChart");

    const labels = data.most_throughput.map(r => r.ride_id);
    const values = data.most_throughput.map(r => r.avg_wait);

    if (throughputChart) throughputChart.destroy();

    throughputChart = new Chart(ctx, {
        type: "bar",
        data: {
            labels: labels,
            datasets: [{
                label: "Lowest Wait Rides",
                data: values
            }]
        }
    });
}

/* Placeholder chart (for later ride selection usage) */

function renderWaitChart(labels = [], values = []) {
    const ctx = document.getElementById("waitChart");

    if (waitChart) waitChart.destroy();

    waitChart = new Chart(ctx, {
        type: "line",
        data: {
            labels: labels,
            datasets: [{
                label: "Wait Time Trend",
                data: values
            }]
        }
    });
}

let rideMap = {};

async function loadRideMap() {
    const res = await fetch("/api/ride_map");
    rideMap = await res.json();

    const select = document.getElementById("rideSelect");
    select.innerHTML = "";

    Object.entries(rideMap).forEach(([id, name]) => {
        const option = document.createElement("option");
        option.value = id;
        option.textContent = name;
        select.appendChild(option);
    });
}

loadRideMap();

async function loadWaitExtremes() {
    const res = await fetch("/summary");
    const data = await res.json();

    const rides = Object.entries(data).map(([id, val]) => ({
        id,
        name: rideMap[id],
        avg: val.avg_wait
    }))
    .filter(r => r.avg > 0 && r.avg != null);

    const sortedLow = [...rides].sort((a,b) => a.avg - b.avg).slice(0,5);
    const sortedHigh = [...rides].sort((a,b) => b.avg - a.avg).slice(0,5);

    document.getElementById("lowestList").innerHTML =
        sortedLow.map(r => `<li>${r.name} - ${r.avg.toFixed(1)} min</li>`).join("");

    document.getElementById("highestList").innerHTML =
        sortedHigh.map(r => `<li>${r.name} - ${r.avg.toFixed(1)} min</li>`).join("");
}

loadWaitExtremes();

async function loadSelectedRide() {
    const rideId = document.getElementById("rideSelect").value;

    const res = await fetch("/summary");
    const data = await res.json();

    const ride = data[rideId];

    if (!ride) {
        document.getElementById("rideOutput").innerHTML =
            "No data available";
        return;
    }

    const name = rideMap[rideId];

    document.getElementById("rideOutput").innerHTML = `
        <h3>${name}</h3>
        <p><b>${ride.min_wait} mins</b></p>
    `;
}

function renderThroughputChart(data) {
    const ctx = document.getElementById("throughputChart");

    const labels = data.most_throughput.map(r => rideMap[r.ride_id]);
    const values = data.most_throughput.map(r => r.avg_wait);

    if (throughputChart) throughputChart.destroy();

    throughputChart = new Chart(ctx, {
        type: "bar",
        data: {
            labels,
            datasets: [{
                label: "Lowest Wait Rides",
                data: values
            }]
        }
    });
}

async function loadRidesPerDay() {
    const res = await fetch("/api/rides_per_day");
    const data = await res.json();

    const fast = data.fastest;
    const slow = data.slowest;

    document.getElementById("fastRides").innerHTML =
        fast.map(r => `
            <div>
                <b>${r.ride_name}</b><br/>
                Avg Wait: ${r.avg_wait.toFixed(1)} min<br/>
                🎢 ${r.rides_per_day} rides/day
            </div><hr/>
        `).join("");

    document.getElementById("slowRides").innerHTML =
        slow.map(r => `
            <div>
                <b>${r.ride_name}</b><br/>
                Avg Wait: ${r.avg_wait.toFixed(1)} min<br/>
                🎢 ${r.rides_per_day} rides/day
            </div><hr/>
        `).join("");
}

loadRidesPerDay();

function renderPathSteps(pathData) {
    return pathData.map((step, index) => `
        <div style="padding:10px; border-bottom:1px solid #eee;">
            <b>Step ${index + 1}:</b> ${step.ride_name || rideMap[step.ride_id]}<br/>
            ⏳ Wait: ${step.wait_time ?? "N/A"} min<br/>
            🎢 Action: Ride
        </div>
    `).join("");
}

function renderPathSteps(pathData, mode) {
    const container = document.getElementById("pathContainer");

    if (!pathData || pathData.length === 0) {
        container.innerHTML = "No path available.";
        return;
    }

    const totalWait = pathData.reduce((sum, step) => {
        const wait = step.wait_time || 0;
        return sum + wait;
    }, 0);

    container.innerHTML = `
        <h3 class="badge">${mode.toUpperCase()} PATH</h3>

        <div style="
            margin: 10px auto;
            font-size: 18px;
            color: #1a73e8;
            font-weight: bold;
        ">
            ⏱ Total Estimated Wait Time: ${totalWait} min
            (${pathData.length} rides planned)
        </div>
    ` + pathData.map((step, index) => `
        <div class="path-step">
            <div class="step-title">
                Step ${index + 1}: ${step.ride_name || rideMap[step.ride_id]}
            </div>

            <div class="wait-time">
                ⏳ Wait Time: ${step.wait_time ?? "N/A"} min
            </div>

            <div>
                🎢 Action: Ride
            </div>
        </div>
    `).join("");
}