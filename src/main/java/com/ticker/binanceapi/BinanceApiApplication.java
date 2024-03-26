package com.ticker.binanceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BinanceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinanceApiApplication.class, args);
	}
}

