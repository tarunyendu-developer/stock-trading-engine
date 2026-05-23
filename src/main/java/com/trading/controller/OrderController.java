package com.trading.controller;

import com.trading.dto.OrderBookResponse;
import com.trading.dto.OrderRequest;
import com.trading.dto.OrderResponse;
import com.trading.entity.Order;
import com.trading.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor

public class OrderController {

    private final OrderService orderService;

    @PostMapping("/buy")
    public OrderResponse placeBuyOrder(@RequestBody OrderRequest request){
        return orderService.placeBuyOrder(request);
    }

    @PostMapping("/sell")
    public OrderResponse placeSellOrder(@RequestBody OrderRequest request) {
        return orderService.placeSellOrder(request);
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