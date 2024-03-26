package com.ticker.binanceapi.model;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class BinanceScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(BinanceScheduledTasks.class);

    @Autowired
    private BinanceService binanceService;

    @Scheduled(cron = "*/10 * * * * *")
    public void fetchAndSaveMarketDataEveryTenSeconds() {
        logger.info("Starting fetchAndSaveMarketDataEveryTenSeconds...");
        try {
            List<String> symbols = binanceService.fetchAllSymbols();
            binanceService.fetchAndSaveMarketData(symbols);
            logger.info("Finished fetchAndSaveMarketDataEveryTenSeconds.");
        } catch (IOException e) {
            logger.error("Error in fetchAndSaveMarketDataEveryTenSeconds: ", e);
        }
    }
}
