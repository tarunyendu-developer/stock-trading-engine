package com.trading.service;

import com.trading.dto.OrderBookResponse;
import com.trading.dto.OrderRequest;
import com.trading.dto.OrderResponse;
import com.trading.entity.Order;
import com.trading.entity.Stock;
import com.trading.entity.User;
import com.trading.enums.OrderStatus;
import com.trading.enums.OrderType;
import com.trading.repository.OrderRepository;
import com.trading.repository.StockRepository;
import com.trading.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final MatchingService matchingService;

    public OrderResponse placeBuyOrder(OrderRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Stock stock = stockRepository.findById(request.getStockId())
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        Order order = Order.builder()
                .user(user)
                .stock(stock)
                .orderType(OrderType.BUY)
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .remainingQuantity(request.getQuantity())
                .status(OrderStatus.OPEN)
                .build();

        Order savedOrder = orderRepository.save(order);

        matchingService.matchBuyOrder(savedOrder);

        log.info("BUY Order Placed Successfully");

        return OrderResponse.builder()
                .orderId(savedOrder.getId())
                .userName(savedOrder.getUser().getName())
                .stockSymbol(savedOrder.getStock().getSymbol())
                .orderType(savedOrder.getOrderType())
                .price(savedOrder.getPrice())
                .quantity(savedOrder.getQuantity())
                .remainingQuantity(savedOrder.getRemainingQuantity())
                .status(savedOrder.getStatus())
                .build();
    }

    public OrderResponse placeSellOrder(OrderRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Stock stock = stockRepository.findById(request.getStockId())
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        Order order = Order.builder()
                .user(user)
                .stock(stock)
                .orderType(OrderType.SELL)
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .remainingQuantity(request.getQuantity())
                .status(OrderStatus.OPEN)
                .build();

        Order savedOrder = orderRepository.save(order);

        matchingService.matchSellOrder(savedOrder);

        log.info("SELL Order Placed Successfully");

        return OrderResponse.builder()
                .orderId(savedOrder.getId())
                .userName(savedOrder.getUser().getName())
                .stockSymbol(savedOrder.getStock().getSymbol())
                .orderType(savedOrder.getOrderType())
                .price(savedOrder.getPrice())
                .quantity(savedOrder.getQuantity())
                .remainingQuantity(savedOrder.getRemainingQuantity())
                .status(savedOrder.getStatus())
                .build();
    }

    public List<Order> getOrdersByUser(Long userId) {

        return orderRepository.findByUserId(userId);
    }

    public String cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.FILLED) {

            return "Filled order cannot be cancelled";
        }

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        log.info("Order Cancelled Successfully");

        return "Order cancelled successfully";
    }

    public OrderBookResponse getOrderBook(Long stockId) {

        List<OrderResponse> buyOrders = orderRepository
                .findByStockIdAndOrderTypeAndStatusOrderByPriceDesc(stockId, OrderType.BUY, OrderStatus.OPEN)
                .stream()
                .map(order -> OrderResponse.builder()
                        .orderId(order.getId())
                        .userName(order.getUser().getName())
                        .stockSymbol(order.getStock().getSymbol())
                        .orderType(order.getOrderType())
                        .price(order.getPrice())
                        .quantity(order.getQuantity())
                        .remainingQuantity(order.getRemainingQuantity())
                        .status(order.getStatus())
                        .build())
                .collect(Collectors.toList());

        List<OrderResponse> sellOrders = orderRepository
                .findByStockIdAndOrderTypeAndStatusOrderByPriceAsc(stockId, OrderType.SELL, OrderStatus.OPEN)
                .stream()
                .map(order -> OrderResponse.builder()
                        .orderId(order.getId())
                        .userName(order.getUser().getName())
                        .stockSymbol(order.getStock().getSymbol())
                        .orderType(order.getOrderType())
                        .price(order.getPrice())
                        .quantity(order.getQuantity())
                        .remainingQuantity(order.getRemainingQuantity())
                        .status(order.getStatus())
                        .build())
                .collect(Collectors.toList());

        return OrderBookResponse.builder().buyOrders(buyOrders).sellOrders(sellOrders).build();
    }
}