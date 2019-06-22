package com.knoldus.wallet.model.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Table(name = "wallet_recharge_request")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargeInfo {

    @Id
    @Column(name = "id")
    String id;

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
