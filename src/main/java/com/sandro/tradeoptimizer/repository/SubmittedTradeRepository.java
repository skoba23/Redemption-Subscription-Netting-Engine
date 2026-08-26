package com.sandro.tradeoptimizer.repository;
import com.sandro.tradeoptimizer.entity.SubmittedTrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmittedTradeRepository extends JpaRepository<SubmittedTrade, Long> {
}
