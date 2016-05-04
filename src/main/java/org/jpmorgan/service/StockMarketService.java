package org.jpmorgan.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.joda.time.LocalDateTime;
import lombok.Getter;
import org.jpmorgan.Exchange;
import org.jpmorgan.Stock;
import org.jpmorgan.StockMarketExchange;
import org.jpmorgan.exception.InvalidStockException;


/**
 * Class to handle stocks.
 *
 * @author Ruben Alfaro
 */

public class StockMarketService implements IStockMarketService {


    @Getter
    private StockMarketExchange stockMarketExchange = new StockMarketExchange();


    @Getter
    private List<Stock> stocksList = Collections.synchronizedList(new ArrayList<>());


    @Override
    public double calculateDividendYield(Stock stock) {
        Exchange exchange = getStockMarketExchange().getExchangeBySymbol(stock.getSymbol());
        return stock.getDividendYield(exchange);
    }


    @Override
    public double calculatePriceEarningRatio(Stock stock) {
        Exchange exchange = getStockMarketExchange().getExchangeBySymbol(stock.getSymbol());
        return stock.getPriceEarningRatio(exchange);
    }


    @Override
    public boolean recordTrade(Stock stock) throws InvalidStockException {
        if (stock.isValid()) {
            stocksList.add(stock);
            return true;
        }
        return false;
    }


    @Override
    public double calculateVolumeWeightedStockPriceInLastPeriod(int slotOfTimeInMinutes) {
        LocalDateTime initPeriod = LocalDateTime.now().minusMinutes(slotOfTimeInMinutes);
        double tradeQuantityPriceSum = getStocksList().stream().
                filter(s -> s.getTimestamp().isAfter(initPeriod)).
                mapToDouble(Stock::getPriceMultiplyByQuantity).sum();
        double tradeQuantitySum = getStocksList().stream().
                filter(s -> s.getTimestamp().isAfter(initPeriod)).
                mapToDouble(Stock::getQuantity).sum();

        return tradeQuantitySum != 0 ? (tradeQuantityPriceSum / tradeQuantitySum) : 0;
    }


    @Override
    public double calculateGeometricMeanVolumeWeightedStockPrice() {
        List<Double> stocksPrice = getStocksList().stream().map(Stock::getPrice).collect(Collectors.toList());
        Optional<Double> geometricMean = stocksPrice.stream().reduce((i, j) -> i * j);
        return geometricMean.isPresent() ? calculateGeometricMean(geometricMean.get(), getTotalNumberTrades()) : 0;

    }


    private double calculateGeometricMean(double totalMultiply, long totalNumberTrades) {
        return Math.pow(totalMultiply, ((double) 1 / totalNumberTrades));
    }


    @Override
    public Stock getTrade(Stock stock) {
        Optional<Stock> searchedStock = getStocksList().stream().filter(s -> s.getId().equals(stock.getId())).findFirst();
        return searchedStock.get();
    }


    @Override
    public long getTotalNumberTrades() {
        return getStocksList().size();
    }
}


