package com.stock.market.entity;

import com.stock.market.utils.MathUtils;

import static com.stock.market.utils.MathUtils.roundTo4Decimal;

/**
 * Created by NikhilKoshi on 10/6/16.
 */
public class Stock {

    private final StockSymbolType symbol;
    private final StockType type;
    private final Double lastDividend; // optional
    private final Double fixedDividend;  // optionals
    private final Double parValue;
    private final Double price;

    private Stock(StockBuilder builder) {
        this.symbol = builder.symbol;
        this.type = builder.type;
        this.lastDividend = builder.lastDividend;
        this.fixedDividend = builder.fixedDividend;
        this.parValue = builder.parValue;
        this.price = builder.price;
    }

    public StockSymbolType getSymbol() {
        return symbol;
    }

    public StockType getType() {
        return type;
    }

    public Double getLastDividend() {
        return lastDividend;
    }

    public Double getFixedDividend() {
        return fixedDividend;
    }

    public Double getParValue() {
        return parValue;
    }

    public Double getPrice() {
        return price;
    }

    /**
     *
     * @return
     */
    public Double getDividendYield() {
        Double dividendYield = -1.0;
        if (StockType.COMMON == type) {
            dividendYield = lastDividend / price;
        } else {
            dividendYield = ((fixedDividend / 100) * parValue) / price;
        }
        return roundTo4Decimal(dividendYield);
    }

    /**
     *
     * @return
     */
    public Double getPERatio() {
        if (price > 0.00) {
            return roundTo4Decimal(price / getDividendYield());
        } else {
            return price;
        }
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol=" + symbol +
                ", type=" + type +
                ", lastDividend=" + lastDividend +
                ", fixedDividend=" + fixedDividend +
                ", parValue=" + parValue +
                ", price=" + price +
                '}';
    }

    public static class StockBuilder {

        private final StockSymbolType symbol;
        private final StockType type;
        private Double lastDividend;
        private Double fixedDividend;
        private final Double parValue;
        private final Double price;

        public StockBuilder(StockSymbolType symbol, StockType type, Double parValue, Double price) {
            this.symbol = symbol;
            this.type = type;
            this.parValue = parValue;
            this.price = price;
        }

        public StockBuilder lastDividend(Double lastDividend) {
            this.lastDividend = lastDividend;
            return this;
        }

        public StockBuilder fixedDividend(Double fixedDividend) {
            this.fixedDividend = fixedDividend;
            return this;
        }

        public Stock build() {
            Stock stock = new Stock(this);
            return stock;
        }
    }
}
