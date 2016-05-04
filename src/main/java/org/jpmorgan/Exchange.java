package org.jpmorgan;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity to handle all possible products of the market
 *
 * @author Ruben Alfaro
 *
 */
public class Exchange {

    @Getter @Setter
    private StockType type;

    @Getter @Setter
    private StockSymbol symbol;

    @Getter @Setter
    private int lastDividend;

    @Getter @Setter
    private Integer fixDividend;

    @Getter @Setter
    private int parValue;

    public Exchange(StockType type, StockSymbol symbol, int lastDividend, Integer fixDividend, int parValue) {
        this.type = type;
        this.symbol = symbol;
        this.lastDividend = lastDividend;
        this.fixDividend = fixDividend;
        this.parValue = parValue;
    }

}
