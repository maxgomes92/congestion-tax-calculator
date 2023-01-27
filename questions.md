# Questions

1. Why is time not in ISO format? 
It would ease data handling as well as allow supporting multiple timezones (server and client).

# Future implementations

1. Connect to a database (memcache for local development, MySQL or MongoDB for production).
2. Move fees from Congestion service to the db including its city.
3. Create a table for entities City (name) and Fee (city, amount, start and end time).
4. Implement logging and tracing (for debugging purposes)
5. Implement Spring Security to protect the API
6. Containerize the application for easier deploy and versioning
7. Set up a pipeline to automate running the tests and deploying
8. Set up alarms to get notified about the service's performance