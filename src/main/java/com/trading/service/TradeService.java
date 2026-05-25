package com.trading.service;

import com.trading.entity.Trade;
import com.trading.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TradeService {

    private final TradeRepository tradeRepository;

    public List<Trade> getTradesByStock(Long stockId) {
        return tradeRepository.findTop10ByBuyOrderStockIdOrderByExecutedAtDesc(stockId);
    }
}