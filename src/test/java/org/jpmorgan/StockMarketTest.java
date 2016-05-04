package org.jpmorgan;


import org.joda.time.LocalDateTime;
import org.jpmorgan.exception.InvalidStockException;
import org.jpmorgan.service.IStockMarketService;
import org.jpmorgan.service.StockMarketService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;



/**
 * Class to test <i>StockMarketService</i> service
 * @author Ruben Alfaro
 */
@RunWith(JUnit4.class)
public class StockMarketTest {

    private IStockMarketService stockMarketService;

    @Before
    public void setUp(){
        stockMarketService = new StockMarketService();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCalculateDividendYieldForStockTypeCommon(){
        Assert.assertEquals(0.23, stockMarketService.calculateDividendYield(new Stock(100, 10, StockType.COMMON, StockSymbol.ALE, LocalDateTime.now())), 0.01);
    }

    @Test
    public void testCalculateDividendYieldForStockTypePreferred(){
        Assert.assertEquals(0.02, stockMarketService.calculateDividendYield(new Stock(100, 10, StockType.PREFERRED, StockSymbol.GIN, LocalDateTime.now())), 0.001);
    }

    @Test
    public void testCalculatePriceEarningRatio(){
        Assert.assertEquals(4.3478, stockMarketService.calculatePriceEarningRatio(new Stock(100, 10, StockType.COMMON, StockSymbol.ALE, LocalDateTime.now())), 0.1);
    }


    @Test
    public void testRecordTrade() throws Exception {
        Stock stock = new Stock(100, 10, StockType.COMMON, StockSymbol.ALE, LocalDateTime.now());
        Assert.assertEquals(stockMarketService.getTotalNumberTrades(), 0);

        stockMarketService.recordTrade(stock);
        Stock recordedStock = stockMarketService.getTrade(stock);
        Assert.assertEquals(stock.getSymbol(), recordedStock.getSymbol());
        Assert.assertEquals(new Double(stock.getQuantity()), new Double(recordedStock.getQuantity()));
        Assert.assertEquals(stock.getTimestamp(), recordedStock.getTimestamp());
        Assert.assertEquals(stock.getOperation(), recordedStock.getOperation());
        Assert.assertEquals(stock.getId(), recordedStock.getId());

        Assert.assertEquals(stockMarketService.getTotalNumberTrades(), 1);
    }



    @Test
    public void testCalculateVolumeWeightByPeriodWithNoTrades() throws Exception {
        //BEGIN -- Insert trades with a timestamp higher than 5 minutes
        Stock stock = new Stock(100, 10, StockType.COMMON, StockSymbol.ALE, LocalDateTime.now().minusMinutes(20));
        stockMarketService.recordTrade(stock);

        Assert.assertEquals(0, stockMarketService.calculateVolumeWeightedStockPriceInLastPeriod(5), 0.0);
    }



    @Test
    public void testCalculateVolumeWeightByPeriodWithTrades() throws Exception {

        //BEGIN -- Insert trades with a timestamp lower than 5 minutes
        Stock stock = new Stock(100, 10, StockType.COMMON, StockSymbol.ALE, LocalDateTime.now().minusMinutes(1));
        stockMarketService.recordTrade(stock);

        stock = new Stock(20, 5, StockType.COMMON, StockSymbol.JOE, LocalDateTime.now().minusMinutes(4));
        stockMarketService.recordTrade(stock);

        //The amount of all trades is (100*10 + 20*5) / 5 + 10 = 1100/15 = 73.3333
        Assert.assertEquals(73.333, stockMarketService.calculateVolumeWeightedStockPriceInLastPeriod(5), 0.001);
    }



    @Test
    public void testCalculateAllSharesVolumeWeightByPeriodGeometricMean() throws Exception {
        //BEGIN -- Insert trades
        Stock stock = new Stock(100, 10, StockType.COMMON, StockSymbol.ALE, LocalDateTime.now());
        stockMarketService.recordTrade(stock);

        stock = new Stock(20, 5, StockType.COMMON, StockSymbol.JOE, LocalDateTime.now());
        stockMarketService.recordTrade(stock);

        //The geometric mean for 2 trades would be 100*20 pow 1/2 = 44.7213
        Assert.assertEquals(44.7213, stockMarketService.calculateGeometricMeanVolumeWeightedStockPrice(), 0.0001);
    }



    @Test
    public void throwsExceptionWhenNegativePriceIsGivenOnRecordStock() throws InvalidStockException {
        thrown.expect(InvalidStockException.class);
        thrown.expectMessage(InvalidStockException.INVALID_PRICE);

        Stock stock = new Stock(-100, 10, StockType.COMMON, StockSymbol.ALE, LocalDateTime.now());
        stockMarketService.recordTrade(stock);
    }



    @Test
    public void throwsExceptionWhenNegativeQuantityIsGivenOnRecordStock() throws InvalidStockException {
        thrown.expect(InvalidStockException.class);
        thrown.expectMessage(InvalidStockException.INVALID_QUANTITY);

        Stock stock = new Stock(100, -10, StockType.COMMON, StockSymbol.ALE, LocalDateTime.now());
        stockMarketService.recordTrade(stock);
    }



    @Test
    public void throwsExceptionWhenNullTimeStampIsGivenOnRecordStock() throws InvalidStockException {
        thrown.expect(InvalidStockException.class);
        thrown.expectMessage(InvalidStockException.INVALID_TIMESTAMP);

        Stock stock = new Stock(100, 10, StockType.COMMON, StockSymbol.ALE, null);
        stockMarketService.recordTrade(stock);
    }

}

