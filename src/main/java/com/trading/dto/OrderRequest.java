package com.trading.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrderRequest {

    private Long userId;
    private Long stockId;
    private BigDecimal price;
    private Integer quantity;
}