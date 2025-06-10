package com.fiospace.crypto_price_fetcher.datasources;

import com.fiospace.crypto_price_fetcher.DataSource;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

public class Bitstamp implements DataSource {
    private static final String API_URL = "https://www.bitstamp.net/api/v2/ticker/btcusd/";

    @Override
    public String getPrice() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonObject = new JSONObject(response.toString());
            double btcPrice = jsonObject.getDouble("last");
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
