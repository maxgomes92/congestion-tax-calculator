
# Congestion Tax Calculator

This project is a solution to a problem proposed by [Volvo Cars](https://github.com/volvo-cars/congestion-tax-calculator/blob/main/ASSIGNMENT.md).

This service exposes one endpoint that calculates fees based on time and vehicle type.

Questions and future implementation plan are listed at [questions.md](questions.md)

```
POST /api/v1/congestion
```

## Pre-requisites

Java 17+

## To Run

```shell
./gradlew bootRun
```

## To test

```shell
./gradlew test
```

## Request example

```json
{
  "vehicleType": "car",
  "timestamps": [
    "2013-01-14 21:00:00",
    "2013-01-15 21:00:00",
    "2013-02-07 06:23:27",
    "2013-02-07 15:27:00",
    "2013-02-08 06:27:00",
    "2013-02-08 06:20:27",
    "2013-02-08 14:35:00",
    "2013-02-08 15:29:00",
    "2013-02-08 15:47:00",
    "2013-02-08 16:01:00",
    "2013-02-08 16:48:00",
    "2013-02-08 17:49:00",
    "2013-02-08 18:29:00",
    "2013-02-08 18:35:00",
    "2013-03-26 14:25:00",
    "2013-03-28 14:07:27"
  ]
}
```

## Response example

```json
{
    "total": 73,
    "details": [
        {
            "timestamp": "2013-01-15",
            "value": 0
        },
        {
            "timestamp": "2013-03-28",
            "value": 0
        },
        {
            "timestamp": "2013-02-08",
            "value": 60
        },
        {
            "timestamp": "2013-03-26",
            "value": 0
        },
        {
            "timestamp": "2013-01-14",
            "value": 0
        },
        {
            "timestamp": "2013-02-07",
            "value": 13
        }
    ]
}
```
