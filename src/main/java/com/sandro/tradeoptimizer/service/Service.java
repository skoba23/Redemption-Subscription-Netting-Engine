package com.sandro.tradeoptimizer.service;

import com.sandro.tradeoptimizer.algorithm.KnapsackOptimizer;
import com.sandro.tradeoptimizer.algorithm.KnapsackResult;
import com.sandro.tradeoptimizer.algorithm.TradeCandidate;
import com.sandro.tradeoptimizer.dto.OptimizeRequestDto;
import com.sandro.tradeoptimizer.dto.OptimizeResponceDto;
import com.sandro.tradeoptimizer.dto.RunSummaryDto;
import com.sandro.tradeoptimizer.dto.TradeDto;
import com.sandro.tradeoptimizer.entity.OptimizationRun;
import com.sandro.tradeoptimizer.entity.SubmittedTrade;
import com.sandro.tradeoptimizer.exception.RunNotFoundException;
import com.sandro.tradeoptimizer.repository.OptimizationRunRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;

import javax.management.RuntimeErrorException;
import org.springframework.data.domain.Pageable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Service {

    private final OptimizationRunRepository runRepository;
    private final KnapsackOptimizer optimizer;
    private final OptimizationRun optimizationRun;

    public Service(OptimizationRunRepository runRepository, KnapsackOptimizer optimizer){
        this.runRepository = runRepository;
        this.optimizer = optimizer;
    }

    @Transactional
    public OptimizeResponceDto optimize(OptimizeRequestDto request){
        List<TradeDto> tradeDtoList = request.getTrades();

        List<TradeCandidate> candidateList = tradeDtoList.stream()
                .map(t -> new TradeCandidate(t.getTradeName(), t.getExpectedPnl(), t.getMarginRequired()))
                .collect(Collectors.toUnmodifiableList());
        KnapsackResult result = optimizer.optimize(candidateList, request.getMaxMargin());

        //Set<TradeCandidate> selected = new HashSet<>(result.getSelectedTrades());

        OptimizationRun run = new OptimizationRun(
                request.getMaxMargin(),
                result.getTotalMarginRequired(),
                result.getTotalExpectedPnl()
        );
        BigDecimal newMaxMargin = request.getMaxMargin() - result.getTotalMarginRequired();

        List<TradeCandidate> listOfNotSelected;
        List<SubmittedTrade> tradesTemp = optimizationRun.getTrades();
        for(int i = 0; i < tradesTemp.size(); i++){
            if(!tradesTemp.get(i).isSelected()){
                listOfNotSelected.add(tradesTemp.get(i));
            }
        }
        KnapsackResult result1 = optimizer.optimize(listOfNotSelected, newMaxMargin);
        List<TradeCandidate> tmp = result1.getSelectedTrades();
        for(int i = 0; i < result.getSelectedTrades().size(); i++){
            tmp.add(result.getSelectedTrades().get(i));
        }
        Set<TradeCandidate> allSelected = new HashSet<>(tmp);
        for(int i = 0; i < tradeDtoList.size(); i++){
            TradeDto trade = tradeDtoList.get(i);
            boolean wasSelected = allSelected.contains(candidateList.get(i));
            run.addTrade(new SubmittedTrade(trade.getTradeName(), trade.getExpectedPnl(),
                    trade.getMarginRequired(), wasSelected));
        }

        OptimizationRun saved = runRepository.save(run);

        return toResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public OptimizeResponceDto getResult(UUID requestId){
        OptimizationRun run = runRepository.findById(requestId)
                .orElseThrow(() -> new RunNotFoundException(requestId));
        return  toResponseDto(run);
    }

    @Transactional(readOnly = true)
    public Page<RunSummaryDto> listRuns(Pageable pageable) {
        return runRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(run -> new RunSummaryDto(
                        run.getId(),
                        run.getMaxMargin(),
                        run.getTotalMarginRequired(),
                        run.getTotalExpectedPnl(),
                        run.getCreatedAt()
                ));
    }

    private OptimizeResponceDto toResponseDto(OptimizationRun run){
        List<TradeDto> selectedDtos = run.getTrades().stream()
                .filter(SubmittedTrade::isSelected)
                .map(t -> new TradeDto(t.getTradeName(), t.getExpectedPnl(), t.getMarginRequired()))
                .collect(Collectors.toList());
        return new OptimizeResponceDto(run.getId(), selectedDtos, run.getTotalMarginRequired(), run.getTotalExpectedPnl());
    }
}
