package com.knoldus.wallet.repository;

import com.knoldus.wallet.model.wallet.RechargeInfo;
import com.knoldus.wallet.model.wallet.WalletInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Mono;

public interface WalletRechargeRepository extends JpaRepository<RechargeInfo, String> {

    boolean existsByRequesterId(String requesterId);

}
