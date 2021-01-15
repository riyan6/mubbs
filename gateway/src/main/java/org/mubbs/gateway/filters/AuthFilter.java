package org.mubbs.gateway.filters;

import com.alibaba.fastjson.JSON;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author cpz
 * @Date 2021/1/15
 * @Description: 授权过滤器
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    /**
     * 鉴权过滤器
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        // 获取请求头的token
//        String token = exchange.getRequest().getHeaders().getFirst("token");
//        // 如果token不合法
//        if (token == null || token.isEmpty()) {
//            ServerHttpResponse response = exchange.getResponse();
//            // 封装错误信息
//            Map<String, Object> responseMap = new ConcurrentHashMap<>();
//            responseMap.put("code", HttpStatus.UNAUTHORIZED.value());
//            responseMap.put("msg", "非法请求");
//            try {
//                // 转为json
//                String jsonStr = JSON.toJSONString(responseMap);
//                byte[] data = jsonStr.getBytes();
//                // 输出错误信息到页面
//                DataBuffer buffer = response.bufferFactory().wrap(data);
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
//                return response.writeWith(Mono.just(buffer));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        return chain.filter(exchange);
    }

    /**
     * 设置过滤器的执行顺序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
