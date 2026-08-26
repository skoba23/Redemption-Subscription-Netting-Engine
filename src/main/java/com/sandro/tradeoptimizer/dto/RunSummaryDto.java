package com.sandro.tradeoptimizer.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class RunSummaryDto {
    private UUID requestId;
    private BigDecimal maxMargin;
    private BigDecimal totalMarginRequired;
    private BigDecimal totalExpectedPnl;
    private Instant createdAt;

    public RunSummaryDto(UUID requestId, BigDecimal maxMargin, BigDecimal totalMarginrequired, BigDecimal totalExpectedPnl, Instant createdAt){

        this.requestId = requestId;
        this.maxMargin = maxMargin;
        this.totalMarginRequired = totalMarginrequired;
        this.totalExpectedPnl = totalExpectedPnl;
        this.createdAt = createdAt;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public BigDecimal getMaxMargin() {
        return maxMargin;
    }

    public BigDecimal getTotalMarginRequired() {
        return totalMarginRequired;
    }

    public BigDecimal getTotalExpectedPnl() {
        return totalExpectedPnl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
