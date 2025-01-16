package com.cengizhanozeyranoglu.trade_service.service;

import com.cengizhanozeyranoglu.trade_service.dto.DtoTrade;

public interface ITradeService {

    public DtoTrade buy (DtoTrade dtoTrade);

    public DtoTrade sell (DtoTrade dtoTrade);


}
