package com.cengizhanozeyranoglu.trade_service.controller;

import com.cengizhanozeyranoglu.trade_service.dto.DtoPortfolio;

public interface IPortfolioController {

    public DtoPortfolio createPortfolio(DtoPortfolio portfolio);

    public DtoPortfolio addShareToPortfolio(Long id, DtoPortfolio portfolio);

    public DtoPortfolio removeShareToPortfolio(Long id, DtoPortfolio portfolio);

    public void deletePortfolio(Long id);
}
