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
import java.util.Date;
import java.util.List;

@Service
public class BinanceService {

    private OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
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
        marketDataModel.setTimestamp(new Date());
        marketDataRepository.save(marketDataModel);
    }

    public void saveAllPrices() {
        String allPricesJson = getAllPrices();
        Type listType = new TypeToken<ArrayList<MarketDataResponse>>(){}.getType();
        List<MarketDataResponse> allPrices = new Gson().fromJson(allPricesJson, listType);
        for (MarketDataResponse marketDataResponse : allPrices) {
            MarketDataModel marketDataModel = new MarketDataModel();
            marketDataModel.setSymbol(marketDataResponse.getSymbol());
            marketDataModel.setPrice(marketDataResponse.getPrice());
            savePrice(marketDataModel);
        }
    }

    public List<SymbolInfo> getAllSymbols() throws IOException {
        Request request = new Request.Builder()
                .url("https://api.binance.com/api/v3/exchangeInfo")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseData = response.body().string();
            ExchangeInfo exchangeInfo = gson.fromJson(responseData, ExchangeInfo.class);
            return exchangeInfo.getSymbols();
        }
    }
}
