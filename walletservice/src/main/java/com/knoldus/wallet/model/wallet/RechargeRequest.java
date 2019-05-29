package com.knoldus.wallet.model.wallet;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class RechargeRequest {

    @NotBlank(message = "The quantity can not be null")
    int quantity;

    @NotBlank(message = "The employee Id cannot be null")
    String empId;
}
