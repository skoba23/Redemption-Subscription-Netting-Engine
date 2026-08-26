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
1.Starting Database
bash
docker compose up -d
