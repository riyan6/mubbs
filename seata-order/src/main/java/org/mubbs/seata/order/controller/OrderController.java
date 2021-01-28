package org.mubbs.seata.order.controller;

import org.mubbs.seata.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author cpz
 * @Date 2021/1/25
 * @Description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public boolean createOrder(Integer points, String userName, String goods, Integer count) {
        return orderService.createOrder(points, userName, goods, count);
    }

}
