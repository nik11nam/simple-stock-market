package com.stock.market.service.impl;

import com.stock.market.entity.Stock;
import com.stock.market.entity.StockSymbolType;
import com.stock.market.entity.Trade;
import com.stock.market.repository.StocksRepository;
import com.stock.market.repository.TradesRepository;
import com.stock.market.service.StockMarketService;

import com.stock.market.exception.StockMarketException;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.stock.market.utils.MathUtils.geometricMean;
import static com.stock.market.utils.MathUtils.roundTo4Decimal;

/**
 * Created by NikhilKoshi on 10/6/16.
 */
public class StockMarketServiceImpl implements StockMarketService {

    private static final Logger LOG = Logger.getLogger(StockMarketServiceImpl.class);

    private StocksRepository stocksRepository;
    private TradesRepository tradesRepository;

    public StockMarketServiceImpl(StocksRepository stocksRepository, TradesRepository tradesRepository) {
        this.stocksRepository = stocksRepository;
        this.tradesRepository = tradesRepository;
    }

    @Override
    public Double calculateDividendYield(StockSymbolType stockSymbol) {
        Double dividendYield = null;
        LOG.debug("Calculating Dividend Yield for the stock symbol: " + stockSymbol);

        Stock stock = stocksRepository.getStockBySymbol(stockSymbol);

        if (null == stock) {
            throw new StockMarketException("Stock symbol: " + stockSymbol + " is not available in the system");
        }

        if (stock.getPrice() <= 0.0) {
            throw new StockMarketException("The price for stock symbol: " + stockSymbol + " should be greater than 0.0");
        }

        try {
            dividendYield = stock.getDividendYield();

        } catch (Exception ex) {
            LOG.error("Error calculating Dividend Yield for the stock symbol: " + stockSymbol);
            throw new StockMarketException("Error calculating Dividend Yield for the stock symbol: " + stockSymbol, ex);
        }

        LOG.info("Dividend Yield for stock symbol: " + stockSymbol + " is " + dividendYield);
        return dividendYield;
    }

    @Override
    public Double calculatePERatio(StockSymbolType stockSymbol) {
        Double peRatio = null;
        LOG.debug("Calculating P/E Ratio for the stock symbol: " + stockSymbol);

        Stock stock = stocksRepository.getStockBySymbol(stockSymbol);

        if (null == stock) {
            throw new StockMarketException("Stock symbol: " + stockSymbol + " is not available in the system");
        }

        try {
            peRatio = stock.getPERatio();

        } catch (Exception ex) {
            LOG.error("Error calculating P/E Ratio for the stock symbol: " + stockSymbol);
            throw new StockMarketException("Error calculating P/E Ratio for the stock symbol: " + stockSymbol, ex);
        }

        LOG.info("P/E Ratio for stock symbol: " + stockSymbol + " is " + peRatio);
        return peRatio;
    }

    @Override
    public boolean recordTrade(Trade trade) {
        boolean recordTrade = false;
        LOG.debug("Start record trade for " + recordTrade);

        if (null == trade) {
            String message = "Trade object is not available to record";
            LOG.error(message);
            throw new StockMarketException(message);
        }

        if (null == trade.getStock()) {
            String message = "Stock symbol not set for the trade: " + trade;
            LOG.error(message);
            throw new StockMarketException(message);
        }

        if ((null == trade.getQuantity()) || (trade.getQuantity() <= 0)) {
            String message = "Trade quantity not set for the trade: " + trade;
            LOG.error(message);
            throw new StockMarketException(message);
        }

        if ((null == trade.getPrice()) || (trade.getPrice() <= 0.0)) {
            String message = "Trade price not set for the trade: " + trade;
            LOG.error(message);
            throw new StockMarketException(message);
        }

        if (null == trade.getTimestamp()) {
            String message = "Trade execution time not available for the trade: " + trade;
            LOG.error(message);
            throw new StockMarketException(message);
        }

        try {
            recordTrade = tradesRepository.recordTrade(trade);
        } catch (Exception ex) {
            LOG.error("Error encountered while recording trade : " + recordTrade);
            throw new StockMarketException("Error encountered while recording trade : " + recordTrade, ex);
        }

        return recordTrade;
    }

