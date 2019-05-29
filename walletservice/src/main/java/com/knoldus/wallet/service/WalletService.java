package com.knoldus.wallet.service;

import com.knoldus.wallet.model.wallet.RechargeInfo;
import com.knoldus.wallet.model.ResponseBody;
import com.knoldus.wallet.model.wallet.RechargeRequest;
import com.knoldus.wallet.model.wallet.RechargeResponse;
import reactor.core.publisher.Mono;

public interface WalletService {

    Mono<ResponseBody<RechargeResponse>> rechargeWallet(RechargeRequest recharge);

//    Flux<RechargeInfo> getWalletWithPendingStatus();
//
//    Mono<RechargeInfo> updateStatusInRechargeRequest(int userId);
//
//    Mono<RechargeInfo> getWallet(int userId);
}
