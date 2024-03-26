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
import java.util.stream.Collectors;

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

    public List<MarketDataModel> fetchAndSaveMarketData(List<String> symbols) {
        String url = "https://api.binance.com/api/v3/ticker?symbols=[";
        String encodedSymbols = symbols.stream()
                .map(symbol -> "\"" + symbol + "\"")
                .collect(Collectors.joining(","));
        url += encodedSymbols + "]";
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseData = response.body().string();
            JsonArray jsonArray = gson.fromJson(responseData, JsonArray.class);
            List<MarketDataModel> marketDataList = new ArrayList<>();
            for (JsonElement element : jsonArray) {
                MarketDataModel marketData = gson.fromJson(element, MarketDataModel.class);
                marketData.setTimestamp(new Date());
                marketDataRepository.save(marketData);
                marketDataList.add(marketData);
            }
            return marketDataList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
