// DataSource.java
package com.fiospace.crypto_price_fetcher;

public interface DataSource {
    /**
     * Fetches the price for the given crypto asset symbol.
     *
     * @param cryptoSymbol The symbol of the crypto asset (e.g., "BTC", "ETH")
     *                     or a trading pair (e.g., "BTCUSDT") depending on the API.
     * @return The price as a String.
     * @throws Exception If there's an error fetching the price.
     */
    String getPrice(String cryptoSymbol) throws Exception;
}