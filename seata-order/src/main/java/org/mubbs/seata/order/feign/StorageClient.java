package org.mubbs.seata.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "seataStorage")
public interface StorageClient {

    @PostMapping("/storage/{goods}/{count}/dec")
    void decStorage(@PathVariable("goods") String goods, @PathVariable("count") Integer count);

}
