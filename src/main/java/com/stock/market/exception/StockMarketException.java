package com.stock.market.exception;

/**
 * Created by NikhilKoshi on 10/6/16.
 */
public class StockMarketException extends RuntimeException {

    public StockMarketException(String message) {
        super(message);
    }

    public StockMarketException(String message, Throwable cause) {
        super(message, cause);
    }
}
