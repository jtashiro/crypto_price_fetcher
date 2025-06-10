// CryptoPriceFetcher.java
package com.fiospace.crypto_price_fetcher;

// ... your imports ...
import com.fiospace.crypto_price_fetcher.datasources.Binance;
import com.fiospace.crypto_price_fetcher.datasources.Bitfinex;
import com.fiospace.crypto_price_fetcher.datasources.Bitstamp;
import com.fiospace.crypto_price_fetcher.datasources.CoinGecko;
import com.fiospace.crypto_price_fetcher.datasources.CoinMarketCap;
import com.fiospace.crypto_price_fetcher.datasources.Coinbase;
import com.fiospace.crypto_price_fetcher.datasources.CryptoCompare;
import com.fiospace.crypto_price_fetcher.datasources.Gemini;
import com.fiospace.crypto_price_fetcher.datasources.Kraken;
import com.fiospace.crypto_price_fetcher.datasources.NonKYC;
import com.fiospace.crypto_price_fetcher.datasources.Phemex;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner; // For user input
import java.util.TreeSet;


public class CryptoPriceFetcher {
    private static final Map<String, DataSource> DATA_SOURCES = new HashMap<>();

    static {
        //DATA_SOURCES.put("Coinbase", new Coinbase());
        //DATA_SOURCES.put("CoinGecko", new CoinGecko());
        //DATA_SOURCES.put("Binance", new Binance());
        //DATA_SOURCES.put("Kraken", new Kraken());
        //DATA_SOURCES.put("Bitfinex", new Bitfinex());
        DATA_SOURCES.put("Bitstamp", new Bitstamp());
        //DATA_SOURCES.put("CoinMarketCap", new CoinMarketCap());
        //DATA_SOURCES.put("CryptoCompare", new CryptoCompare());
        //DATA_SOURCES.put("Phemex", new Phemex());
        DATA_SOURCES.put("Gemini", new Gemini());
        DATA_SOURCES.put("NonKYC", new NonKYC());

    }

    /**
     * Fetches the price for the given crypto asset from the specified data source.
     *
     * @param dataSourceName The name of the data source (e.g., "Coinbase").
     * @param cryptoSymbol The symbol of the crypto asset (e.g., "BTC", "ETH").
     * @return The price as a String.
     * @throws Exception If there's an error fetching the price or an invalid data source.
     */
    public static String getPrice(String dataSourceName, String cryptoSymbol) throws Exception {
        if (!DATA_SOURCES.containsKey(dataSourceName)) {
            throw new IllegalArgumentException("Invalid data source: " + dataSourceName);
        }
        if (cryptoSymbol == null || cryptoSymbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Crypto symbol cannot be null or empty.");
        }
        // Pass the cryptoSymbol to the specific DataSource's getPrice method
        return DATA_SOURCES.get(dataSourceName).getPrice(cryptoSymbol.trim().toUpperCase());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cryptoSymbolInput;

            cryptoSymbolInput = "XMR/USDT";

            System.out.println("\nFetching prices for " + cryptoSymbolInput.toUpperCase() + " from available sources:");
            TreeSet<String> sortedSources = new TreeSet<>(DATA_SOURCES.keySet());
            boolean foundAnyPrice = false;

            for (String sourceName : sortedSources) {
                try {
                    // Call the updated getPrice method
                    String price = getPrice(sourceName, cryptoSymbolInput);
                    System.out.println(sourceName + " " + cryptoSymbolInput.toUpperCase() + " Price: " + price);
                    foundAnyPrice = true;
                } catch (IllegalArgumentException e) {
                    // This specific catch block might not be strictly necessary here
                    // if getPrice handles symbol validation, but good for clarity.
                    System.err.println("Error for " + sourceName + ": " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Error fetching price from " + sourceName + " for " + cryptoSymbolInput.toUpperCase() + ": " + e.getMessage());
                    // You might want to print more details for debugging e.g. e.printStackTrace();
                }
            }
            if (!foundAnyPrice && DATA_SOURCES.size() > 0) {
                System.out.println("Could not fetch the price for " + cryptoSymbolInput.toUpperCase() + " from any active source. The symbol might be unsupported by the configured data sources or there might have been network issues.");
            }
            System.out.println("--------------------------------------------------");

        System.out.println("Exiting application.");
        scanner.close();
    }
}