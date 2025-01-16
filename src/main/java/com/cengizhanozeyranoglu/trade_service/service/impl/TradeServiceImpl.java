package com.cengizhanozeyranoglu.trade_service.service.impl;

import com.cengizhanozeyranoglu.trade_service.dto.DtoTrade;
import com.cengizhanozeyranoglu.trade_service.mapper.TradeMapper;
import com.cengizhanozeyranoglu.trade_service.model.Portfolio;
import com.cengizhanozeyranoglu.trade_service.model.Trade;
import com.cengizhanozeyranoglu.trade_service.repository.PortfolioRepository;
import com.cengizhanozeyranoglu.trade_service.repository.TradeRepository;
import com.cengizhanozeyranoglu.trade_service.service.CustomerClient;
import com.cengizhanozeyranoglu.trade_service.service.ITradeService;
import com.cengizhanozeyranoglu.trade_service.service.ShareClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class TradeServiceImpl implements ITradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private ShareClient shareClient;

    @Override
    public DtoTrade buy(DtoTrade dtoTrade) {
        log.info("Processing buy trade for customer id :{}", dtoTrade.getCustomerId());

        if (!validateCustomerAndPortfolio(dtoTrade)) {
            log.warn("Buy validation failed for customer id {} or portfolio id {}",
                    dtoTrade.getCustomerId(), dtoTrade.getPortfolioId());
            return null;
        }
        Optional<Portfolio> optPortfolio = portfolioRepository.findById(dtoTrade.getPortfolioId());
        BigDecimal customerBalance = getCustomerBalance(dtoTrade);
        BigDecimal totalCost = getSharePrice(dtoTrade).multiply(dtoTrade.getQuantity());

        if (customerBalance.compareTo(totalCost) < 0) {
            log.warn("Insufficient balance for trade");
            return null;
        }

        Portfolio dbPortfolio = optPortfolio.get();
        Map<String, Integer> shares = dbPortfolio.getShares();

        if (isShareSymbolExist(dtoTrade)) {
            shares.put(dtoTrade.getShareSymbol(),
                    shares.getOrDefault(dtoTrade.getShareSymbol(), 0) + dtoTrade.getQuantity().intValue());
            dbPortfolio.setShares(shares);
            dbPortfolio.setCustomerId(dtoTrade.getCustomerId());
            Portfolio savedPortfolio = portfolioRepository.save(dbPortfolio);
        }
        Trade trade = createTrade(dtoTrade, "BUY", totalCost);
        Trade savedTrade = tradeRepository.save(trade);

        BigDecimal newBalance = customerBalance.subtract(totalCost);
        customerClient.updateBalance(dtoTrade.getCustomerId(), newBalance);
        return TradeMapper.toDtoTrade(savedTrade);
    }

    @Override
    public DtoTrade sell(DtoTrade dtoTrade) {
        log.info("Processing sell trade for customer id :{}", dtoTrade.getCustomerId());

        if (!validateCustomerAndPortfolio(dtoTrade)) {
            log.warn("Sell validation failed for customer id {} or portfolio id {}",
                    dtoTrade.getCustomerId(), dtoTrade.getPortfolioId());
            return null;
        }

        Optional<Portfolio> optPortfolio = portfolioRepository.findById(dtoTrade.getPortfolioId());

        if (!isShareSymbolExist(dtoTrade)) {
            log.warn("Share symbol:" + dtoTrade.getShareSymbol() + "does not exist");
            return null;
        }
        Portfolio dbPortfolio = optPortfolio.get();
        Map<String, Integer> shares = dbPortfolio.getShares();

        if (shares.get(dtoTrade.getShareSymbol()) < dtoTrade.getQuantity().intValue()) {
            log.warn("Not enough share to sell for symbol:" + dtoTrade.getShareSymbol());
            return null;
        }
        BigDecimal totalIncome = getSharePrice(dtoTrade).multiply(dtoTrade.getQuantity());
        BigDecimal updatedBalance = getCustomerBalance(dtoTrade).add(totalIncome);

        shares.put(dtoTrade.getShareSymbol(),
                shares.get(dtoTrade.getShareSymbol()) - dtoTrade.getQuantity().intValue());
        if (shares.get(dtoTrade.getShareSymbol()) == 0) {
            shares.remove(dtoTrade.getShareSymbol());
        }
        dbPortfolio.setShares(shares);
        Portfolio savedPortfolio = portfolioRepository.save(dbPortfolio);

        Trade trade = createTrade(dtoTrade, "SELL", totalIncome);
        Trade savedTrade = tradeRepository.save(trade);

        customerClient.updateBalance(dtoTrade.getCustomerId(), updatedBalance);

        return TradeMapper.toDtoTrade(savedTrade);
    }

    public boolean validateCustomerAndPortfolio(DtoTrade dtoTrade) {
        boolean isCustomerAvailable = customerClient.isCustomerAvailable(dtoTrade.getCustomerId());
        Optional<Portfolio> optPortfolio = portfolioRepository.findById(dtoTrade.getPortfolioId());
        if (isCustomerAvailable && optPortfolio.isPresent()) {
            return true;
        } else
            log.warn("Check customer id:" + dtoTrade.getCustomerId() + "and portfolio id:" + dtoTrade.getPortfolioId());
        return false;
    }

    public BigDecimal getSharePrice(DtoTrade dtoTrade) {
        return shareClient.getSharePrice(dtoTrade.getShareSymbol());
    }

    public BigDecimal getCustomerBalance(DtoTrade dtoTrade) {
        return customerClient.getCustomerBalance(dtoTrade.getCustomerId());
    }

    public boolean isShareSymbolExist(DtoTrade dtoTrade) {
        return shareClient.isShareSymbolExist(dtoTrade.getShareSymbol());
    }

    public Trade createTrade(DtoTrade dtoTrade, String tradeType, BigDecimal price) {
        return Trade.builder()
                .customerId(dtoTrade.getCustomerId())
                .portfolioId(dtoTrade.getPortfolioId())
                .shareSymbol(dtoTrade.getShareSymbol())
                .tradeType(tradeType)
                .quantity(dtoTrade.getQuantity())
                .price(price)
                .tradeTime(LocalDateTime.now())
                .build();
    }
}
