package com.knoldus.wallet.repository;

import com.knoldus.wallet.model.RechargeRequest;
import org.davidmoten.rx.jdbc.Database;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

public class WalletRepositoryImpl implements WalletRepository {

    private Database database;

    @Inject
    public WalletRepositoryImpl() {
        database = Database.from("jdbc:mysql://localhost:3306/knolbucks?user=root&password=root", 10);
    }

   // TODO
    @Override
    public Mono<String> rechargeWallet(RechargeRequest request) {
      return null;

    }

    // TODO
    @Override
    public Flux<RechargeRequest> getWalletWithPendingStatus() {
        return null;
    }

    // TODO
    @Override
    public Mono<RechargeRequest> updateStatusInWalletRecharge(int userId) {
        return null;
    }
}
