package com.knoldus.wallet.repository;

import com.knoldus.wallet.model.wallet.RechargeInfo;
import com.knoldus.wallet.model.wallet.WalletInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Mono;

public interface WalletRepository extends JpaRepository<RechargeInfo, String> {


//    Mono<String> rechargeWallet(RechargeInfo rechargeRequest);
//
//    Flux<RechargeInfo> getWalletsWithPendingStatus();
//
//    Mono<RechargeInfo> updateStatusInWalletRecharge(int userId);
//
//    Mono<RechargeInfo> getWallet(int userId);

}
