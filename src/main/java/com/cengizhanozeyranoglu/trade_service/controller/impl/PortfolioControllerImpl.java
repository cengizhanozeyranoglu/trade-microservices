package com.cengizhanozeyranoglu.trade_service.controller.impl;

import com.cengizhanozeyranoglu.trade_service.controller.IPortfolioController;
import com.cengizhanozeyranoglu.trade_service.dto.DtoPortfolio;
import com.cengizhanozeyranoglu.trade_service.service.IPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rest/api/portfolio")
public class PortfolioControllerImpl implements IPortfolioController {

    @Autowired
    private IPortfolioService portfolioService;

    @PostMapping(path = "/create")
    @Override
    public DtoPortfolio createPortfolio(@RequestBody DtoPortfolio portfolio) {
        return portfolioService.createPortfolio(portfolio);
    }

    @PostMapping(path = "/addShare/{id}")
    @Override
    public DtoPortfolio addShareToPortfolio(@PathVariable(name = "id") Long id, @RequestBody DtoPortfolio portfolio) {
        return portfolioService.addShareToPortfolio(id, portfolio);
    }

    @PostMapping(path = "/removeShare/{id}")
    @Override
    public DtoPortfolio removeShareToPortfolio(@PathVariable(name = "id") Long id, @RequestBody DtoPortfolio portfolio) {
        return portfolioService.removeShareToPortfolio(id, portfolio);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deletePortfolio(@PathVariable(name = "id") Long id) {
        portfolioService.deletePortfolio(id);
    }
}
