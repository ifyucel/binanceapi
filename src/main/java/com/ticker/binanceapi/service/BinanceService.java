package com.ticker.binanceapi.service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BinanceService {

    private OkHttpClient client = new OkHttpClient();

    public String getCurrentPrice(String symbol) {
        Request request = new Request.Builder()
                .url("https://api.binance.com/api/v3/ticker/price?symbol=" + symbol)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
