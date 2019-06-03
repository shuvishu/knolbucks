package controller;

import com.knoldus.wallet.controller.WalletController;
import com.knoldus.wallet.model.ResponseBody;
import com.knoldus.wallet.model.wallet.RechargeRequest;
import com.knoldus.wallet.model.wallet.RechargeResponse;
import com.knoldus.wallet.service.WalletService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest
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
                                        .walletRequestId("walletId").build())
                                .build()));

        webTestClient.post().uri("/wallet").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(rechargeRequest), RechargeRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

    }

}
