package org.jpmorgan;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;



/**
 * Initial Factory with all possible exchange values for the example market
 *
 * @author Ruben Alfaro
 */

public class StockMarketExchange {



    @Getter
    private Map<StockSymbol, Exchange> marketExchange = new HashMap<StockSymbol, Exchange>();



    public StockMarketExchange() {
        loadMarketExchange();
    }



    private void loadMarketExchange() {
        getMarketExchange().put(StockSymbol.TEA, new Exchange(StockType.COMMON, StockSymbol.TEA, 0, null, 100));
        getMarketExchange().put(StockSymbol.POP, new Exchange(StockType.COMMON, StockSymbol.POP, 8, null, 100));
        getMarketExchange().put(StockSymbol.ALE, new Exchange(StockType.COMMON, StockSymbol.ALE, 23, null, 60));
        getMarketExchange().put(StockSymbol.GIN, new Exchange(StockType.PREFERRED, StockSymbol.GIN, 8, new Integer(2), 100));
        getMarketExchange().put(StockSymbol.JOE, new Exchange(StockType.COMMON, StockSymbol.JOE, 13, null, 250));
    }



    public Exchange getExchangeBySymbol(StockSymbol symbol) {
        return getMarketExchange().get(symbol);
    }

}

