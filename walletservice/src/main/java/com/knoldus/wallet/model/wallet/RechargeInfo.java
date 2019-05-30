package com.knoldus.wallet.model.wallet;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
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

    @Column(name = "walletId")
    String walletId;

    @Column(name = "requesterId")
    String requesterId;

    @Column(name = "issuerId")
    String issuerId;

    @Column(name = "requestedOn")
    Timestamp requestedOn;

    @Column(name = "approvedOn")
    Timestamp approvedOn;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "status")
    String status;
}
