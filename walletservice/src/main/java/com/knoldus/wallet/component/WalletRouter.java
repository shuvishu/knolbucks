package com.knoldus.wallet.component;

import com.knoldus.wallet.handler.WalletHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.http.MediaType;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class WalletRouter {

    @Bean
    public RouterFunction<ServerResponse> walletRoutes(WalletHandler walletHandler) {

        return RouterFunctions.route()
                .path("/wallet", builder -> builder
                        .POST("", accept(MediaType.APPLICATION_JSON), walletHandler::rechargeWallet)
                        .GET("", accept(MediaType.APPLICATION_JSON), walletHandler::getWalletsWithPendingStatus)
                        .GET("/{userId}", accept(MediaType.APPLICATION_JSON), walletHandler::getWallet)
                        .PUT("/wallet/{userId}", accept(MediaType.APPLICATION_JSON), walletHandler::updateStatusOfWallet)
                )
                .build();
    }

}

