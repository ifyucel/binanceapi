package com.ticker.binanceapi.model;

import com.google.gson.*;
import com.ticker.binanceapi.repository.MarketDataRepository;
import jakarta.transaction.Transactional;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
                String symbol = element.getAsJsonObject().get("symbol").getAsString();
                if (symbol.endsWith("USDT")) {
                    symbols.add(symbol);
                }
            }
            // Count and print the number of symbols ending with "USDT"
            long count = symbols.stream().filter(s -> s.endsWith("USDT")).count();
            System.out.println("Total number of symbols ending with USDT: " + count);

            return symbols;
        }
    }

    public List<MarketDataModel> fetchAndSaveMarketData(List<String> symbols) {
        List<MarketDataModel> marketDataList = new ArrayList<>();
        int batchSize = 100;

        for (int i = 0; i < symbols.size(); i += batchSize) {
            int start = i;
            int end = Math.min(start + batchSize, symbols.size());
            List<String> batchSymbols = symbols.subList(start, end);

            try {
                // URL encode the symbols and format them as a JSON array
                String encodedSymbols = URLEncoder.encode("[\"" + String.join("\",\"", batchSymbols) + "\"]", StandardCharsets.UTF_8.toString());
                String url = "https://api.binance.com/api/v3/ticker?symbols=" + encodedSymbols;

                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                String responseData = response.body().string();
                JsonArray jsonArray = gson.fromJson(responseData, JsonArray.class);
                for (JsonElement element : jsonArray) {
                    MarketDataModel marketData = gson.fromJson(element, MarketDataModel.class);
                    marketData.setTimestamp(new Date());
                    marketDataRepository.save(marketData);
                    marketDataList.add(marketData);
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception as appropriate for your application
            }
        }
        return marketDataList;
    }
}
