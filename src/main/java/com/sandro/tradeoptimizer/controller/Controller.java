package com.sandro.tradeoptimizer.controller;

import com.sandro.tradeoptimizer.dto.OptimizeRequestDto;
import com.sandro.tradeoptimizer.dto.OptimizeResponceDto;
import com.sandro.tradeoptimizer.dto.RunSummaryDto;
import com.sandro.tradeoptimizer.service.Service;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/trades")
public class Controller {
    private final Service service;

    public Controller(Service service){
        this.service = service;
    }

    @PostMapping("/optimize")
    public ResponseEntity<OptimizeResponceDto> optimize(@Valid @RequestBody OptimizeRequestDto request){
        OptimizeResponceDto responceDto = service.optimize(request);

        HttpStatus status = responceDto.getSelectedTrades().isEmpty() ? HttpStatus.OK : HttpStatus.CREATED;
        return ResponseEntity.status(status).body(responceDto);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<OptimizeResponceDto> getResult(@PathVariable UUID requestId){
        return ResponseEntity.ok(service.getResult(requestId));
    }

    @GetMapping
    public ResponseEntity<Page<RunSummaryDto>> listRuns(Pageable pageable){
        return ResponseEntity.ok(service.listRuns(pageable));
    }
}

