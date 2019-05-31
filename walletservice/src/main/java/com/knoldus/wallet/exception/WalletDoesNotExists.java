package com.knoldus.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "wallet does not exists ")
public class WalletDoesNotExists extends RuntimeException {

}
