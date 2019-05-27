package com.knoldus.wallet.handler;

import com.knoldus.wallet.model.RechargeRequest;
import com.knoldus.wallet.service.WalletService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Component
public class WalletHandler {

    private final WalletService walletService;

    @Inject
    public WalletHandler(WalletService walletService) {
        this.walletService = walletService;
    }

    public Mono<ServerResponse> rechargeWallet(ServerRequest serverRequest) {
        Mono<RechargeRequest> walletRechargeRequest = serverRequest.bodyToMono(RechargeRequest.class);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(walletRechargeRequest.flatMap(walletService::rechargeWallet), String.class));

    }

    public Mono<ServerResponse> getWalletWithPendingStatus(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(walletService.getWalletWithPendingStatus(), RechargeRequest.class));

    }

    public Mono<ServerResponse> updateStatusOfWallet(ServerRequest serverRequest) {
        final int userId = Integer.parseInt(serverRequest.pathVariable("userId"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(walletService.updateStatusInRechargeRequest(userId),
                        RechargeRequest.class));
    }
}
