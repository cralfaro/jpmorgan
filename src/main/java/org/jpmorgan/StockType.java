package org.jpmorgan;


/**
 * Enum with all stock types
 *
 * @author Ruben Alfaro
 */

public enum StockType {

    COMMON {
        @Override
        public double calculateYield(Exchange exchange, double price) {
            return exchange.getLastDividend() / price;
        }

    },

    PREFERRED {
        @Override
        public double calculateYield(Exchange exchange, double price) {
            return (((double) exchange.getFixDividend() / 100) * exchange.getParValue()) / price;
        }

    };

    /**
     * According to the type, the implementation of the Yield calculation is different
     *
     * @param exchange
     * @param price
     * @return
     */

    public abstract double calculateYield(Exchange exchange, double price);

}

