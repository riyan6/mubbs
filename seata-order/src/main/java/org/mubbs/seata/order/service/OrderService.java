package org.mubbs.seata.order.service;

import org.mubbs.seata.order.dao.order.OrderMapper;
import org.mubbs.seata.order.domain.entity.order.Order;
import org.mubbs.seata.order.feign.AccountClient;
import org.mubbs.seata.order.feign.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private StorageClient storageClient;

    public void createOrder() {
        accountClient.addPoints(100, "");
        storageClient.decStorage("coco", 10);
        orderMapper.insertSelective(Order.builder()
                .goods("")
                .money(100)
                .points(100)
                .quantity(10)
                .userName("aabb")
                .build());
    }

}
