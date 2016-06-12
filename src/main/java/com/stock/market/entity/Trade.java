package com.stock.market.entity;

import java.util.Date;

/**
 * Created by NikhilKoshi on 10/6/16.
 */
public class Trade {

    private final Stock stock;
    private final TradeIndicator indicator;
    private final Integer quantity;
    private final Double price;
    private final Date timestamp;

    private Trade(TradeBuilder builder) {
        this.stock = builder.stock;
        this.indicator = builder.indicator;
        this.quantity = builder.quantity;
        this.price = builder.price;
        this.timestamp = builder.timestamp;
    }

    public Stock getStock() {
        return stock;
    }

    public TradeIndicator getIndicator() {
        return indicator;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "stock=" + stock +
                ", indicator=" + indicator +
                ", quantity=" + quantity +
                ", price=" + price +
                ", timestamp=" + timestamp +
                '}';
    }

    public static class TradeBuilder {
        private final Stock stock;
        private final TradeIndicator indicator;
        private final Integer quantity;
        private final Double price;
        private final Date timestamp;

        public TradeBuilder(Stock stock, TradeIndicator indicator, Integer quantity, Double price, Date timestamp) {
            this.stock = stock;
            this.indicator = indicator;
            this.quantity = quantity;
            this.price = price;
            this.timestamp = timestamp;
        }

        public Trade build() {
            Trade trade = new Trade(this);
            return trade;
        }
    }
}
