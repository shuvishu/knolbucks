package com.knoldus.wallet.model.wallet;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "wallet")
@Getter
@Entity
public class WalletInfo {

   @Id
   String id;

   String userId;

   int amount;

}
