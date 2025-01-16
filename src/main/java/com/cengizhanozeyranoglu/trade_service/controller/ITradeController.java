package com.cengizhanozeyranoglu.trade_service.controller;

import com.cengizhanozeyranoglu.trade_service.dto.DtoTrade;

public interface ITradeController {

    public DtoTrade buy (DtoTrade dtoTrade);

    public DtoTrade sell(DtoTrade dtoTrade);
}
