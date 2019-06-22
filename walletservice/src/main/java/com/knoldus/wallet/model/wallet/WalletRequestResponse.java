package com.knoldus.wallet.model.wallet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class WalletRequestResponse {

    List<RechargeInfo> rechargeRequests;
}