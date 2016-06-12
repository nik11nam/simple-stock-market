package com.stock.market;

import com.stock.market.entity.Trade;
import com.stock.market.repository.StocksRepository;
import com.stock.market.repository.TradesRepository;
import com.stock.market.repository.impl.StocksRepositoryImpl;
import com.stock.market.repository.impl.TradesRepositoryImpl;
import com.stock.market.service.StockMarketService;
import com.stock.market.service.impl.StockMarketServiceImpl;
import com.stock.market.exception.StockMarketException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static com.stock.market.entity.StockSymbolType.*;
import static com.stock.market.entity.TradeIndicator.*;
import static com.stock.market.utils.DateUtils.nowOffsetMinutes;
import static com.stock.market.utils.StockUtils.buildStockPOP;
import static com.stock.market.utils.StockUtils.initializeStocks;
import static com.stock.market.utils.TradeUtils.generateRandomTrades;
import static org.junit.Assert.*;

/**
 * Created by NikhilKoshi on 11/6/16.
 */
public class StockMarketServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private StockMarketService service;

    private StocksRepository stocksRepository;
    private TradesRepository tradesRepository;

    @Before
    public void setUp() throws Exception {
        // Set up the stocks in stock market
        stocksRepository = new StocksRepositoryImpl(initializeStocks());
        // Set up random trades in the system
        tradesRepository = new TradesRepositoryImpl(new ArrayList<>(generateRandomTrades()));
        // Inject the dependencies to the service
        this.service = new StockMarketServiceImpl(stocksRepository, tradesRepository);
    }

    @Test
    public void testCalculateDividendYieldMissingStock() {
        thrown.expect(StockMarketException.class);
        thrown.expectMessage("Stock symbol: null is not available in the system");

        service.calculateDividendYield(null);
    }

    @Test
    public void testCalculateDividendYieldZeroStockPrice() {
        thrown.expect(StockMarketException.class);
        thrown.expectMessage("The price for stock symbol: TEA should be greater than 0.0");

        service.calculateDividendYield(TEA);
    }

    @Test
    public void testCalculateDividendYieldForCommonStock() {
        Double dividendYield = service.calculateDividendYield(ALE);
        assertNotNull(dividendYield);
    }

    @Test
    public void testCalculateDividendYieldForPreferredStock() {
        Double dividendYield = service.calculateDividendYield(GIN);
        assertNotNull(dividendYield);
    }

    @Test
    public void testCalculatePERatioMissingStock() {
        thrown.expect(StockMarketException.class);
        thrown.expectMessage("Stock symbol: null is not available in the system");

        service.calculatePERatio(null);
    }

    @Test
    public void testCalculatePERatioZeroStockPrice() {
        Double peRatio = service.calculatePERatio(TEA);
        assertNotNull(peRatio);
        assertEquals(0.00, 0.00, 0.0001);
    }

    @Test
    public void testCalculatePERatioForCommonStock() {
        Double peRatio = service.calculatePERatio(ALE);
        assertNotNull(peRatio);
    }

    @Test
    public void testCalculatePERatioForPreferredStock() {
        Double peRatio = service.calculatePERatio(GIN);
        assertNotNull(peRatio);
    }

    @Test
    public void testRecordTradeMissingTrade() {
        thrown.expect(StockMarketException.class);
        thrown.expectMessage("Trade object is not available to record");

        service.recordTrade(null);
    }

    @Test
    public void testRecordTradeMissingStock() {
        thrown.expect(StockMarketException.class);
        thrown.expectMessage("Stock symbol not set for the trade");

        Trade trade = new Trade.TradeBuilder(null, BUY, 20, 8.25, nowOffsetMinutes(-26)).build();
        service.recordTrade(trade);
    }

    @Test
    public void testRecordTradeInvalidQuantity() {
        thrown.expect(StockMarketException.class);
        thrown.expectMessage("Trade quantity not set for the trade");

        Trade trade = new Trade.TradeBuilder(buildStockPOP(), BUY, 0, 8.25, nowOffsetMinutes(-26)).build();
        service.recordTrade(trade);
    }

    @Test
    public void testRecordTradeInvalidPrice() {
        thrown.expect(StockMarketException.class);
        thrown.expectMessage("Trade price not set for the trade");

        Trade trade = new Trade.TradeBuilder(buildStockPOP(), BUY, 10, 0.00, nowOffsetMinutes(-26)).build();
        service.recordTrade(trade);
    }

    @Test
    public void testRecordTradeMissingExecutionTime() {
        thrown.expect(StockMarketException.class);
        thrown.expectMessage("Trade execution time not available for the trade");

        Trade trade = new Trade.TradeBuilder(buildStockPOP(), BUY, 10, 1.10, null).build();
        service.recordTrade(trade);
    }

    @Test
    public void testRecordTrade() {
        // Initial no. of trades
        int initialNoOfTrades = tradesRepository.getTrades().size();
        assertEquals(15, initialNoOfTrades);
        Trade trade = new Trade.TradeBuilder(buildStockPOP(), BUY, 10, 1.10, nowOffsetMinutes(-12)).build();
        boolean recordTrade = service.recordTrade(trade);
        assertTrue(recordTrade);
        int noOfTrades = tradesRepository.getTrades().size();
        assertEquals(initialNoOfTrades + 1, noOfTrades);
    }

    @Test
    public void testCalculateWeightedStockPriceMissingStock() {
        thrown.expect(StockMarketException.class);
        thrown.expectMessage("Stock symbol: null is not available in the system");

        service.calculateWeightedStockPrice(null);
    }

    @Test
    public void testCalculateWeightedStockPrice() {
        Double avgStockPrice = service.calculateWeightedStockPrice(ALE);
        assertNotNull(avgStockPrice);
    }

    @Test
    public void testCalculateGBCEShareIndex() {
        Double shareIndex = service.calculateGBCEShareIndex();
        assertNotNull(shareIndex);
    }

}
