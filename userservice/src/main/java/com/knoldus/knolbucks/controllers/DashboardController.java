package com.knoldus.knolbucks.controllers;


import com.knoldus.knolbucks.model.request.user.LoginRequest;
import com.knoldus.knolbucks.model.response.GenericResponse;
import com.knoldus.knolbucks.model.response.user.LoginSuccess;
import com.knoldus.knolbucks.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
public class DashboardController {
    private final UserService userService;
    @Inject
    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    Mono<GenericResponse<LoginSuccess>> userLogin(@Valid @RequestBody LoginRequest request){
        return userService.loginUser(request).map(res -> GenericResponse.<LoginSuccess>builder().data(res).build());
    }
}
