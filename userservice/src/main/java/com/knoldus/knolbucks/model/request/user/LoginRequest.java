package com.knoldus.knolbucks.model.request.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.executable.ValidateOnExecution;

@Getter
@Builder
public class LoginRequest {

    @NotBlank(message = "The employee email cannot be blank")
    @NotNull
    @Pattern(regexp = "^[\\w.+\\-]+@knoldus\\.(in|com)$")
    String email;

    @Override
    public String toString() {
        return this.email;
    }
}
