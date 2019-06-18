package com.knoldus.wallet.controller;

import com.knoldus.wallet.model.ResponseBody;
import com.knoldus.wallet.model.wallet.RechargeRequest;
import com.knoldus.wallet.model.wallet.RechargeResponse;
import com.knoldus.wallet.model.wallet.WalletRequestResponse;
import com.knoldus.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping(value = "/wallet", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<ResponseBody<RechargeResponse>> rechargeWallet(@Valid @RequestBody RechargeRequest request) {

        return walletService.rechargeWallet(request);
    }

    @GetMapping(value = "/wallet/pending")
    Mono<ResponseBody<WalletRequestResponse>> getPendingRequests() {

        return walletService.getPendingRequests();
    }
}

