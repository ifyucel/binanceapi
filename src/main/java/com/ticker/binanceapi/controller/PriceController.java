package com.ticker.binanceapi.controller;

import com.ticker.binanceapi.model.BinanceService;
import com.ticker.binanceapi.model.MarketDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class PriceController {

    @Autowired
    private BinanceService binanceService;

    @GetMapping("/api/price/save/{symbol}")
    public MarketDataModel saveMarketData(@PathVariable String symbol) {
        return binanceService.fetchAndSaveMarketData(symbol);
    }

    @GetMapping("/api/price/save/all")
    public String saveMarketDataForAllSymbols() {
        try {
            List<String> symbols = binanceService.fetchAllSymbols();
            for (String symbol : symbols) {
                binanceService.fetchAndSaveMarketData(symbol);
            }
            return "Market data for all symbols has been saved successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred while fetching market data for all symbols.";
        }
    }
}
