package com.knoldus.wallet.controller;

import com.knoldus.wallet.model.ResponseBody;
import com.knoldus.wallet.model.wallet.RechargeRequest;
import com.knoldus.wallet.model.wallet.RechargeResponse;
import com.knoldus.wallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
public class WalletController {

    private final WalletService walletService;

    @Inject
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping(value = "/wallet", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<ResponseBody<RechargeResponse>> rechargeWallet(@Valid @RequestBody RechargeRequest request) {

        return walletService.rechargeWallet(request);
    }
}

