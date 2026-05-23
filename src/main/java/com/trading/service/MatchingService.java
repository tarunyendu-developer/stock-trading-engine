package com.trading.service;

import com.trading.entity.Order;
import com.trading.entity.Trade;
import com.trading.enums.OrderStatus;
import com.trading.enums.OrderType;
import com.trading.repository.OrderRepository;
import com.trading.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MatchingService {

    private final OrderRepository orderRepository;
    private final TradeRepository tradeRepository;

    public void matchBuyOrder(Order buyOrder) {

        List<Order> sellOrders =
                orderRepository.findByStockIdAndOrderTypeAndStatusAndPriceLessThanEqualOrderByPriceAscCreatedAtAsc(
                        buyOrder.getStock().getId(),
                        OrderType.SELL,
                        OrderStatus.OPEN,
                        buyOrder.getPrice()
                );

        for (Order sellOrder : sellOrders) {

            if (buyOrder.getRemainingQuantity() == 0) {
                break;
            }

            int matchedQuantity = Math.min(
                    buyOrder.getRemainingQuantity(),
                    sellOrder.getRemainingQuantity()
            );

            buyOrder.setRemainingQuantity(
                    buyOrder.getRemainingQuantity() - matchedQuantity
            );

            sellOrder.setRemainingQuantity(
                    sellOrder.getRemainingQuantity() - matchedQuantity
            );

            if (buyOrder.getRemainingQuantity() == 0) {
                buyOrder.setStatus(OrderStatus.FILLED);
            } else {
                buyOrder.setStatus(OrderStatus.PARTIAL);
            }

            if (sellOrder.getRemainingQuantity() == 0) {
                sellOrder.setStatus(OrderStatus.FILLED);
            } else {
                sellOrder.setStatus(OrderStatus.PARTIAL);
            }

            orderRepository.save(buyOrder);
            orderRepository.save(sellOrder);

            Trade trade = Trade.builder()
                    .buyOrder(buyOrder)
                    .sellOrder(sellOrder)
                    .price(sellOrder.getPrice())
                    .quantity(matchedQuantity)
                    .build();

            tradeRepository.save(trade);

            log.info("BUY Order Matched Successfully");
            log.info("Trade Executed Quantity: {}", matchedQuantity);
        }
    }

    public void matchSellOrder(Order sellOrder) {

        List<Order> buyOrders =
                orderRepository.findByStockIdAndOrderTypeAndStatusAndPriceGreaterThanEqualOrderByPriceDescCreatedAtAsc(
                        sellOrder.getStock().getId(),
                        OrderType.BUY,
                        OrderStatus.OPEN,
                        sellOrder.getPrice()
                );

        for (Order buyOrder : buyOrders) {

            if (sellOrder.getRemainingQuantity() == 0) {
                break;
            }

            int matchedQuantity = Math.min(
                    sellOrder.getRemainingQuantity(),
                    buyOrder.getRemainingQuantity()
            );

            sellOrder.setRemainingQuantity(
                    sellOrder.getRemainingQuantity() - matchedQuantity
            );

            buyOrder.setRemainingQuantity(
                    buyOrder.getRemainingQuantity() - matchedQuantity
            );

            if (sellOrder.getRemainingQuantity() == 0) {
                sellOrder.setStatus(OrderStatus.FILLED);
            } else {
                sellOrder.setStatus(OrderStatus.PARTIAL);
            }

            if (buyOrder.getRemainingQuantity() == 0) {
                buyOrder.setStatus(OrderStatus.FILLED);
            } else {
                buyOrder.setStatus(OrderStatus.PARTIAL);
            }

            orderRepository.save(sellOrder);
            orderRepository.save(buyOrder);

            Trade trade = Trade.builder()
                    .buyOrder(buyOrder)
                    .sellOrder(sellOrder)
                    .price(sellOrder.getPrice())
                    .quantity(matchedQuantity)
                    .build();

            tradeRepository.save(trade);

            log.info("SELL Order Matched Successfully");
            log.info("Trade Executed Quantity: {}", matchedQuantity);
        }
    }
}