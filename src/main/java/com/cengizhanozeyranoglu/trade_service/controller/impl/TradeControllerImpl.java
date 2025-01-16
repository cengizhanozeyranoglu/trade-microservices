package com.cengizhanozeyranoglu.trade_service.controller.impl;

import com.cengizhanozeyranoglu.trade_service.controller.ITradeController;
import com.cengizhanozeyranoglu.trade_service.dto.DtoTrade;
import com.cengizhanozeyranoglu.trade_service.service.ITradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rest/api/trade")
public class TradeControllerImpl implements ITradeController {

    @Autowired
    private ITradeService tradeService;

    @PostMapping(path = "/buy")
    @Override
    public DtoTrade buy(@RequestBody DtoTrade dtoTrade) {
        return tradeService.buy(dtoTrade);
    }

    @PostMapping(path = "/sell")
    @Override
    public DtoTrade sell(@RequestBody DtoTrade dtoTrade){
        return tradeService.sell(dtoTrade);
    }
}
