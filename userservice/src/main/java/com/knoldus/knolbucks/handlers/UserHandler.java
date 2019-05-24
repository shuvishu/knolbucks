package com.knoldus.knolbucks.handlers;

import com.knoldus.knolbucks.model.User;
import com.knoldus.knolbucks.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Iterator;

@Component
public class UserHandler {

	private final UserService userService;

	@Autowired
	private Validator validator;

	@Inject
	public UserHandler(UserService userService, RequestHandler handler) {

		this.userService = userService;
	}

	public Mono<ServerResponse> updateUser(ServerRequest request) {
		Mono<User> user = request.bodyToMono(User.class);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(fromPublisher(user.flatMap(userService::updateUser), User.class));
	}

	public Mono<ServerResponse> createUser(ServerRequest request) {
		return request.bodyToMono(User.class).flatMap(user -> {
			String message = validation(user);
			if (message == null) {
				return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(
						fromPublisher(request.bodyToMono(User.class).flatMap(userService::createUser), User.class));
			} else {
				return Mono.error(new RuntimeException(message));
			}
		});
//        return requestHandler.requireValidBody(body -> {
//            Mono<User> userMono = body.map(addUser -> User.of(addUser.getName(), addUser.getEmail(), addUser.getActive(), addUser.getDateOfBirth() ));
//        Mono<User> user = request.bodyToMono(User.class);

//        }
	}

	private <T> String validation(T t) {
		Iterator<ConstraintViolation<T>> iterator = validator.validate(t).iterator();
		while (iterator.hasNext()) {
			ConstraintViolation<T> voil = iterator.next();
			return voil.getMessage();
		}
		return null;
	}

	public Mono<ServerResponse> getUser(ServerRequest request) {
		String userId = request.pathVariable("userId");
		final Mono<User> user = userService.getUser(userId);
		return user.flatMap(p -> ok().contentType(APPLICATION_JSON).body(fromPublisher(user, User.class)))
				.switchIfEmpty(notFound().build());
	}

	public Mono<ServerResponse> getUsers(ServerRequest request) {
		return ok().contentType(APPLICATION_JSON).body(fromPublisher(userService.getUsers(), User.class));
	}

}
