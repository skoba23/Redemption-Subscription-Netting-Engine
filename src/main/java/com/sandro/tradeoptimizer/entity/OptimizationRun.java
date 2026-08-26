package com.sandro.tradeoptimizer.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "optimization_run")
public class OptimizationRun {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "max_margin", nullable = false)
    private BigDecimal maxMargin;

    @Column(name = "total_margin_required", nullable = false)
    private BigDecimal totalMarginRequired;

    @Column(name = "total_expected_pnl", nullable = false)
    private BigDecimal totalExpectedPnl;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "run", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubmittedTrade> trades = new ArrayList<>();

    protected  OptimizationRun(){

    }

    public OptimizationRun(BigDecimal maxMargin, BigDecimal totalMarginRequired, BigDecimal totalExpectedPnl){

        this.maxMargin = maxMargin;
        this.totalMarginRequired = totalMarginRequired;
        this.totalExpectedPnl = totalExpectedPnl;
        this.createdAt = Instant.now();
    }

    public void addTrade(SubmittedTrade trade) {
        trades.add(trade);
        trade.setRun(this);
    }

    public UUID getId() {
        return id;
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

    public List<SubmittedTrade> getTrades() {
        return trades;
    }
}
