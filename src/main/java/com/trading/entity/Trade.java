package com.trading.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trades")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buy_order_id")
    private Order buyOrder;

    @ManyToOne
    @JoinColumn(name = "sell_order_id")
    private Order sellOrder;

    private BigDecimal price;
    private Integer quantity;
    private LocalDateTime executedAt;

    @PrePersist
    public void prePersist() {
        executedAt = LocalDateTime.now();
    }
}