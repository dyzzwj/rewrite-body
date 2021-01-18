package com.dyzwj.consumerhellohystrixfeign.Feign;

import com.dyzwj.consumerhellohystrixfeign.bean.User;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zwj
 * @version 1.0.0
 * @ClassName HelloHystrixFeignClient.java
 * @Description TODO
 * @createTime 2020年01月20日 10:32:00
 */
@Component
public class HelloHystrixFeignClient implements HelloFeignClient {
    @Override
    public String hello(Map<String, String> param) {
        return "hello fallback";
    }

    @Override
    public String world(String username, String password) {
        return "world fallback";
    }

    @Override
    public String post1(User user) {
        return "post1 fallback";
    }

    @Override
    public String path(String id) {
        return "path fallback";
    }
}
