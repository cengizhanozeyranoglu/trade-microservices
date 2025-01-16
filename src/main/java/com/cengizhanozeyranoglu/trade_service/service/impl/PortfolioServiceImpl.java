package com.cengizhanozeyranoglu.trade_service.service.impl;

import com.cengizhanozeyranoglu.trade_service.dto.DtoPortfolio;
import com.cengizhanozeyranoglu.trade_service.mapper.PortfolioMapper;
import com.cengizhanozeyranoglu.trade_service.model.Portfolio;
import com.cengizhanozeyranoglu.trade_service.repository.PortfolioRepository;
import com.cengizhanozeyranoglu.trade_service.service.CustomerClient;
import com.cengizhanozeyranoglu.trade_service.service.IPortfolioService;
import com.cengizhanozeyranoglu.trade_service.service.ShareClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class PortfolioServiceImpl implements IPortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private CustomerClient customerClient;
    @Autowired
    private ShareClient shareClient;

    @Override
    public DtoPortfolio createPortfolio(DtoPortfolio dtoPortfolio) {
        Optional<Portfolio> optPortfolio = portfolioRepository.findByCustomerIdAndPortfolioName
                (dtoPortfolio.getCustomerId(),
                        dtoPortfolio.getPortfolioName());
        if (optPortfolio.isPresent()) {
            throw new RuntimeException("Portfolio already exists");
        } else {
            Portfolio portfolio = new Portfolio();
            Portfolio portfolioNew = portfolioRepository.save(portfolio.toBuilder()
                    .portfolioName(dtoPortfolio.getPortfolioName())
                    .shares(dtoPortfolio.getShares())
                    .customerId(dtoPortfolio.getCustomerId())
                    .build());
            DtoPortfolio dtoPortfolio1 = PortfolioMapper.toDtoPortfolio(portfolioNew);
            return dtoPortfolio1;
        }
    }

    @Override
    public DtoPortfolio addShareToPortfolio(Long id, DtoPortfolio dtoPortfolio) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(id);
        if (portfolio.isPresent()) {
            Portfolio dbPortfolio = portfolio.get();
            boolean isCustomerExists = customerClient.isCustomerAvailable(dtoPortfolio.getCustomerId());
            if (isCustomerExists) {
                Map<String, Integer> shares = dbPortfolio.getShares();
                dtoPortfolio.getShares().forEach((shareSymbol, quantity) -> {
                    if (shareClient.isShareSymbolExist(shareSymbol))
                        shares.put(shareSymbol, shares.getOrDefault(shareSymbol, 0) + quantity);
                });
                dbPortfolio.setShares(shares);
                Portfolio savedPortfolio = portfolioRepository.save(dbPortfolio);
                return PortfolioMapper.toDtoPortfolio(savedPortfolio);
            } else throw new RuntimeException("Customer with ID" + dtoPortfolio.getCustomerId() + " not found");
        } else throw new RuntimeException("Portfolio with ID " + id + "not found");
    }


    public DtoPortfolio removeShareToPortfolio(Long id, DtoPortfolio dtoPortfolio) {
        Optional<Portfolio> optPortfolio = portfolioRepository.findById(id);
        if (optPortfolio.isEmpty()) {
            throw new RuntimeException("Portfolio with ID" + id + "not found");
        }
        boolean isCustomerExists = customerClient.isCustomerAvailable(dtoPortfolio.getCustomerId());
        if (!isCustomerExists) {
            throw new RuntimeException("Customer with ID" + dtoPortfolio.getCustomerId() + "not found");
        }
        Portfolio dbPortfolio = optPortfolio.get();
        Map<String, Integer> shares = dbPortfolio.getShares();
        dtoPortfolio.getShares().forEach((shareSymbol, quantityToRemove) -> {
            if (!shares.containsKey(shareSymbol)) {
                throw new RuntimeException("Share symbol" + shareSymbol + "not found in Portfolio");
            }
            int currentQuantity = shares.get(shareSymbol);

            if (currentQuantity < quantityToRemove) {
                throw new RuntimeException("Not enough shares of" + shareSymbol + "to remove. " +
                        "Current:" + currentQuantity + ", Requested" + quantityToRemove);
            }
            int newQuantity = currentQuantity - quantityToRemove;
            if (newQuantity > 0) {
                shares.put(shareSymbol, newQuantity);
            } else {
                shares.remove(shareSymbol);
            }
        });
        dbPortfolio.setShares(shares);
        Portfolio savedPortfolio = portfolioRepository.save(dbPortfolio);
        return PortfolioMapper.toDtoPortfolio(savedPortfolio);
    }

    @Override
    public void deletePortfolio(Long id) {
        Optional<Portfolio> optPortfolio = portfolioRepository.findById(id);
        if (optPortfolio.isEmpty()) {
            throw new RuntimeException("Portfolio with ID" + id + "not found");
        } else {
            Portfolio portfolio = optPortfolio.get();
            portfolioRepository.delete(portfolio);
        }

    }

}