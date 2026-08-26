package com.sandro.tradeoptimizer.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "submitted_trade")
public class SubmittedTrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "run_id", nullable = false)
    private OptimizationRun run;

    @Column(name = "trade_name", nullable = false)
    private String tradeName;

    @Column(name = "expected_pnl", nullable = false)
    private BigDecimal expectedPnl;

    @Column(name = "margin_required", nullable = false)
    private BigDecimal marginRequired;

    @Column(name = "selected", nullable = false)
    private boolean selected;

    protected SubmittedTrade() {
    }
    public SubmittedTrade(String tradeName, BigDecimal expectedPnl, BigDecimal marginRequired, boolean selected){

        this.tradeName = tradeName;
        this.expectedPnl = expectedPnl;
        this.marginRequired = marginRequired;
        this.selected = selected;
    }
    public OptimizationRun getRun() {
        return run;
    }

    public void setRun(OptimizationRun run) {
        this.run = run;
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

    public boolean isSelected() {
        return selected;
    }
}
