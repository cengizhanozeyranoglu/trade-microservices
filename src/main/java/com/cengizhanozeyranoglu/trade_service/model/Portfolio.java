package com.cengizhanozeyranoglu.trade_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private String portfolioName;

    @ElementCollection
    @CollectionTable(name = "portfolio_shares", joinColumns = @JoinColumn(name = "portfolio_id"))
    @MapKeyColumn(name = "share_symbol")
    @Column(name = "quantity")
    private Map<String,Integer> shares;


}
