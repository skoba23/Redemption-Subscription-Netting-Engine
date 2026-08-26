CREATE INDEX idx_optimization_run_created_at ON optimization_run (created_at DESC);
CREATE INDEX idx_submitted_trade_run_id ON submitted_trade (run_id);