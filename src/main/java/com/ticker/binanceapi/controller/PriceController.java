package com.ticker.binanceapi.controller;

import com.ticker.binanceapi.model.BinanceService;
import com.ticker.binanceapi.model.MarketDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/price")
public class PriceController {

    @Autowired
    private BinanceService binanceService;

    @GetMapping("/{symbol}")
    public String getCurrentPrice(@PathVariable String symbol) {
        return binanceService.getCurrentPrice(symbol);
    }

    @GetMapping("/all")
    public String getAllPrices() {
        return binanceService.getAllPrices();
    }

    @GetMapping("/saveAll")
    public String saveAllPrices() {
        binanceService.saveAllPrices();
        return "All prices saved successfully!";
    }
}
