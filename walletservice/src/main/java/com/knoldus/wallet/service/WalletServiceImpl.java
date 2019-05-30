package com.knoldus.wallet.service;

import com.knoldus.wallet.exception.WalletDoesNotExists;
import com.knoldus.wallet.exception.WalletRequestAlreadyPendingException;
import com.knoldus.wallet.model.wallet.RechargeInfo;
import com.knoldus.wallet.model.ResponseBody;
import com.knoldus.wallet.model.wallet.RechargeRequest;
import com.knoldus.wallet.model.wallet.RechargeResponse;
import com.knoldus.wallet.model.wallet.WalletInfo;
import com.knoldus.wallet.model.wallet.WalletStatus;
import com.knoldus.wallet.repository.WalletRechargeRepository;
import com.knoldus.wallet.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@Service
public class WalletServiceImpl implements WalletService {

    private WalletRechargeRepository walletRechargeRepository;

    private WalletRepository walletRepository;

    @Inject
    public WalletServiceImpl(WalletRechargeRepository walletRechargeRepository, WalletRepository walletRepository) {

        this.walletRechargeRepository = walletRechargeRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public Mono<ResponseBody<RechargeResponse>> rechargeWallet(RechargeRequest recharge) {


        return Mono.fromSupplier(() -> {

            String walletId = getWalletDetailsByEmployeeId(recharge.getEmpId()).getId();

            if(walletRechargeRepository.existsByRequesterId(recharge.getEmpId())) {

                throw new WalletRequestAlreadyPendingException();
            }
                   RechargeInfo rechargeInfo = RechargeInfo.builder()
                           .quantity(recharge.getQuantity())
                           .issuerId("Admin1")
                           .requestedOn(Timestamp.valueOf(LocalDateTime.now()))
                           .approvedOn(Timestamp.valueOf(LocalDateTime.now()))
                           .walletId(walletId)
                           .requesterId(recharge.getEmpId())
                           .status(WalletStatus.PENDING.getStatus())
                           .build();

                    RechargeInfo response = walletRechargeRepository.save(rechargeInfo);

                    return ResponseBody.<RechargeResponse>builder()
                            .data(RechargeResponse.builder()
                                    .message("Wallet Request Sent and is Pending for Approval")
                                    .walletRequestId(response.getWalletId())
                                    .build()

                            ).build();
                }

        ).onErrorMap(throwable -> {
            log.error("Unknown exception has occurred due to the following reason " + throwable.getCause());
            throw new RuntimeException(throwable.getCause());
        });
    }

   private WalletInfo getWalletDetailsByEmployeeId(String empId) {

        return walletRepository.findByUserId(empId).orElseThrow(WalletDoesNotExists::new);
   }

}
