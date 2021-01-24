package org.mubbs.seata.account.service;

import org.mubbs.seata.account.dao.account.AccountMapper;
import org.mubbs.seata.account.domain.entity.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    public void incPoints(String userName, Integer points) {
        Account account = accountMapper.selectOne(Account.builder().userName(userName).build());
        account.setPoints(account.getPoints() + points);
        int i = accountMapper.updateByPrimaryKeySelective(account);
        if (i <= 0) {
            throw new RuntimeException("insert exception");
        }
    }

}
