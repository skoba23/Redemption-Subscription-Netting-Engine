package com.sandro.tradeoptimizer.algorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class KnapsackOptimizer {

    //main class that does logic: we give it a list of trades and maxMargin.
    //it will give us result as KnapsackResult object.
    public KnapsackResult optimize(List<TradeCandidate> trades, BigDecimal maxMargin){
        int n = trades.size();
        int[] marginsToCents = new int[n];
        int capacity = toCents(maxMargin);
        BigDecimal[] pnls = new BigDecimal[n];
        for(int i = 0; i < trades.size(); i++){
            marginsToCents[i] = toCents(trades.get(i).getMarginRequired());
            pnls[i] = trades.get(i).getExpectedPnl();
        }

        BigDecimal[][] dp = new BigDecimal[n+1][capacity + 1];
        for(int m = 0; m <= capacity; m++){
            dp[0][m] = BigDecimal.ZERO;
        }
        //creating dp table for backtracking.
        for(int i = 1; i <= n; i++){
            int margin = marginsToCents[i-1];
            BigDecimal pnl = pnls[i-1];
            for(int m = 0; m <= capacity; m++){
                BigDecimal skip = dp[i-1][m];
                if(margin <= m){
                    BigDecimal take = dp[i-1][m-margin].add(pnl);
                    if(take.compareTo(skip) > 0) dp[i][m] = take;
                    else dp[i][m] = skip;
                }
                else dp[i][m] = skip;
            }
        }

        List<TradeCandidate> selected = backtrack(dp, trades, marginsToCents, capacity);

        BigDecimal totalMargin = selected.stream()
                .map(TradeCandidate::getMarginRequired)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new KnapsackResult(selected, totalMargin, dp[n][capacity]);
    }
    //finds list of trades that do make change in final total profit.
    private List<TradeCandidate> backtrack(BigDecimal[][] dp,
                                           List<TradeCandidate> trades,
                                           int[] marginsToCents,
                                           int capacity){
        List<TradeCandidate> result = new ArrayList<>();
        int m = capacity;
        for(int i = trades.size(); i>0; i--){
            if(dp[i][m].compareTo(dp[i-1][m]) != 0){
                result.add(trades.get(i-1));
                m -= marginsToCents[i-1];
            }
        }
        return result;
    }
    private int toCents(BigDecimal value) {
        return value.multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP)
                .intValueExact();
    }
}
