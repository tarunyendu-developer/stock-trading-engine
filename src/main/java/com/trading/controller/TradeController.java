package com.trading.controller;

import com.trading.entity.Trade;
import com.trading.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trades")
@RequiredArgsConstructor

public class TradeController {

    private final TradeService tradeService;

    @GetMapping("/stock/{stockId}")
    public List<Trade> getTrades(@PathVariable Long stockId) {
        return tradeService.getTradesByStock(stockId);
    }
}