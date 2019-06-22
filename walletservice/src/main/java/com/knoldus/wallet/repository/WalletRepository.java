package com.knoldus.wallet.repository;

import com.knoldus.wallet.model.wallet.WalletInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletInfo, String> {

    Optional<WalletInfo> findByUserId(String userId);
}
