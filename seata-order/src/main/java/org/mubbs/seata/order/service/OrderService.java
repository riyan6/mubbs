package org.mubbs.seata.order.service;

import io.seata.spring.annotation.GlobalTransactional;
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

    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean createOrder(Integer points, String userName, String goods, Integer count) {
        try {
            accountClient.addPoints(points * count, userName);
            storageClient.decStorage(goods, count);
            orderMapper.insert(Order.builder()
                    .goods(goods)
                    .money(points * count)
                    .points(points * count)
                    .quantity(count)
                    .userName(userName)
                    .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
