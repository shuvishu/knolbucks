package com.knoldus.wallet.repository;

import com.knoldus.wallet.model.wallet.RechargeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRechargeRepository extends JpaRepository<RechargeInfo, String> {

    boolean existsByRequesterId(String requesterId);

}
