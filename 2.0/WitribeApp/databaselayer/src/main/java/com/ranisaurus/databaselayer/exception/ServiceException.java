package com.ranisaurus.databaselayer.exception;

/**
 * Created by muzammilpeer on 9/6/15.
 */

public class ServiceException extends Exception {
    @SuppressWarnings("compatibility:-415314040946411841")
    private static final long serialVersionUID = 1L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable tx) {
        super(tx.getMessage(), tx);
    }

    public ServiceException(String message, Throwable tx) {
        super(message, tx);
    }
}