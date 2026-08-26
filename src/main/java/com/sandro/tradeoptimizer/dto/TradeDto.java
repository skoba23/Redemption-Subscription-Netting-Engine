package com.sandro.tradeoptimizer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class TradeDto {

    @NotBlank
    private String tradeName;

    @NotNull
    private BigDecimal expectedPnl;

    @NotNull
    @PositiveOrZero
    private BigDecimal marginRequired;

    public TradeDto(String tradeName, BigDecimal expectedPnl, BigDecimal marginRequired){

        this.tradeName = tradeName;
        this.expectedPnl = expectedPnl;
        this.marginRequired = marginRequired;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public BigDecimal getExpectedPnl() {
        return expectedPnl;
    }

    public void setExpectedPnl(BigDecimal expectedPnl) {
        this.expectedPnl = expectedPnl;
    }

    public BigDecimal getMarginRequired() {
        return marginRequired;
    }

    public void setMarginRequired(BigDecimal marginRequired) {
        this.marginRequired = marginRequired;
    }
}
