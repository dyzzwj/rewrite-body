package com.dyzwj.consumerhellohystrixribbon.controller;

import com.dyzwj.consumerhellohystrixribbon.hystrix.command.HelloHystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * @author zwj
 * @version 1.0.0
 * @ClassName HelloController.java
 * @Description TODO
 * @createTime 2020年01月15日 19:53:00
 */

@RestController
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;

    private Random random = new Random();


    @HystrixCommand(fallbackMethod = "helloFallback")
    @GetMapping("/hello")
    public String hello(){
//        int i = 10/0;
        return restTemplate.getForObject("http://producer-hello/hello",String.class);

    }

    public String helloFallback(){
        return "helloFallback";
    }


    @HystrixCommand(fallbackMethod = "hello2Fallback",commandProperties = {
            //设置操作时间为100ms
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "100")
    })
    @GetMapping("/hello2")
    public String hello2() throws InterruptedException {
        int i = random.nextInt(200);
        System.out.println(i);
        //通过休眠来模拟执行时间
        Thread.sleep(i);
        return "xixi";
    }


    public String hello2Fallback(){
        return "hello2Fallback";
    }


    @GetMapping("/hello3")
    public String hello3(){
        return new HelloHystrixCommand().execute();
    }



}
