package com.knoldus.wallet.service;

import com.knoldus.wallet.exception.NoPendingRequestException;
import com.knoldus.wallet.exception.UserDoesNotExistException;
import com.knoldus.wallet.exception.WalletRequestAlreadyPendingException;
import com.knoldus.wallet.model.ResponseBody;
import com.knoldus.wallet.model.wallet.RechargeInfo;
import com.knoldus.wallet.model.wallet.RechargeRequest;
import com.knoldus.wallet.model.wallet.RechargeResponse;
import com.knoldus.wallet.model.wallet.WalletInfo;
import com.knoldus.wallet.model.wallet.WalletRequestResponse;
import com.knoldus.wallet.model.wallet.WalletStatus;
import com.knoldus.wallet.repository.WalletRechargeRepository;
import com.knoldus.wallet.repository.WalletRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.knoldus.wallet.model.WalletConstants.NO_RECHARGE_REQUEST_FOUND;
import static com.knoldus.wallet.model.WalletConstants.USER_DOES_NOT_EXIST;
import static com.knoldus.wallet.model.WalletConstants.WALLET_RECHARGE_SUCCESS_MESSAGE;
import static com.knoldus.wallet.model.WalletConstants.WALLET_REQUEST_ALREADY_PENDING;


@Slf4j
@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private WalletRechargeRepository walletRechargeRepository;

    private WalletRepository walletRepository;

    @Override
    public Mono<ResponseBody<RechargeResponse>> rechargeWallet(RechargeRequest recharge) {

        return Mono.fromSupplier(() -> {

                    String walletId = getWalletDetailsByEmployeeId(recharge.getEmpId()).getId();
                    if (walletRechargeRepository.existsByRequesterId(recharge.getEmpId())) {

                        log.error("wallet request has been sent and waiting for the approval for employee having id {}", recharge.getEmpId());

                        throw new WalletRequestAlreadyPendingException(WALLET_REQUEST_ALREADY_PENDING);
                    }

                    walletRechargeRepository.save(RechargeInfo.builder()
                            .id(UUID.randomUUID().toString())
                            .quantity(recharge.getQuantity())
                            .issuerId("Admin1")
                            .requestedOn(Timestamp.valueOf(LocalDateTime.now()))
                            .walletId(walletId)
                            .requesterId(recharge.getEmpId())
                            .status(WalletStatus.PENDING.getStatus())
                            .build());

                    return ResponseBody.<RechargeResponse>builder()
                            .data(RechargeResponse.builder()
                                    .message(WALLET_RECHARGE_SUCCESS_MESSAGE)
                                    .build()

                            ).build();
                }
        );
    }

    @Override
    public Mono<ResponseBody<WalletRequestResponse>> getPendingRequests() {
        return Mono.fromSupplier(() -> {

            List<RechargeInfo> pendingRequests = walletRechargeRepository
                    .findRechargeInfoByStatusEquals(WalletStatus.PENDING.getStatus());
            if (pendingRequests.isEmpty()) {

                log.error("No wallet request with pending request found");

                throw new NoPendingRequestException(NO_RECHARGE_REQUEST_FOUND);
            } else {

                return ResponseBody.<WalletRequestResponse>builder()
                        .data(WalletRequestResponse.builder()
                                .rechargeRequests(pendingRequests)
                                .build()
                        ).build();
            }
        });
    }


    private WalletInfo getWalletDetailsByEmployeeId(String empId) {

        log.info("Fetching Wallet details of employee {}", empId);

        return walletRepository.findByUserId(empId).orElseThrow(() -> {

            log.error("User having id {} does not exist", empId);
            return new UserDoesNotExistException(USER_DOES_NOT_EXIST);
        });
    }
}
