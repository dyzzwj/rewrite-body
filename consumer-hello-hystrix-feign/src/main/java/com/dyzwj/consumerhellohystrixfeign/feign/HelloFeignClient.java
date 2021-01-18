package com.dyzwj.consumerhellohystrixfeign.feign;

import com.dyzwj.consumerhellohystrixfeign.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zwj
 * @version 1.0.0
 * @ClassName HelloClient.java
 * @Description TODO
 * @createTime 2020年01月19日 15:02:00
 */

@FeignClient(name = "producer-hello",fallback = HelloHystrixFeignClient.class,path = "/hello")
//@RequestMapping("/hello")   <==> path = "/hello"
public interface HelloFeignClient {

    @GetMapping("/")
    String hello(@RequestParam Map<String,String> param);


    @GetMapping("/world")
    String world(@RequestParam("username") String username,@RequestParam("password") String password);

    @PostMapping("/post1")
    String post1(User user);

    @GetMapping("/path/{id}")
    String path(@PathVariable String id);

}
