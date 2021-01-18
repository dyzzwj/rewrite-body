package com.dyzwj.consumerhellohystrixribbon.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * @author zwj
 * @version 1.0.0
 * @ClassName HelloHystrixCommand.java
 * @Description TODO
 * @createTime 2020年01月17日 17:09:00
 */
public class HelloHystrixCommand extends HystrixCommand<String> {



    private RestTemplate restTemplate = new RestTemplate();

    private Random random = new Random();

    public HelloHystrixCommand(){
        super(HystrixCommandGroupKey.Factory.asKey("test"),100);
    }

    @Override
    protected String run() throws Exception {
        int i = random.nextInt(200);
        System.out.println(i);
        Thread.sleep(i);
       return restTemplate.getForObject("http://localhost:4444/hello",String.class);
    }

    @Override
    protected String getFallback() {

        return "HelloHystrixCommand...getFallback";
    }
}
