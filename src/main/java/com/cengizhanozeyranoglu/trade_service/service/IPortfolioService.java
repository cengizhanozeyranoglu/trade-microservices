package com.cengizhanozeyranoglu.trade_service.service;

import com.cengizhanozeyranoglu.trade_service.dto.DtoPortfolio;

public interface IPortfolioService {

    public DtoPortfolio createPortfolio(DtoPortfolio dtoPortfolio);

    public DtoPortfolio addShareToPortfolio(Long id, DtoPortfolio dtoPortfolio);

    public DtoPortfolio removeShareToPortfolio(Long id, DtoPortfolio dtoPortfolio);

    public void deletePortfolio (Long id);
}
