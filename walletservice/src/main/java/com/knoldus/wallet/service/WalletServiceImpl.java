package com.knoldus.wallet.service;

import com.knoldus.wallet.model.RechargeRequest;
import com.knoldus.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

@Service
public class WalletServiceImpl implements WalletService {

    private WalletRepository walletRepository;

    @Inject
    public WalletServiceImpl(WalletRepository walletRepository) {

        this.walletRepository = walletRepository;
    }

    // TODO
    @Override
    public Mono<String> rechargeWallet(RechargeRequest rechargeRequest) {
        return walletRepository.rechargeWallet(rechargeRequest);
    }

   // TODO
    @Override
    public Flux<RechargeRequest> getWalletWithPendingStatus() {
        return walletRepository.getWalletWithPendingStatus();
    }

    // TODO
    @Override
    public Mono<RechargeRequest> updateStatusInRechargeRequest(int userId) {

        return walletRepository.updateStatusInWalletRecharge(userId);
    }
}
