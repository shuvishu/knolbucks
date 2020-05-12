package com.knoldus.knolbucks.service;

import com.knoldus.knolbucks.model.User;
import com.knoldus.knolbucks.model.expections.CustomException;
import com.knoldus.knolbucks.model.request.user.LoginRequest;
import com.knoldus.knolbucks.model.response.user.LoginSuccess;
import com.knoldus.knolbucks.repository.UserRepository;
import com.knoldus.knolbucks.service.auth.JwtTokenService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final JwtTokenService jwtTokenService;

	@Inject
	public UserServiceImpl(UserRepository userRepository, JwtTokenService jwtTokenService) {

		this.userRepository = userRepository;
		this.jwtTokenService = jwtTokenService;
	}

	@Override
	public Flux<User> getUsers() {

		return userRepository.findAll();
	}

	@Override
	public Mono<User> getUser(String employeeId) {
		return userRepository.findByEmployeeId(employeeId);
	}

	@Override
	public Mono<User> createUser(User user) {
		return Mono.fromSupplier(() -> {
			userRepository.save(user).subscribe();
			return user;
		});
	}
/**
 *  updateUser(User user) 
 *  user object which has user information to be updated 
 */
	@Override
	public Mono<User> updateUser(User user) {
		return userRepository.findByEmployeeId(user.getEmployeeId()).doOnSuccess(findUser -> {
			findUser.setName(user.getName());
			userRepository.save(findUser).subscribe();
		});
	}

	@Override
	public Mono<LoginSuccess> loginUser(LoginRequest request) {
		return userRepository.findByEmail(request.getEmail()).flatMap(user -> {
			if(user.getActive()){
				 return Mono.just(LoginSuccess
                         .builder()
                         .jwtToken(jwtTokenService.createToken(user))
                         .build());
			} else {
				user.setActive(true);
				return userRepository.save(user).map(res -> LoginSuccess
						.builder()
						.jwtToken(jwtTokenService.createToken(user))
						.build());
			}
		});
	}
}
