package com.knoldus.knolbucks.service;

import com.knoldus.knolbucks.model.User;
import com.knoldus.knolbucks.model.request.user.LoginRequest;
import com.knoldus.knolbucks.model.response.user.LoginSuccess;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<User> getUsers();
    Mono<User> getUser(String userId);
    Mono<User> createUser(User user);
    Mono<User> updateUser(User user);
    Mono<LoginSuccess> loginUser(LoginRequest request);
}
