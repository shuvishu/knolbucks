package controller;

import com.knoldus.wallet.WalletApplication;
import com.knoldus.wallet.model.ResponseBody;
import com.knoldus.wallet.model.wallet.RechargeInfo;
import com.knoldus.wallet.model.wallet.RechargeRequest;
import com.knoldus.wallet.model.wallet.RechargeResponse;
import com.knoldus.wallet.model.wallet.WalletRequestResponse;
import com.knoldus.wallet.service.WalletService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WalletApplication.class)
public class WalletControllerTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private WalletService service;

    @Test
    public void testRechargeWallet() {

        RechargeRequest rechargeRequest = RechargeRequest.builder().empId("1").quantity(2).build();

        when(service.rechargeWallet(rechargeRequest))
                .thenReturn(Mono
                        .just(ResponseBody
                                .<RechargeResponse>builder()
                                .data(RechargeResponse.builder()
                                        .message("Wallet Request Sent and is Pending for Approval")
                                        .build())
                                .build()));

        webTestClient.post().uri("/wallet").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(rechargeRequest), RechargeRequest.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testGetPendingRequests() {

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
        when(service.getPendingRequests())
                .thenReturn(Mono
                        .just(ResponseBody.<WalletRequestResponse>builder()
                                .data(WalletRequestResponse.builder()
                                        .rechargeRequests(pendingRequests)
                                        .build()
                                ).build()));

        webTestClient.get().uri("/wallet/pending")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ResponseBody<WalletRequestResponse>>() {
                })
                .value(response -> {
                            Assertions.assertThat(response.getData().getRechargeRequests().get(0).getId()).isEqualTo("1");
                            Assertions.assertThat(response.getData().getRechargeRequests().get(0).getStatus()).isEqualTo("Pending");
                            Assertions.assertThat(response.getData().getRechargeRequests().get(1).getId()).isEqualTo("2");
                            Assertions.assertThat(response.getData().getRechargeRequests().get(1).getStatus()).isEqualTo("Pending");
                        }
                );
    }

}
