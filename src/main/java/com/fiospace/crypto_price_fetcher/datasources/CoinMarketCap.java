package com.fiospace.crypto_price_fetcher.datasources;

import com.fiospace.crypto_price_fetcher.DataSource;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

public class CoinMarketCap implements DataSource {
    private static final String API_KEY = "YOUR_API_KEY"; // Replace with your actual API key
    private static final String API_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
    private static final String SYMBOL = "BTC";

    @Override
    public String getPrice() throws Exception {
        URL url = new URL(API_URL + "?symbol=" + SYMBOL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("X-CMC_PRO_API_KEY", API_KEY);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONObject btcData = jsonObject.getJSONObject("data").getJSONObject(SYMBOL);
            double btcPrice = btcData.getJSONObject("quote").getJSONObject("USD").getDouble("price");
            return formatPrice(btcPrice);
        } finally {
            urlConnection.disconnect();
        }
    }

    private String formatPrice(double price) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        currencyFormat.setMaximumFractionDigits(0);
        return currencyFormat.format(price);
    }
}
