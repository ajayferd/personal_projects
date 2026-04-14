# This is an application to query historical theme park times via https://queue-times.com/ and to calculate and visualize the best statistical day at Universal Islands of Adventure. I built this using mainly python and FastAPI. Wait times are queried every 5 minutes and then combined with my own historical data for the pathing algorithm. The optimal wait times path is weighted to include popular rides, the longer average wait time, the more weight an item has.

# To deploy application, run the following scripts:
    python -m venv venv
    venv\Scripts\activate
    pip install -r requirements.txt
    uvicorn main:app --reload

# Finally once succesfully running go to the following ip and port to see the application:
    http://127.0.0.1:8000

Created by Alejandro Fernandez