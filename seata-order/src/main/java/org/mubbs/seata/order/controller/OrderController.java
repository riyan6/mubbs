package org.mubbs.seata.order.controller;

import org.mubbs.seata.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String createOrder(Integer points, String userName, String goods, Integer count) {
        boolean flag = orderService.createOrder(points, userName, goods, count);
        return  flag ? "处理成功！" : "处理失败";
    }

}
