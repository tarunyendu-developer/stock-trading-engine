package com.trading.controller;

import com.trading.dto.StockRequest;
import com.trading.entity.Stock;
import com.trading.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor

public class StockController {

    private final StockService stockService;

    @PostMapping
    public Stock createStock(@RequestBody StockRequest request) {
        return stockService.createStock(request);
    }

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }
}