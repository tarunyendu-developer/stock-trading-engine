package com.trading.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class StockRequest {

    private String symbol;
    private String companyName;
}