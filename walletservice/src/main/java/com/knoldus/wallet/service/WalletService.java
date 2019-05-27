package com.knoldus.wallet.service;

import com.knoldus.wallet.model.RechargeRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WalletService {

    Mono<String> rechargeWallet(RechargeRequest rechargeRequest);

    Flux<RechargeRequest> getWalletWithPendingStatus();

    Mono<RechargeRequest> updateStatusInRechargeRequest(int userId);
}
