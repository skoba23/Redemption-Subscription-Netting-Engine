package com.sandro.tradeoptimizer.algorithm;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class KnapsackResult {
    private final List<TradeCandidate> selectedTrades;
    private final BigDecimal totalMarginRequired;
    private final BigDecimal totalExpectedPnl;

    public KnapsackResult(List<TradeCandidate> selectedTrades, BigDecimal totalMarginRequired, BigDecimal totalExpectedPnl) {
        this.selectedTrades = selectedTrades;
        this.totalMarginRequired = totalMarginRequired;
        this.totalExpectedPnl = totalExpectedPnl;
    }

    public List<TradeCandidate> getSelectedTrades() {
        return selectedTrades;
    }

    public BigDecimal getTotalMarginRequired() {
        return totalMarginRequired;
    }

    public BigDecimal getTotalExpectedPnl() {
        return totalExpectedPnl;
    }
}
