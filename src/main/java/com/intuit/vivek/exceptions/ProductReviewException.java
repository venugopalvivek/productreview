package com.intuit.vivek.exceptions;

/**
 * Created by vvenugopal on 10/21/17.
 */
public class ProductReviewException extends Exception {

    private int code;

    public ProductReviewException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ProductReviewException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
