package com.knoldus.wallet.exception;

public class NoPendingRequestException extends RuntimeException {

    public NoPendingRequestException(String message) {
        super(message);
    }
}