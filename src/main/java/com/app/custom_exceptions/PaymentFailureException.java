package com.app.custom_exceptions;

public class PaymentFailureException extends RuntimeException {
    public PaymentFailureException(String message) {
        super(message);
    }
}
