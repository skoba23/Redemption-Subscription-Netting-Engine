package com.sandro.tradeoptimizer.algorithm;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KnapsackOptimizerTest {
    private final KnapsackOptimizer optimizer = new KnapsackOptimizer();

    @Test
    void selectsHigherValueCombinationOverGreedyChoice(){
        TradeCandidate a = new TradeCandidate("A", bd(60), bd(100));
        TradeCandidate b = new TradeCandidate("B", bd(40), bd(50));
        TradeCandidate c = new TradeCandidate("C", bd(40), bd(50));

        KnapsackResult result = optimizer.optimize(List.of(a,b,c), bd(100));

        assertThat(result.getTotalExpectedPnl()).isEqualByComparingTo(bd(80));
        assertThat(result.getSelectedTrades()).containsExactlyInAnyOrder(b, c);
    }

    @Test
    void returnsEmptyResultWhenNothingFitsWithinMargin(){
        TradeCandidate a = new TradeCandidate("A", bd(500), bd(1000));
        KnapsackResult result = optimizer.optimize(List.of(a), bd(100));
        assertThat(result.getSelectedTrades()).isEmpty();
        assertThat(result.getTotalExpectedPnl()).isEqualByComparingTo(BigDecimal.ZERO);
    }
    @Test
    void handleDecimalMarginAndPnlCorrectly(){
        TradeCandidate a = new TradeCandidate("A", bd("125.75"), bd("300.50"));
        TradeCandidate b = new TradeCandidate("B", bd("80.25"), bd("199.50"));

        KnapsackResult result = optimizer.optimize(List.of(a, b), bd("500.00"));

        assertThat(result.getSelectedTrades()).containsExactlyInAnyOrder(a, b);
        assertThat(result.getTotalExpectedPnl()).isEqualByComparingTo(bd("206.00"));
    }

    private static BigDecimal bd(String value) {
        return new BigDecimal(value);
    }
    private static BigDecimal bd(long value) {
        return BigDecimal.valueOf(value);
    }
}
