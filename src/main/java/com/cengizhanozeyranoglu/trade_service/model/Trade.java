package com.cengizhanozeyranoglu.trade_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "portfolio_id")
    private Long portfolioId;

    @Column(name = "share_symbol")
    private String shareSymbol;

    @Column(name = "trade_type")
    private String tradeType;

    private BigDecimal quantity;

    private BigDecimal price;

    @Column(name = "trade_time")
    private LocalDateTime tradeTime;
}
