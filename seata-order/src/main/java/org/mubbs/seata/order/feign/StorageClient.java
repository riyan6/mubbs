package org.mubbs.seata.order.feign;

import org.mubbs.seata.order.configuration.ServiceFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "seataStorage", configuration = ServiceFeignConfiguration.class)
public interface StorageClient {

    @PostMapping("/storage/{goods}/{count}/dec")
    void decStorage(@PathVariable("goods") String goods, @PathVariable("count") Integer count);

}
