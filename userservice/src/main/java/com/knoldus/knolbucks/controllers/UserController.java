package com.knoldus.knolbucks.controllers;

import com.knoldus.knolbucks.model.User;
import com.knoldus.knolbucks.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    @Inject
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "")
    public Mono<User> createUser(@Valid @RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    public Mono<User> getUserById(@PathVariable String userId){
        return userService.getUser(userId);
    }

    @GetMapping("")
    public Flux<User> getUsers(){
        return userService.getUsers();
    }

    @PutMapping("")
    public Mono<User> updateUser(@Valid @RequestBody User user){
        return userService.updateUser(user);
    }

}
