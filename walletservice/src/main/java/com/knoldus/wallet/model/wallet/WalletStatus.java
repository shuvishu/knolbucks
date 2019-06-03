package com.knoldus.wallet.model.wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WalletStatus {

    APPROVED("Approved"),
    PENDING("Pending"),
    REJECTED("Rejected");

  private String status;

}
