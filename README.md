# Roomba Simulation Service

Simulates a roomba in a rectangular room.

# Setup

**Requires Docker & Java 8**

```
> git clone https://github.com/tompierce/roomba-service.git
> cd roomba-service
> docker build -t roomba-service .
```

# Running a Dev Server

```
# start database
> docker run -p 3306:3306 --name roomba-db -v $PWD/sql:/docker-entrypoint-initdb.d -e MYSQL_ROOT_PASSWORD=password -d mysql:5.6

# start service
> docker run -p 8080:8080 -v $PWD:/code --link roomba-db --name roomba-service -d roomba-service

# it takes the server a minute or so to download dependencies the first time
# while we wait, check the logs 
> docker logs -f roomba-service
```

Access the server at localhost:8080 (or 192.168.99.100:8080 on OSX)

```
# run a roomba simulation
curl -H "Content-Type: application/json" -X POST -d '{"roomSize" : [5, 5],"coords" : [1, 2],"patches" : [[1, 0],[2, 2],[2, 3]],"instructions" : "NNESEESWNWW"}' http://192.168.99.100:8080/simulation

# get a previous simulation
curl 192.168.99.100:8080/simulation/1
```

# Running the tests

```
> ./gradlew test
# or run tests and generate coverage report
> ./gradlew jacocoTestReport 
```

# API Documentation
## Run a roomba simulation

### Request

```
POST /simulation
{
  "roomSize" : [5, 5],
  "coords" : [1, 2],
  "patches" : [
    [1, 0],
    [2, 2],
    [2, 3]
  ],
  "instructions" : "NNESEESWNWW"
}
```

### Response

```
{
  "coords" : [1, 3],
  "patches" : 1,
  "simId", "1"
}
```
## Retrieve a previously run simulation

### Request

```
GET /simulation/1
```

### Response

```
{
  "coords" : [1, 3],
  "patches" : 1,
  "simId", "1"
}
```

## Errors
Both endpoints will return a JSON error response in the form:

```
{"error":"<error message>"}
```