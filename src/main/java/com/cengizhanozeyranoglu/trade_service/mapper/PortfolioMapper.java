package com.cengizhanozeyranoglu.trade_service.mapper;

import com.cengizhanozeyranoglu.trade_service.dto.DtoPortfolio;
import com.cengizhanozeyranoglu.trade_service.model.Portfolio;

public class PortfolioMapper {

    public static DtoPortfolio toDtoPortfolio(Portfolio portfolio) {
        DtoPortfolio dtoPortfolio = DtoPortfolio.builder()
                .customerId(portfolio.getCustomerId())
                .portfolioName(portfolio.getPortfolioName())
                .shares(portfolio.getShares())
                .build();
        return dtoPortfolio;
    }
}
