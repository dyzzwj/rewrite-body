package com.dyzwj.consumerhellohystrixfeign.controller;

import com.dyzwj.consumerhellohystrixfeign.feign.HelloFeignClient;
import com.dyzwj.consumerhellohystrixfeign.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zwj
 * @version 1.0.0
 * @ClassName HelloController.java
 * @Description TODO
 * @createTime 2020年01月19日 14:55:00
 */
@RestController
public class HelloController {

    @Autowired
    private HelloFeignClient helloFeignClient;

//    @HystrixCommand(fallbackMethod = "helloFallback")
    @GetMapping("/hello")
    public String hello(User user){
        Map<String,String> param = new HashMap<>();
        param.put("username",user.getUsername());
        param.put("password",user.getPassword());
        return helloFeignClient.hello(param);
    }

    public String helloFallback(User user){
        return "fallback";
    }

    @GetMapping("/world")
    public String world(String username,String password){
        return helloFeignClient.world(username,password);
    }

    @PostMapping("/post1")
    public String post1(User user){
        return helloFeignClient.post1(user);
    }

    @GetMapping("/path/{id}")
    public String path(@PathVariable String id){
        System.out.println(id);
        return helloFeignClient.path(id);
    }


}
