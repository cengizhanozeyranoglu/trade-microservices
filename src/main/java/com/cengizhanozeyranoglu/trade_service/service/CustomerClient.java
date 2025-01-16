package com.cengizhanozeyranoglu.trade_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "customer-service")
public interface CustomerClient {

    @GetMapping(path = "/rest/api/customer/{id}/exists")
    boolean isCustomerAvailable(@PathVariable(name = "id") Long customerId);

    @GetMapping(path = "/rest/api/customer/getBalance/{id}")
    BigDecimal getCustomerBalance(@PathVariable(name = "id") Long customerId);

    @PutMapping(path = "rest/api/customer/updateBalance/{id}")
    BigDecimal updateBalance(@PathVariable(name = "id") Long id, @RequestParam BigDecimal balance);




}
