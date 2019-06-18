package com.knoldus.wallet.repository;

import com.knoldus.wallet.model.wallet.RechargeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRechargeRepository extends JpaRepository<RechargeInfo, String> {

    boolean existsByRequesterId(String requesterId);

    List<RechargeInfo> findRechargeInfoByStatusEquals(String status);

}
