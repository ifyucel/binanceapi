package com.ticker.binanceapi.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.ticker.binanceapi.repository.MarketDataRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class BinanceService {

    private OkHttpClient client = new OkHttpClient();

    @Autowired
    private MarketDataRepository marketDataRepository;

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

    public String getAllPrices() {
        Request request = new Request.Builder()
                .url("https://api.binance.com/api/v3/ticker/price")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void savePrice(MarketDataModel marketDataModel) {
        marketDataRepository.save(marketDataModel);
    }

    public void saveAllPrices() {
        String allPricesJson = getAllPrices();
        Type listType = new TypeToken<ArrayList<MarketDataModel>>(){}.getType();
        List<MarketDataModel> allPrices = new Gson().fromJson(allPricesJson, listType);
        for (MarketDataModel marketDataModel : allPrices) {
            savePrice(marketDataModel);
        }
    }
}
