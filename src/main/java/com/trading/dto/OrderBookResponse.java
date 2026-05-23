package com.trading.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderBookResponse {

    private List<OrderResponse> buyOrders;

    private List<OrderResponse> sellOrders;
}