package com.ticker.binanceapi.model;

import java.util.List;

public class ExchangeInfo {
    private List<SymbolInfo> symbols;

    // Getters and setters
    public List<SymbolInfo> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<SymbolInfo> symbols) {
        this.symbols = symbols;
    }
}
