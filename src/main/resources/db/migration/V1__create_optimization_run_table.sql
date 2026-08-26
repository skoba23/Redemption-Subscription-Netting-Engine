CREATE TABLE optimization_run(
    id UUID PRIMARY KEY,
    max_margin NUMERIC(19,2) NOT NULL,
    total_margin_required NUMERIC(19,2) NOT NULL,
    total_expected_pnl NUMERIC(19,2) NOT NULL,
    created_at TIMESTAMP NOT NULL
);