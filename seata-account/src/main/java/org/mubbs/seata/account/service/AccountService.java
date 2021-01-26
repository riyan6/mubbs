package org.mubbs.seata.account.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.mubbs.seata.account.dao.account.AccountMapper;
import org.mubbs.seata.account.domain.entity.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    public void incPoints(String reqUserName, Integer points) {

        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", reqUserName);
        Account account = accountMapper.selectOne(queryWrapper);
        account.setPoints(account.getPoints() + points);

        int i = accountMapper.updateById(account);
        if (i <= 0) {
            throw new RuntimeException("insert exception");
        }
    }

}
