package com.trading.controller;

import com.trading.dto.ApiResponse;
import com.trading.dto.OrderBookResponse;
import com.trading.dto.OrderRequest;
import com.trading.dto.OrderResponse;
import com.trading.entity.Order;
import com.trading.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor

public class OrderController {

    private final OrderService orderService;

    @PostMapping("/buy")
    public ApiResponse<OrderResponse> placeBuyOrder(@Valid @RequestBody OrderRequest request) {

        OrderResponse response = orderService.placeBuyOrder(request);

        return ApiResponse.<OrderResponse>builder()
                .success(true)
                .message("BUY Order Placed Successfully")
                .data(response)
                .build();
    }

    @PostMapping("/sell")
    public ApiResponse<OrderResponse> placeSellOrder(@Valid @RequestBody OrderRequest request) {

        OrderResponse response = orderService.placeSellOrder(request);

        return ApiResponse.<OrderResponse>builder()
                .success(true)
                .message("SELL Order Placed Successfully")
                .data(response)
                .build();
    }

    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    @DeleteMapping("/{orderId}")
    public String cancelOrder(@PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    @GetMapping("/orderbook/{stockId}")
    public OrderBookResponse getOrderBook(@PathVariable Long stockId) {
        return orderService.getOrderBook(stockId);
    }
}