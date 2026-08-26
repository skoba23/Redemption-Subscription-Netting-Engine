CREATE TABLE submitted_trade (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    run_id UUID NOT NULL REFERENCES optimization_run (id),
    trade_name VARCHAR(255) NOT NULL,
    expected_pnl NUMERIC(19,2) NOT NULL,
    margin_required NUMERIC(19,2) NOT NULL,
    selected BOOLEAN NOT NULL DEFAULT FALSE
);