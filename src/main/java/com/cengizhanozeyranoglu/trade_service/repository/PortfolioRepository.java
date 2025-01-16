package com.cengizhanozeyranoglu.trade_service.repository;

import com.cengizhanozeyranoglu.trade_service.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio,Long> {

    public Optional<Portfolio> findByCustomerIdAndPortfolioName(Long customerId, String portfolioName);
}
