package com.knoldus.wallet.exception;

public class WalletRequestAlreadyPendingException extends RuntimeException {

    public WalletRequestAlreadyPendingException(String message) {
        super(message);
    }
}
