package com.knoldus.knolbucks.model.response.user;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginSuccess {
    String jwtToken;
}
