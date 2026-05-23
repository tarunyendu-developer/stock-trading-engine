package com.trading.service;

import com.trading.dto.StockRequest;
import com.trading.entity.Stock;
import com.trading.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class StockService {

    private final StockRepository stockRepository;

    public Stock createStock(StockRequest request) {

        log.info("Creating New Stock");

        Stock stock = Stock.builder()
                .symbol(request.getSymbol())
                .companyName(request.getCompanyName())
                .build();

        Stock savedStock = stockRepository.save(stock);

        log.info("Stock Created Successfully");

        return savedStock;
    }

    public List<Stock> getAllStocks() {

        log.info("Fetching All Stocks");

        return stockRepository.findAll();
    }
}