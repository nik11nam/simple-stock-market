package com.stock.market.utils;

import com.stock.market.entity.Stock;
import com.stock.market.entity.StockSymbolType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.stock.market.entity.StockSymbolType.*;
import static com.stock.market.entity.StockType.*;

/**
 * Created by NikhilKoshi on 10/6/16.
 */
public class StockUtils {

    public static Stock buildStockTEA() {
        return new Stock.StockBuilder(TEA, COMMON, 100.00, 0.00)
                .lastDividend(0.00)
                .build();
    }

    public static Stock buildStockPOP() {
        return new Stock.StockBuilder(POP, COMMON, 100.00, 11.00)
                .lastDividend(8.00)
                .build();
    }

    public static Stock buildStockALE() {
        return new Stock.StockBuilder(ALE, COMMON, 60.00, 21.00)
                .lastDividend(23.00)
                .build();
    }

    public static Stock buildStockGIN() {
        return new Stock.StockBuilder(GIN, PREFERRED, 100.00, 17.00)
                .lastDividend(8.00)
                .fixedDividend(2.00)
                .build();
    }

    public static Stock buildStockJOE() {
        return new Stock.StockBuilder(JOE, COMMON, 250.00, 46.00)
                .lastDividend(13.00)
                .build();
    }

    public static Map<StockSymbolType, Stock> initializeStocks() {
        return Collections.unmodifiableMap(new HashMap<StockSymbolType, Stock>() {
            {
                put(TEA, buildStockTEA());
                put(POP, buildStockPOP());
                put(ALE, buildStockALE());
                put(GIN, buildStockGIN());
                put(JOE, buildStockJOE());
            }
        });
    }
}
