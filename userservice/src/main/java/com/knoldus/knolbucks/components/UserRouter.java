package com.knoldus.knolbucks.components;

import com.knoldus.knolbucks.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class UserRouter {

	@Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {

        return RouterFunctions.route()
                .path("/users", builder -> builder
                        .POST("", accept(MediaType.APPLICATION_JSON), userHandler::createUser)
                        .GET("/{userId}", accept(MediaType.APPLICATION_JSON), userHandler::getUser)
                        .GET("", accept(MediaType.APPLICATION_JSON), userHandler::getUsers)
                        .PUT("", accept(MediaType.APPLICATION_JSON), userHandler::updateUser))
                .build();
    }
}
