package com.stock.market.repository;

import com.stock.market.entity.Stock;
import com.stock.market.entity.StockSymbolType;

import java.util.Map;

/**
 * Created by NikhilKoshi on 10/6/16.
 */
public interface StocksRepository {

    /**
     * Get all stocks
     *
     * @return
     */
    Map<StockSymbolType, Stock> getStocks();

    /**
     * Get Stock by symbol
     *
     * @param symbol
     * @return
     */
    Stock getStockBySymbol(StockSymbolType symbol);

}
