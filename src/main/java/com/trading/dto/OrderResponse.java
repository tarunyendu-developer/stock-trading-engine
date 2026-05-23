package com.trading.dto;

import com.trading.enums.OrderStatus;
import com.trading.enums.OrderType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderResponse {

    private Long orderId;
    private String userName;
    private String stockSymbol;
    private OrderType orderType;
    private BigDecimal price;
    private Integer quantity;
    private Integer remainingQuantity;
    private OrderStatus status;
}