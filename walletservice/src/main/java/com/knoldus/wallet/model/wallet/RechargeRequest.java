package com.knoldus.wallet.model.wallet;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class RechargeRequest {

    @NotNull(message = "The quantity can not be null")
    @Max(value = 5, message = "The max value of quantity should be less than 5")
    int quantity;

    @NotBlank(message = "The employee Id cannot be blank")
    String empId;
}
