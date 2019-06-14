package service;

import com.knoldus.wallet.exception.NoPendingRequestException;
import com.knoldus.wallet.model.ResponseBody;
import com.knoldus.wallet.model.wallet.RechargeInfo;
import com.knoldus.wallet.model.wallet.RechargeRequest;
import com.knoldus.wallet.model.wallet.RechargeResponse;
import com.knoldus.wallet.model.wallet.WalletInfo;
import com.knoldus.wallet.model.wallet.WalletRequestResponse;
import com.knoldus.wallet.model.wallet.WalletStatus;
import com.knoldus.wallet.repository.WalletRechargeRepository;
import com.knoldus.wallet.repository.WalletRepository;
import com.knoldus.wallet.service.WalletServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WalletServiceImplTest {

    private final WalletRepository walletRepository = mock(WalletRepository.class);

    private final WalletRechargeRepository walletRechargeRepository = mock(WalletRechargeRepository.class);

    private final WalletServiceImpl walletService = new WalletServiceImpl(walletRechargeRepository, walletRepository);

    @Test
    public void shouldAbleToRechargeWallet() {

        Optional<WalletInfo> walletInfo = Optional.of(WalletInfo.builder()
                .id("walletId")
                .userId("empId")
                .amount(200).build());

        RechargeInfo rechargeInfo = RechargeInfo.builder()
                .requesterId("empId")
                .walletId("walletId")
                .requestedOn(Timestamp.valueOf(LocalDateTime.now()))
                .quantity(3)
                .id("id")
                .status("status")
                .issuerId("issuerId")
                .build();

        RechargeRequest rechargeRequest = RechargeRequest.builder().quantity(3).empId("empId").build();

        when(walletRechargeRepository.existsByRequesterId("empId")).thenReturn(false);

        when(walletRepository.findByUserId("empId")).thenReturn(walletInfo);

        when(walletRechargeRepository.save(any(RechargeInfo.class))).thenReturn(rechargeInfo);

        RechargeResponse response = walletService.rechargeWallet(rechargeRequest).block().getData();
        assertNotNull(response);
        assertEquals("Wallet Request Sent and is Pending for Approval", response.getMessage());

    }


    @Test
    public void shouldFailToRechargeWalletAsWalletDoesNotExists() {

        RechargeRequest rechargeRequest = RechargeRequest.builder().quantity(3).empId("empId").build();

        when(walletRepository.findByUserId("empId")).thenReturn(Optional.empty());

        try {

            walletService.rechargeWallet(rechargeRequest).block();
            fail("An exception should be thrown in try block.");
        } catch (Exception ex) {
            assertThat(ex.getMessage(), containsString("User does not exist"));
        }
    }

    @Test
    public void shouldFailToRechargeWalletAsWalletRequestIsAlreadyPending() {

        RechargeRequest rechargeRequest = RechargeRequest.builder().quantity(3).empId("empId").build();

        Optional<WalletInfo> walletInfo = Optional.of(WalletInfo.builder()
                .id("walletId")
                .userId("empId")
                .amount(200)
                .build());

        when(walletRepository.findByUserId("empId")).thenReturn(walletInfo);

        when(walletRechargeRepository.existsByRequesterId("empId")).thenReturn(true);

        try {

            walletService.rechargeWallet(rechargeRequest).block();
            fail("An exception should be thrown in try block.");
        } catch (Exception ex) {
            assertThat(ex.getMessage(), containsString("Your wallet request has been sent and waiting for the approval"));
        }
    }

    @Test
    public void shouldAbleToGetPendingRequests() {

        RechargeInfo rechargeInfo1 = RechargeInfo.builder()
                .id("1")
                .walletId("wallet1")
                .requesterId("requester1")
                .issuerId("issuer1")
                .status("Pending")
                .build();
        RechargeInfo rechargeInfo2 = RechargeInfo.builder()
                .id("2")
                .walletId("wallet2")
                .requesterId("requester2")
                .issuerId("issuer2")
                .status("Pending")
                .build();

        List<RechargeInfo> pendingRequests = Arrays.asList(rechargeInfo1, rechargeInfo2);

        when(walletRechargeRepository.findRechargeInfoByStatusEquals(WalletStatus.PENDING.getStatus())).thenReturn(pendingRequests);

        Mono<ResponseBody<WalletRequestResponse>> walletServicePendingRequests = walletService.getPendingRequests();

        StepVerifier.create(walletServicePendingRequests)
                .expectSubscription()
                .consumeNextWith(response -> {
                    Assertions.assertThat(response.getData().getRechargeRequests().get(0).getId()).isEqualTo("1");
                    Assertions.assertThat(response.getData().getRechargeRequests().get(0).getStatus()).isEqualTo("Pending");
                    Assertions.assertThat(response.getData().getRechargeRequests().get(1).getId()).isEqualTo("2");
                    Assertions.assertThat(response.getData().getRechargeRequests().get(1).getStatus()).isEqualTo("Pending");
                })
                .verifyComplete();

    }

    @Test
    public void shouldThrowNoPendingRequestExceptionOnGetPendingRequests() {

        List<RechargeInfo> pendingRequests = Collections.emptyList();

        when(walletRechargeRepository.findRechargeInfoByStatusEquals(WalletStatus.PENDING.getStatus())).thenReturn(pendingRequests);

        Mono<ResponseBody<WalletRequestResponse>> walletServicePendingRequests = walletService.getPendingRequests();

        StepVerifier.create(walletServicePendingRequests.log())
                .expectSubscription()
                .expectError(NoPendingRequestException.class)
                .verify();

    }
}
