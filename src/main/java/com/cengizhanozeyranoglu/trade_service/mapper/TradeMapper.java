package com.cengizhanozeyranoglu.trade_service.mapper;

import com.cengizhanozeyranoglu.trade_service.dto.DtoTrade;
import com.cengizhanozeyranoglu.trade_service.model.Trade;

public class TradeMapper {

    public static DtoTrade toDtoTrade(Trade trade) {
        DtoTrade dtoTrade = DtoTrade.builder()
                .customerId(trade.getCustomerId())
                .portfolioId(trade.getPortfolioId())
                .shareSymbol(trade.getShareSymbol())
                .tradeType(trade.getTradeType())
                .quantity(trade.getQuantity())
                .build();
        return dtoTrade;
    }
}
