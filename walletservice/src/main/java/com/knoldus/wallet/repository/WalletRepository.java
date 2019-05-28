package com.knoldus.wallet.repository;

import com.knoldus.wallet.model.RechargeRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WalletRepository {

    Mono<String> rechargeWallet(RechargeRequest rechargeRequest);

    Flux<RechargeRequest> getWalletsWithPendingStatus();

    Mono<RechargeRequest> updateStatusInWalletRecharge(int userId);

    Mono<RechargeRequest> getWallet(int userId);

}
