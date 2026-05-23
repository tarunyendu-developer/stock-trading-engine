package com.trading.repository;

import com.trading.entity.Order;
import com.trading.enums.OrderStatus;
import com.trading.enums.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStockIdAndOrderTypeAndStatusAndPriceLessThanEqualOrderByPriceAscCreatedAtAsc(
            Long stockId,
            OrderType orderType,
            OrderStatus status,
            BigDecimal price
    );

    List<Order> findByStockIdAndOrderTypeAndStatusAndPriceGreaterThanEqualOrderByPriceDescCreatedAtAsc(
            Long stockId,
            OrderType orderType,
            OrderStatus status,
            BigDecimal price
    );

    List<Order> findByUserId(Long userId);

    List<Order> findByStockIdAndOrderTypeAndStatusOrderByPriceDesc(
            Long stockId,
            OrderType orderType,
            OrderStatus status
    );

    List<Order> findByStockIdAndOrderTypeAndStatusOrderByPriceAsc(
            Long stockId,
            OrderType orderType,
            OrderStatus status
    );
}