package org.mubbs.seata.account.domain.entity.account;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("account")
public class Account {

    @TableId(value = "account_id", type = IdType.AUTO)
    private Integer accountId;

    @TableField("user_name")
    private String userName;

    private Integer points;
}