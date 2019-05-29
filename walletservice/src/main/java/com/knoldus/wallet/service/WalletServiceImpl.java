package com.knoldus.wallet.service;

import com.knoldus.wallet.model.wallet.RechargeInfo;
import com.knoldus.wallet.model.ResponseBody;
import com.knoldus.wallet.model.wallet.RechargeRequest;
import com.knoldus.wallet.model.wallet.RechargeResponse;
import com.knoldus.wallet.model.wallet.WalletStatus;
import com.knoldus.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class WalletServiceImpl implements WalletService {

    private WalletRepository walletRepository;

    @Inject
    public WalletServiceImpl(WalletRepository walletRepository) {

        this.walletRepository = walletRepository;
    }

    @Override
    public Mono<ResponseBody<RechargeResponse>> rechargeWallet(RechargeRequest recharge) {


        return Mono.fromSupplier(() -> {
                   RechargeInfo rechargeInfo = RechargeInfo.builder()
                           .quantity(recharge.getQuantity())
                           .issuerId(recharge.getEmpId())
                           .requestedOn(Timestamp.valueOf(LocalDateTime.now()))
                           .approvedOn(Timestamp.valueOf(LocalDateTime.now()))
                           .walletId("")
                           .requesterId("")
                           .status(WalletStatus.PENDING.getStatus())
                           .build();

            System.out.println("asd/sdfghjkl;" + rechargeInfo.getApprovedOn());
                    RechargeInfo response = walletRepository.save(rechargeInfo);
                    return ResponseBody.<RechargeResponse>builder()
                            .data(RechargeResponse.builder()
                                    .message("Wallet Request Sent and is Pending for Approval")
                                    .walletRequestId(response.getWalletId())
                                    .build()

                            ).build();
                }

        );
    }

}
