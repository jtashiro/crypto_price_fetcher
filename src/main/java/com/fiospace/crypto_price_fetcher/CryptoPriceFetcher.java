package com.fiospace.crypto_price_fetcher;

import com.fiospace.crypto_price_fetcher.datasources.Binance;
import com.fiospace.crypto_price_fetcher.datasources.Bitfinex;
import com.fiospace.crypto_price_fetcher.datasources.Bitstamp;
import com.fiospace.crypto_price_fetcher.datasources.CoinGecko;
import com.fiospace.crypto_price_fetcher.datasources.CoinMarketCap;
import com.fiospace.crypto_price_fetcher.datasources.Coinbase;
import com.fiospace.crypto_price_fetcher.datasources.CryptoCompare;
import com.fiospace.crypto_price_fetcher.datasources.Kraken;
import com.fiospace.crypto_price_fetcher.datasources.Phemex;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class CryptoPriceFetcher {
    private static final Map<String, DataSource> DATA_SOURCES = new HashMap<>();

    static {
        DATA_SOURCES.put("Coinbase", new Coinbase());
        DATA_SOURCES.put("CoinGecko", new CoinGecko());
        //DATA_SOURCES.put("Binance", new Binance());
        DATA_SOURCES.put("Kraken", new Kraken());
        DATA_SOURCES.put("Bitfinex", new Bitfinex());
        DATA_SOURCES.put("Bitstamp", new Bitstamp());
        //DATA_SOURCES.put("CoinMarketCap", new CoinMarketCap());
        DATA_SOURCES.put("CryptoCompare", new CryptoCompare());
        DATA_SOURCES.put("Phemex", new Phemex());
    }

    public static String getPrice(String dataSource) throws Exception {
        if (!DATA_SOURCES.containsKey(dataSource)) {
            throw new IllegalArgumentException("Invalid data source: " + dataSource);
        }
        return DATA_SOURCES.get(dataSource).getPrice();
    }

    public static void main(String[] args) {
        TreeSet<String> sortedSources = new TreeSet<>(DATA_SOURCES.keySet());
        for (String source : sortedSources) {

        //for (String source : DATA_SOURCES.keySet()) {
            try {
                String price = getPrice(source);
                System.out.println(source + " BTC Price: " + price);
            } catch (Exception e) {
                System.err.println("Error fetching price from " + source + ": " + e.getMessage());
            }
        }
    }
}
