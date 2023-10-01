package com.ticker.binanceapi.controller;

import com.ticker.binanceapi.service.BinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/price")
public class PriceController {

    @Autowired
        private BinanceService binanceService;

    @GetMapping("/{symbol}")
    public String getCurrentPrice(@PathVariable String symbol) {
        return binanceService.getCurrentPrice(symbol);
    }
}
