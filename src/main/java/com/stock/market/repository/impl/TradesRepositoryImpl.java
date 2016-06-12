package com.stock.market.repository.impl;

import com.stock.market.entity.Trade;
import com.stock.market.exception.StockMarketException;
import com.stock.market.repository.TradesRepository;

import java.util.List;

/**
 * Created by NikhilKoshi on 10/6/16.
 */
public class TradesRepositoryImpl implements TradesRepository {

    private List<Trade> trades;

    public TradesRepositoryImpl(List<Trade> trades) {
        this.trades = trades;
    }

    @Override
    public boolean recordTrade(Trade trade) {
        try {
            trades.add(trade);
        } catch (Exception ex) {
            throw new StockMarketException("Error recording the trade: " + trade, ex);
        }
        return true;
    }

    @Override
    public List<Trade> getTrades() {
        return this.trades;
    }

}
