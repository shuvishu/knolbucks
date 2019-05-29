package com.knoldus.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Wallet Details does not exist for empId")
public class WalletDoesNotExists extends RuntimeException {
}
