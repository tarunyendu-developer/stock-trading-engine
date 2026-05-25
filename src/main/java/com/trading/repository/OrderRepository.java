package com.trading.repository;

import com.trading.entity.Order;
import com.trading.enums.OrderStatus;
import com.trading.enums.OrderType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Order> findByStockIdAndOrderTypeAndStatusAndPriceLessThanEqualOrderByPriceAscCreatedAtAsc(
            Long stockId,
            OrderType orderType,
            OrderStatus status,
            BigDecimal price
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Order> findByStockIdAndOrderTypeAndStatusAndPriceGreaterThanEqualOrderByPriceDescCreatedAtAsc(
            Long stockId,
            OrderType orderType,
            OrderStatus status,
            BigDecimal price
    );

    List<Order> findByUserId(Long userId);

    List<Order> findTop5ByStockIdAndOrderTypeAndStatusOrderByPriceDesc(
            Long stockId,
            OrderType orderType,
            OrderStatus status
    );

    List<Order> findTop5ByStockIdAndOrderTypeAndStatusOrderByPriceAsc(
            Long stockId,
            OrderType orderType,
            OrderStatus status
    );
}