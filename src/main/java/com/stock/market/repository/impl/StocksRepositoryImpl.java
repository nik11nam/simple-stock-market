package com.stock.market.repository.impl;

import com.stock.market.entity.Stock;
import com.stock.market.entity.StockSymbolType;
import com.stock.market.repository.StocksRepository;

import java.util.Map;

/**
 * Created by NikhilKoshi on 10/6/16.
 */
public class StocksRepositoryImpl implements StocksRepository {

    private Map<StockSymbolType, Stock> stocks = null;

    public StocksRepositoryImpl(Map<StockSymbolType, Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public Map<StockSymbolType, Stock> getStocks() {
        return stocks;
    }

    @Override
    public Stock getStockBySymbol(StockSymbolType symbol) {
        return stocks.get(symbol);
    }

}
