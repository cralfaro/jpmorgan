package org.jpmorgan.service;


import org.jpmorgan.Stock;
import org.jpmorgan.exception.InvalidStockException;

/**
 * Service with all defined operations for the market with stocks
 * @author Ruben Alfaro
 *
 */
public interface IStockMarketService {
    double calculateDividendYield(Stock stock);
    double calculatePriceEarningRatio(Stock stock);
    boolean recordTrade(Stock stock) throws InvalidStockException;
    double calculateVolumeWeightedStockPriceInLastPeriod(int slotOfTimeInMinutes);
    double calculateGeometricMeanVolumeWeightedStockPrice();
    Stock getTrade(Stock stock);
    long getTotalNumberTrades();
}

