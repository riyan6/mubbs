package org.mubbs.seata.order.configuration;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author cpz
 * @Date 2021/1/28
 * @Description: feign 配置 解决 outTime 问题？
 */
@Configuration
public class ServiceFeignConfiguration {

    /**
     * 连接超时时间
     */
    private int connectTimeout = 300000;

    /**
     * 读取时间
     */
    private int readTimeout = 100000;

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeout, readTimeout);
    }

}
