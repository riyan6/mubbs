package org.mubbs.seata.account.dao.account;

import org.mubbs.seata.account.domain.entity.account.Account;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.annotation.MapperScan;

public interface AccountMapper extends Mapper<Account> {
}