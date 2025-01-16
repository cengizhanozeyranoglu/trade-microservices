package com.cengizhanozeyranoglu.trade_service.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DtoPortfolio {

    private Long customerId;

    private String portfolioName;

    private Map<String,Integer> shares;
}
