package com.stock.market.utils;

import com.stock.market.entity.Trade;

import java.util.Arrays;
import java.util.List;

import static com.stock.market.entity.TradeIndicator.*;
import static com.stock.market.utils.StockUtils.*;
import static com.stock.market.utils.DateUtils.nowOffsetMinutes;

/**
 * Created by NikhilKoshi on 10/6/16.
 */
public class TradeUtils {

    public static List<Trade> generateRandomTrades() {
        return Arrays.asList(
                new Trade.TradeBuilder(buildStockPOP(), BUY, 20, 8.25, nowOffsetMinutes(-26)).build(),
                new Trade.TradeBuilder(buildStockPOP(), SELL, 25, 8.10, nowOffsetMinutes(-13)).build(),
                new Trade.TradeBuilder(buildStockALE(), SELL, 30, 16.40, nowOffsetMinutes(-9)).build(),
                new Trade.TradeBuilder(buildStockALE(), SELL, 10, 16.15, nowOffsetMinutes(-17)).build(),
                new Trade.TradeBuilder(buildStockALE(), BUY, 40, 16.30, nowOffsetMinutes(-6)).build(),
                new Trade.TradeBuilder(buildStockTEA(), SELL, 15, 12.80, nowOffsetMinutes(-12)).build(),
                new Trade.TradeBuilder(buildStockTEA(), BUY, 10, 12.90, nowOffsetMinutes(-11)).build(),
                new Trade.TradeBuilder(buildStockTEA(), BUY, 5, 12.85, nowOffsetMinutes(-10)).build(),
                new Trade.TradeBuilder(buildStockGIN(), SELL, 40, 5.50, nowOffsetMinutes(-30)).build(),
                new Trade.TradeBuilder(buildStockGIN(), BUY, 25, 5.60, nowOffsetMinutes(-27)).build(),
                new Trade.TradeBuilder(buildStockGIN(), BUY, 15, 5.55, nowOffsetMinutes(-12)).build(),
                new Trade.TradeBuilder(buildStockJOE(), SELL, 55, 4.40, nowOffsetMinutes(-28)).build(),
                new Trade.TradeBuilder(buildStockJOE(), BUY, 25, 4.45, nowOffsetMinutes(-26)).build(),
                new Trade.TradeBuilder(buildStockJOE(), BUY, 20, 4.42, nowOffsetMinutes(-17)).build(),
                new Trade.TradeBuilder(buildStockJOE(), BUY, 10, 4.50, nowOffsetMinutes(-6)).build()
                );

    }

}
