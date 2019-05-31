package com.knoldus.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS, reason = "Your wallet request has been sent and waiting for the approval")
public class WalletRequestAlreadyPending extends RuntimeException {
}
