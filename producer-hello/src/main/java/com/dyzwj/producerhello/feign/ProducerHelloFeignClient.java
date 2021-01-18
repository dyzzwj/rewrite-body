package com.dyzwj.producerhello.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "order-service")
public interface ProducerHelloFeignClient {

    @GetMapping("/order")
    String hello();

}
