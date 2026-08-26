package com.sandro.tradeoptimizer.algorithm;

import java.math.BigDecimal;

public class TradeCandidate {

    private final String tradeName;
    private final BigDecimal expectedPnl;
    private final BigDecimal marginRequired;

    public TradeCandidate(String tradeName, BigDecimal expectedPnl, BigDecimal marginRequired) {
        this.tradeName = tradeName;
        this.expectedPnl = expectedPnl;
        this.marginRequired = marginRequired;
    }

    public String getTradeName() {
        return tradeName;
    }

    public BigDecimal getExpectedPnl() {
        return expectedPnl;
    }

    public BigDecimal getMarginRequired() {
        return marginRequired;
    }

    public String toString(){
        return "candidateTrades: [" +
                "{ tradeName: '" + tradeName + "'" +
                ", marginRequired: " + marginRequired +
                ", expectedPnl: +" + expectedPnl + "}" +
                "]";
    }
}
