package com.ticker.binanceapi.model;

public class CombinedPriceDetails {
    private String symbol;
    private String avgPrice;
    private String tickerPrice;
    public CombinedPriceDetails() {
    }
    // Constructor
    public CombinedPriceDetails(String symbol, String avgPrice, String tickerPrice) {
        this.symbol = symbol;
        this.avgPrice = avgPrice;
        this.tickerPrice = tickerPrice;
    }

    // Getters and Setters
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getTickerPrice() {
        return tickerPrice;
    }

    public void setTickerPrice(String tickerPrice) {
        this.tickerPrice = tickerPrice;
    }
}
