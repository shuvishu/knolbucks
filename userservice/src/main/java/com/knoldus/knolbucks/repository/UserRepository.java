package com.knoldus.knolbucks.repository;

import com.knoldus.knolbucks.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, ObjectId> {

    Flux<User> findAll();
    Mono<User> findByEmployeeId(String employeeId);
    Mono<User> save(User user);
    
}
