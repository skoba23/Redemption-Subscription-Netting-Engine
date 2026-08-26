package com.sandro.tradeoptimizer.repository;

import com.sandro.tradeoptimizer.entity.OptimizationRun;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface OptimizationRunRepository extends JpaRepository<OptimizationRun, UUID> {
    Page<OptimizationRun> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
