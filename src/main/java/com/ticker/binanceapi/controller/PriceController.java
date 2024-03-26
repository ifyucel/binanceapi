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

    // Updated method to accept a list of symbols
    @GetMapping("/api/price/save/{symbols}")
    public List<MarketDataModel> saveMarketData(@PathVariable List<String> symbols) {
        return binanceService.fetchAndSaveMarketData(symbols);
    }

    @GetMapping("/api/price/save/all")
    public String saveMarketDataForAllSymbols() {
        try {
            List<String> symbols = binanceService.fetchAllSymbols();
            // Pass the list of symbols to the updated method
            binanceService.fetchAndSaveMarketData(symbols);
            return "Market data for all symbols has been saved successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred while fetching market data for all symbols.";
        }
    }
}
