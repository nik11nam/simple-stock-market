package com.stock.market.service;

import com.stock.market.entity.StockSymbolType;
import com.stock.market.entity.Trade;

/**
 * Created by NikhilKoshi on 10/6/16.
 */
public interface StockMarketService {

    /**
     * Calculate the dividend yield
     *
     * @param stockSymbol
     * @return
     */
    Double calculateDividendYield(StockSymbolType stockSymbol);

    /**
     * Calculate the P/E Ratio
     *
     * @param stockSymbol
     * @return
     */
    Double calculatePERatio(StockSymbolType stockSymbol);

    /**
     * Record a trade
     *
     * @param trade
     * @return
     */
    boolean recordTrade(Trade trade);

    /**
     * Calculate Volume Weighted Stock Price based on trades in past 15 minutes
     *
     * @param stockSymbol
     * @return
     */
    Double calculateWeightedStockPrice(StockSymbolType stockSymbol);

    /**
     * Calculate the GBCE All Share Index
     *
     * @return
     */
    Double calculateGBCEShareIndex();

}
