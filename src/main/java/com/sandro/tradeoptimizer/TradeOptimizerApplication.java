package com.sandro.tradeoptimizer;

import com.sandro.tradeoptimizer.algorithm.KnapsackOptimizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TradeOptimizerApplication {
    public static void main(String[] args){
        SpringApplication.run(TradeOptimizerApplication.class, args);
    }

    @Bean
    public KnapsackOptimizer knapsackOptimizer() {
        return new KnapsackOptimizer();
    }
}
