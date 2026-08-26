# Redemption - Subscription Netting Engine
A Spring Boot REST Service that selects the best combination of trades, maximizing total expected P&L without exceeding the available margin. This is a classis Knapsack problem: each trade's *marginRequired* is the "Weight", *expectedPnl* is "Value" and *maxMargin* is "capacity".

## Repository Structure
```
trade-optimizer-service/
├── .idea -- gitignore is here
├── README.md
├── docker-compose.yml
├── pom.xml
│
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── sandro/
    │   │           └── tradeoptimizer/
    │   │               ├── TradeOptimizerApplication.java
    │   │               │
    │   │               ├── algorithm/
    │   │               │   ├── KnapsackOptimizer.java
    │   │               │   ├── KnapsackResult.java
    │   │               │   └── TradeCandidate.java
    │   │               │
    │   │               ├── controller/
    │   │               │   └── Controller.java
    │   │               │
    │   │               ├── dto/
    │   │               │   ├── OptimizeRequestDto.java
    │   │               │   ├── OptimizeResponceDto.java
    │   │               │   ├── RunSummaryDto.java
    │   │               │   └── TradeDto.java
    │   │               │
    │   │               ├── entity/
    │   │               │   ├── OptimizationRun.java
    │   │               │   └── SubmittedTrade.java
    │   │               │
    │   │               ├── exception/
    │   │               │   ├── GlobalExceptionHandler.java
    │   │               │   └── RunNotFoundException.java
    │   │               │
    │   │               ├── repository/
    │   │               │   ├── OptimizationRunRepository.java
    │   │               │   └── SubmittedTradeRepository.java
    │   │               │
    │   │               └── service/
    │   │                   └── Service.java
    │   │
    │   └── resources/
    │       ├── application.yml
    │       └── db/
    │           └── migration/
    │               ├── V1__create_optimization_run_table.sql
    │               └── V2__create_submitted_trade_table.sql
    │
    └── test/
        └── java/
            └── com/
                └── sandro/
                    └── tradeoptimizer/
                        └── algorithm/
                            └── KnapsackOptimizerTest.java
```
## Running Instructions
**1.Starting Database**
```
docker compose up -d
```
`trade-optimizer-db` status should be healthy

**2.Run the application**
```
mvn spring-boot:run
```
app listens on port 8081

**3.Run the tests**
```
mvn test
```
this runs 'KnapsackOptimizerTest' which is test of a Logic. I ran it and it manually passed all the tests.

## Database Setup
Setup was handled by `docked-compose.yml`.
database, name and password was tradeoptimizer, matching with `application.yml`.
On application startup, Flyway automatically applies the migrations in `src/main/resources/db/migration/` to create the schema.

## API Endpoints

### `POST /api/v1/trades/optimize`
Accepts a list of candidate trades and a max margin, runs the optimizer, persists the request and result, and returns the outcome.

- Returns **201 Created** when at least one trade was selected.
- Returns **200 OK** with an empty list and `totalExpectedPnl: 0` when no combination of trades fits within `maxMargin`.
- Returns **400 Bad Request** with a descriptive message for invalid input

**Example - trades selected (201)**

```bash
curl -i -X POST http://localhost:8081/api/v1/trades/optimize \
  -H "Content-Type: application/json" \
  -d '{
    "trades": [
      {"tradeName": "A", "expectedPnl": 60, "marginRequired": 100},
      {"tradeName": "B", "expectedPnl": 40, "marginRequired": 50},
      {"tradeName": "C", "expectedPnl": 40, "marginRequired": 50}
    ],
    "maxMargin": 100
  }'
```

```
HTTP/1.1 201
Content-Type: application/json
 
{
  "requestId": "9680e988-b2b0-452b-a0cd-24f804dbce49",
  "selectedTrades": [
    {"tradeName": "B", "expectedPnl": 40, "marginRequired": 50},
    {"tradeName": "C", "expectedPnl": 40, "marginRequired": 50}
  ],
  "totalMarginRequired": 100,
  "totalExpectedPnl": 80
}
```

**Example - nothing fits within margin (200)**

```bash
curl -i -X POST http://localhost:8081/api/v1/trades/optimize \
  -H "Content-Type: application/json" \
  -d '{
    "trades": [
      {"tradeName": "A", "expectedPnl": 500, "marginRequired": 1000}
    ],
    "maxMargin": 10
  }'
```
 
```
HTTP/1.1 200
Content-Type: application/json
 
{
  "requestId": "2e6254be-d2f2-4632-9520-d34367025b5b",
  "selectedTrades": [],
  "totalMarginRequired": 0,
  "totalExpectedPnl": 0
}
```

**Example - invalid input (400)**
 
```bash
curl -i -X POST http://localhost:8081/api/v1/trades/optimize \
  -H "Content-Type: application/json" \
  -d '{"trades": [], "maxMargin": 100}'
```
 
```
HTTP/1.1 400
Content-Type: application/json
 
{
  "timestamp": "2026-08-26T06:31:07.221788132Z",
  "status": 400,
  "error": "Bad Request",
  "message": "trades: must not be empty"
}
```

### `GET /api/v1/trades/{requestId}`

Returns result for the given requestId

- Returns **200 OK** if found.
- Returns **404 Not Found** if no run exists for that ID.

**Example - found (200)**

```bash
curl -i http://localhost:8081/api/v1/trades/9680e988-b2b0-452b-a0cd-24f804dbce49
```
 
```
HTTP/1.1 200
Content-Type: application/json
 
{
  "requestId": "9680e988-b2b0-452b-a0cd-24f804dbce49",
  "selectedTrades": [
    {"tradeName": "B", "expectedPnl": 40.00, "marginRequired": 50.00},
    {"tradeName": "C", "expectedPnl": 40.00, "marginRequired": 50.00}
  ],
  "totalMarginRequired": 100.00,
  "totalExpectedPnl": 80.00
}
```

**Example - not found (404)**

```bash
curl -i http://localhost:8081/api/v1/trades/00000000-0000-0000-0000-000000000000
```
 
```
HTTP/1.1 404
Content-Type: application/json
 
{
  "timestamp": "2026-08-26T06:31:31.591313923Z",
  "status": 404,
  "error": "Not Found",
  "message": "No optimization run found for this id: 00000000-0000-0000-0000-000000000000"
}
```

### `GET /api/v1/trades`

Shows a list of every optimization the service has run before, most recent first.

**Example (200)**

```bash
curl -i http://localhost:8081/api/v1/trades
```
 
```
HTTP/1.1 200
Content-Type: application/json
 
{
  "content": [
    {
      "requestId": "9680e988-b2b0-452b-a0cd-24f804dbce49",
      "maxMargin": 100.00,
      "totalMarginRequired": 100.00,
      "totalExpectedPnl": 80.00,
      "createdAt": "2026-08-26T06:23:04.816456Z"
    }
  ],
  "totalPages": 1,
  "totalElements": 1,
  "size": 20,
  "number": 0,
  "first": true,
  "last": true,
  "empty": false
}
```

## Schema Design

We have two tables:

`optimization_run` - one row per call. 
(id, maxMargin, totalMarginRequired, totalExpectedPnl, createdAt)

`submitted_trade` - one row per trade that was in a request, with a selected flag showing whether the optimizer picked it or not. 
(id, run_id, tradeName, expectedPnl, marginRequired, selected)

Each `submitted_trade` links to its `optimization_run` with *run_id*

**Indexes:**

`run_id` on `submitted_trade` - the database filters by this column.

`created_at` on `optimization_run` - the audit list endpoint always sorts newest-first.
