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
