package com.stock.market.repository;

import com.stock.market.entity.Trade;

import java.util.List;

/**
 * Created by NikhilKoshi on 10/6/16.
 */
public interface TradesRepository {

    /**
     * Record a trade
     *
     * @param trade
     * @return
     */
    boolean recordTrade(Trade trade);

    /**
     * Get all trades
     *
     * @return
     */
    List<Trade> getTrades();

}
