package com.knoldus.wallet.model.wallet;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Table(name = "coupon_requests")
@Entity
@Builder
public class RechargeInfo {

    @Id
    int id;

    String walletId;

    String requesterId;

    String issuerId;

    Timestamp requestedOn;

    Timestamp approvedOn;

    int quantity;

    String status;
}
