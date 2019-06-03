package com.knoldus.wallet.exception;

public class WalletRequestAlreadyPending extends RuntimeException {

    public WalletRequestAlreadyPending(String message) {
        super(message);
    }
}
