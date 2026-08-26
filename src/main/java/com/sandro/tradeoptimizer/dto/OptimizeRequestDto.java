package com.sandro.tradeoptimizer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.List;

public class OptimizeRequestDto {

    @NotEmpty
    @Valid
    private List<TradeDto> trades;

    @NotNull
    @PositiveOrZero
    private BigDecimal maxMargin;

    public OptimizeRequestDto(){

    }

    public List<TradeDto> getTrades() {
        return trades;
    }

    public void setTrades(List<TradeDto> trades) {
        this.trades = trades;
    }

    public BigDecimal getMaxMargin() {
        return maxMargin;
    }

    public void setMaxMargin(BigDecimal maxMargin) {
        this.maxMargin = maxMargin;
    }
}
