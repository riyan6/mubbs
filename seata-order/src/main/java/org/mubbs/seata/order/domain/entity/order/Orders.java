package org.mubbs.seata.order.domain.entity.order;

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
@NoArgsConstructor
@AllArgsConstructor
@TableName("orders")
public class Orders {

    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;

    @TableField("user_name")
    private String userName;

    @TableField("points")
    private Integer points;

    private String goods;

    private Integer quantity;

    private Integer money;
}