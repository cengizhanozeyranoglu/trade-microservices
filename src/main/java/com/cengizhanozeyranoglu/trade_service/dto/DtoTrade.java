package com.cengizhanozeyranoglu.trade_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoTrade {

    private Long customerId;

    private Long portfolioId;

    private String shareSymbol;

    private String tradeType;

    private BigDecimal quantity;
}
