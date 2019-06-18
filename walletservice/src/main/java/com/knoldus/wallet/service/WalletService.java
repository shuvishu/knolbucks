package com.knoldus.wallet.service;

import com.knoldus.wallet.model.ResponseBody;
import com.knoldus.wallet.model.wallet.RechargeRequest;
import com.knoldus.wallet.model.wallet.RechargeResponse;
import com.knoldus.wallet.model.wallet.WalletRequestResponse;
import reactor.core.publisher.Mono;

public interface WalletService {

    Mono<ResponseBody<RechargeResponse>> rechargeWallet(RechargeRequest recharge);

    Mono<ResponseBody<WalletRequestResponse>> getPendingRequests();

}
