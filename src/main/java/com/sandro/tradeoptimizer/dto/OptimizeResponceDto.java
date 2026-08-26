package com.sandro.tradeoptimizer.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OptimizeResponceDto {

    private UUID requestId;
    private List<TradeDto> selectedTrades;
    private BigDecimal totalMarginRequired;
    private BigDecimal totalExpectedPnl;

    public OptimizeResponceDto(UUID requestId, List<TradeDto> selectedTrades, BigDecimal totalMarginRequired, BigDecimal totalExpectedPnl){
        this.requestId = requestId;
        this.selectedTrades = selectedTrades;
        this.totalMarginRequired = totalMarginRequired;
        this.totalExpectedPnl = totalExpectedPnl;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public List<TradeDto> getSelectedTrades() {
        return selectedTrades;
    }

    public BigDecimal getTotalMarginRequired() {
        return totalMarginRequired;
    }

    public BigDecimal getTotalExpectedPnl() {
        return totalExpectedPnl;
    }
}
