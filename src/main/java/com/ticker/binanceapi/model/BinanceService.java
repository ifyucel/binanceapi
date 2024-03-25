package com.ticker.binanceapi.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.ticker.binanceapi.repository.MarketDataRepository;
import jakarta.transaction.Transactional;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BinanceService {

    private OkHttpClient client = new OkHttpClient();
    private final Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private MarketDataRepository marketDataRepository;

    public List<String> fetchAllSymbols() throws IOException {
        String url = "https://api.binance.com/api/v3/ticker/price";
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseData = response.body().string();
            JsonArray jsonArray = gson.fromJson(responseData, JsonArray.class);
            List<String> symbols = new ArrayList<>();
            for (JsonElement element : jsonArray) {
                symbols.add(element.getAsJsonObject().get("symbol").getAsString());
            }
            return symbols;
        }
    }

    public MarketDataModel fetchAndSaveMarketData(String symbol) {
        String url = "https://api.binance.com/api/v3/ticker?symbol=" + symbol;
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseData = response.body().string();
            MarketDataModel marketData = gson.fromJson(responseData, MarketDataModel.class);
            marketData.setTimestamp(new Date());
            marketDataRepository.save(marketData);
            return marketData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
