package com.cengizhanozeyranoglu.trade_service.repository;

import com.cengizhanozeyranoglu.trade_service.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade,Long> {
}
