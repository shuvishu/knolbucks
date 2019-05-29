package com.knoldus.wallet.model.wallet;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RechargeResponse {

    String message;

    String walletRequestId;
}
