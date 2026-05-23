package com.trading.repository;

import com.trading.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findByBuyOrderStockId(Long stockId);
}