package org.jpmorgan;

import org.joda.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.jpmorgan.exception.InvalidStockException;

/**
 * Representation of a single Stock with all minimum attributes to operate with it
 *
 * @author Ruben Alfaro
 *
 */
public class Stock {

    @Getter
    private Long id;

    @Getter @Setter
    private Operation operation;

    @Getter @Setter
    private StockType type;

    @Getter @Setter
    private StockSymbol symbol;

    @Getter @Setter
    private double price;

    @Getter @Setter
    private double quantity;

    @Getter
    private LocalDateTime timestamp;


    public Stock(double price, long quantity, StockType type, StockSymbol symbol, LocalDateTime localDateTime){
        this.timestamp = localDateTime;
        this.id = (localDateTime != null) ? new Long(localDateTime.toDate().getTime()) : null;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.symbol = symbol;
    }

    public double getDividendYield(Exchange exchange) {
        return type.calculateYield(exchange, price);
    }

    public double getPriceEarningRatio(Exchange exchange) {
        return price / exchange.getLastDividend();
    }

    public double getPriceMultiplyByQuantity() {
        return getPrice() * getQuantity();
    }

    public boolean isValid() throws InvalidStockException {
        if(getPrice() < 0){
            throw new InvalidStockException(InvalidStockException.INVALID_PRICE);
        }
        if(getQuantity() < 0){
            throw new InvalidStockException(InvalidStockException.INVALID_QUANTITY);
        }
        if(getTimestamp() == null){
            throw new InvalidStockException(InvalidStockException.INVALID_TIMESTAMP);
        }
        return true;
    }

}
