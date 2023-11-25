package com.heima.gateway.app.filter;

import com.heima.gateway.app.util.AppJwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //得到请求
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //判断是否为登录请求
        String path = request.getURI().getPath();
        if (path.contains("login")){
            log.info("登录请求,放行");
            return chain.filter(exchange);
        }
        //获取token
        String token = request.getHeaders().getFirst("token");
        if (StringUtils.isEmpty(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //校验token
        Claims claimsBody = AppJwtUtil.getClaimsBody(token);
        int verifyToken = AppJwtUtil.verifyToken(claimsBody);
        //防止魔法数字
        if (verifyToken == AppJwtUtil.TIME_OUT_1 || verifyToken == AppJwtUtil.TIME_OUT_2){
            log.info("token过期");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        return chain.filter(exchange)   ;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
