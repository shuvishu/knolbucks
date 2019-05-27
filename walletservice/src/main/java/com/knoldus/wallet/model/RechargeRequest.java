package com.knoldus.wallet.model;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class RechargeRequest {

    String id;

    String walletId;

    String requesterId;

    String issuerId;

    Timestamp requestedOn;

    Timestamp approvedOn;

    int quantity;

    String status;
}
