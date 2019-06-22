package com.knoldus.wallet.model.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "wallet")
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletInfo {

   @Id
   String id;

   @Column(name = "userId")
   String userId;

   @Column(name = "amount")
   int amount;

}
