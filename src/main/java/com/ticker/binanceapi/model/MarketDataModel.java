package com.ticker.binanceapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MarketDataModel {
    @Id
    private String symbol;
    private String price;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
