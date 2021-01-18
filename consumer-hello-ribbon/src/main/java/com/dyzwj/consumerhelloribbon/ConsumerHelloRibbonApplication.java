package com.dyzwj.consumerhelloribbon;

import com.dyzwj.consumerhelloribbon.ping.MyPing;
import com.dyzwj.consumerhelloribbon.rule.MyRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class ConsumerHelloRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerHelloRibbonApplication.class, args);
    }



    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }

    @Bean
    public MyPing myPing(){
        return new MyPing();
    }
    @Bean
    public MyRule myRule(){
        return new MyRule();
    }
}