    @Override
    public Double calculateWeightedStockPrice(StockSymbolType stockSymbol) {
        Double avgStockPrice = 0.0;
        LOG.debug("Calculating Stock Price for the stock symbol: "+stockSymbol);

        Stock stock = stocksRepository.getStockBySymbol(stockSymbol);

        if (null == stock) {
            throw new StockMarketException("Stock symbol: " + stockSymbol + " is not available in the system");
        }

        try {
            List<Trade> trades = tradesRepository.getTrades();

            List<Trade> filteredTrades = filterByStockSymbolTimeRange(trades, stockSymbol, -15);
            LOG.debug("No. of trades in filtered list for stock symbol: " + stockSymbol + " in last 15 mins is "
                    + filteredTrades.size());

            avgStockPrice = averageWeightedStockPrice(filteredTrades);

        } catch (Exception ex) {
            LOG.error("Error calculating Average Weighted Stock Price for the stock symbol: " + stockSymbol);
            throw new StockMarketException("Error calculating Average Weighted Stock Price for the stock symbol: " + stockSymbol, ex);
        }

        LOG.info("Average Weighted Stock Price for " + stockSymbol + " is " + avgStockPrice);

        return avgStockPrice;
    }


    @Override
    public Double calculateGBCEShareIndex() {
        Double shareIndex = 0.0;
        LOG.debug("Calculating GBCE All Share Index");

        try {
            Map<StockSymbolType, Stock> stocks = stocksRepository.getStocks();
            List<Trade> trades = tradesRepository.getTrades();

            List<Double> avgWeightedStockPrices = new ArrayList<>();

            for (final StockSymbolType stockSymbol : stocks.keySet()) {
                Double avgStockPrice = 0.0;

                List<Trade> filteredTrades = filterByStockSymbol(trades, stockSymbol);
                LOG.debug("No. of trades in filtered list for stock symbol: " + stockSymbol + " is "
                        + filteredTrades.size());

                avgStockPrice = averageWeightedStockPrice(filteredTrades);
                LOG.debug("Avg. weighted price for stock: " + stockSymbol + " is " + avgStockPrice);
                if (avgStockPrice > 0.0) {
                    avgWeightedStockPrices.add(avgStockPrice);
                }
            }

            if (avgWeightedStockPrices.size() > 0) {
                shareIndex = geometricMean(avgWeightedStockPrices);
            }
        } catch (Exception ex) {
            LOG.error("Error calculating GBCE All Share Index");
            throw new StockMarketException("GBCE All Share Index", ex);
        }

        shareIndex = roundTo4Decimal(shareIndex);

        LOG.info("Calculated GBCE All Share Index: " + shareIndex);
        return shareIndex;
    }

    /**
     * Calculate average weighted price for list of trades
     *
     * @param trades
     * @return
     */
    private Double averageWeightedStockPrice(List<Trade> trades) {
        Double avgStockPrice = 0.0;
        Double totalQuantity = 0.0;
        Double totalPrice = 0.0;

        for(Trade trade : trades) {
            totalPrice += trade.getQuantity() * trade.getPrice();
            totalQuantity += trade.getQuantity();
        }

        if(totalQuantity > 0.0) {
            avgStockPrice = totalPrice / totalQuantity;
        }

        return roundTo4Decimal(avgStockPrice);
    }

    /**
     * Filter trades by stock symbol and time range
     *
     * @param trades
     * @param stockSymbol
     * @param range
     * @return
     */
    private List<Trade> filterByStockSymbolTimeRange(final List<Trade> trades, final StockSymbolType stockSymbol, final int range) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, range);

        Date timeRange = instance.getTime();
        LOG.debug("Trades within time range: " + timeRange.toString());

        List<Trade> filteredTradesBySymbol = filterByStockSymbol(trades, stockSymbol);
        return filteredTradesBySymbol
                .stream()
                .filter(new Predicate<Trade>() {
                    @Override
                    public boolean test(Trade trade) {
                        if (trade.getTimestamp().compareTo(timeRange) >= 0) {
                            return true;
                        }
                        return false;
                    }
                }).collect(Collectors.toList());
    }

    /**
     * Filter trades by stock symbol
     *
     * @param trades
     * @param stockSymbol
     * @return
     */
    private List<Trade> filterByStockSymbol(final List<Trade> trades, final StockSymbolType stockSymbol) {
        LOG.debug("Trades within time range: " + stockSymbol.name());
        return trades
                .stream()
                .filter(new Predicate<Trade>() {
                    @Override
                    public boolean test(Trade trade) {
                        if (stockSymbol == trade.getStock().getSymbol()) {
                            return true;
                        }
                        return false;
                    }
                }).collect(Collectors.toList());
    }

}
