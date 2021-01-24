package org.mubbs.seata.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "seataAccount")
public interface AccountClient {

    @PostMapping("/account/{user}/{points}/points")
    void addPoints(@PathVariable("points") Integer points, @PathVariable("user") String user);

}
