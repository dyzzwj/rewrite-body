package com.dyzwj.consumerhelloribbon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author zwj
 * @version 1.0.0
 * @ClassName HelloController.java
 * @Description TODO
 * @createTime 2020年01月17日 09:58:00
 */
@RestController
public class HelloController {

    @Value("${provider.service.name}")
    private String providerServiceName;

    private final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private RestTemplate ribbonRestTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * @title 
     * @description 通过手动设置 producer-hello.ribbon.listOfServers 
     * 不依赖Eureka注册中 手动实现客户端负载均衡
     * @author zwj 
     * @updateTime 2020-01-17 10:34 
     * @throws 
     */
    @GetMapping("/hello")
    public String hello() throws IOException {
        ServiceInstance serviceInstance = loadBalancerClient.choose(providerServiceName);
        logger.info("loadBalancerClient:{}",loadBalancerClient);
        logger.info("providerServiceName:{}",providerServiceName);
        logger.info("instance:{}",serviceInstance);
        return loadBalancerClient.execute(providerServiceName,serviceInstance, instance -> {
            String host = serviceInstance.getHost();
            int port = serviceInstance.getPort();
            String url = "http://" + host + ":" + port + "/hello";
            return restTemplate.getForObject(url, String.class);
        });
    }


    /**
     * @title 
     * @description 通过@LoadBalance 的RestTemplate实现客户端负载均衡
     * @author zwj
     * @updateTime 2020-01-17 10:39
     * @throws 
     */
    @GetMapping("/hello2")
    public String hello2(){
        return ribbonRestTemplate.getForObject("http://" + providerServiceName +"/hello",String.class);

    }


}
