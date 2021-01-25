package org.mubbs.seata.order.domain.entity.order;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`order`")
public class Order {

    @Id
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "points")
    private Integer points;

    private String goods;

    private Integer quantity;

    private Integer money;
}