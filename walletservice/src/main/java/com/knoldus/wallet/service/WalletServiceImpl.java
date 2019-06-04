package com.knoldus.wallet.service;

import com.knoldus.wallet.exception.WalletDoesNotExists;
import com.knoldus.wallet.exception.WalletRequestAlreadyPending;
import com.knoldus.wallet.model.ResponseBody;
import com.knoldus.wallet.model.wallet.RechargeInfo;
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
import java.util.UUID;

import static com.knoldus.wallet.model.WalletConstants.WALLET_DOES_NOT_EXIST;
import static com.knoldus.wallet.model.WalletConstants.WALLET_RECHARGE_SUCCESS_MESSAGE;
import static com.knoldus.wallet.model.WalletConstants.WALLET_REQUEST_ALREADY_PENDING;

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
                    if (walletRechargeRepository.existsByRequesterId(recharge.getEmpId())) {

                        log.error("wallet request has been sent and waiting for the approval for employee having id {}", recharge.getEmpId());

                        throw new WalletRequestAlreadyPending(WALLET_REQUEST_ALREADY_PENDING);
                    }

                    RechargeInfo response = walletRechargeRepository.save(RechargeInfo.builder()
                            .id(UUID.randomUUID().toString())
                            .quantity(recharge.getQuantity())
                            .issuerId("Admin1")
                            .requestedOn(Timestamp.valueOf(LocalDateTime.now()))
                            .approvedOn(Timestamp.valueOf(LocalDateTime.now()))
                            .walletId(walletId)
                            .requesterId(recharge.getEmpId())
                            .status(WalletStatus.PENDING.getStatus())
                            .build());

                    return ResponseBody.<RechargeResponse>builder()
                            .data(RechargeResponse.builder()
                                    .message(WALLET_RECHARGE_SUCCESS_MESSAGE)
                                    .walletRequestId(response.getWalletId())
                                    .build()

                            ).build();
                }

        );
    }

    private WalletInfo getWalletDetailsByEmployeeId(String empId) {

        log.info("Fetching Wallet details of employee {}", empId);

        return walletRepository.findByUserId(empId).orElseThrow(() -> {

            log.error("Wallet does not exists for empId {}", empId);
            return new WalletDoesNotExists(WALLET_DOES_NOT_EXIST);
        });
    }
}
