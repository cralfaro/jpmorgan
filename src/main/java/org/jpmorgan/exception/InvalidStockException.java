package org.jpmorgan.exception;


/**
 * Class to handle all possible exceptions at stock creation time
 *
 * @author Ruben Alfaro
 */

public class InvalidStockException extends Exception {


    private static final long serialVersionUID = 1L;


    public static final String INVALID_PRICE = "The value of the stock price is not valid. Only positive values";
    public static final String INVALID_QUANTITY = "The value of the stock quantity is not valid. Only positive values";
    public static final String INVALID_TIMESTAMP = "The value of the stock timestamp is null";


    public InvalidStockException() {
        super();
    }


    public InvalidStockException(String message) {
        super(message);
    }


    public InvalidStockException(String message, Throwable throwable) {
        super(message, throwable);
    }

}

