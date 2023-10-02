package com.ticker.binanceapi.model;

public class MarketDataModel {
    private String symbol;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
