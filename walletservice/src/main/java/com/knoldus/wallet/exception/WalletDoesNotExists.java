package com.knoldus.wallet.exception;

public class WalletDoesNotExists extends RuntimeException {

    public WalletDoesNotExists(String message) {
        super(message);
    }
}
