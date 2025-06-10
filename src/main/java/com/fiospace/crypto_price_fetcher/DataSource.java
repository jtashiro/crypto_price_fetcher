package com.fiospace.crypto_price_fetcher;

public interface DataSource {
    String getPrice() throws Exception;
}
