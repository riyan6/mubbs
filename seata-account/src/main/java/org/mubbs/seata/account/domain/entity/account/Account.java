package org.mubbs.seata.account.domain.entity.account;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "user_name")
    private String userName;

    private Integer points;
}