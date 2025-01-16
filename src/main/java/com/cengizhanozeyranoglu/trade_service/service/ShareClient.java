package com.cengizhanozeyranoglu.trade_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "share-service")
public interface ShareClient {

    @GetMapping(path = "/rest/api/share/{symbol}/exist")
    boolean isShareSymbolExist(@PathVariable(name = "symbol") String symbol);

    @GetMapping(path = "/rest/api/share/get/Price/{symbol}")
    BigDecimal getSharePrice(@PathVariable(name = "symbol") String symbol);
}
