package com.dyzwj.producerhello.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dyzwj.producerhello.domain.User;
import com.dyzwj.producerhello.feign.ProducerHelloFeignClient;
import com.dyzwj.producerhello.service.HelloService;
import com.dyzwj.producerhello.service.TransformService;
import com.dyzwj.producerhello.service.TransformService3;
import com.netflix.discovery.converters.Auto;
import com.netflix.discovery.converters.JsonXStream;
import com.oracle.jrockit.jfr.Producer;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * @author zwj
 * @version 1.0.0
 * @ClassName HelloController.java
 * @Description TODO
 * @createTime 2020年01月13日 10:20:00
 */

@RestController
public class HelloController {


    @Value("${server.port}")
    private Integer port;

    @Autowired
    HelloService helloService;

//    @Autowired
    ProducerHelloFeignClient producerHelloFeignClient;

    @GetMapping("/hello")
    public String hello(User user){
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return producerHelloFeignClient.hello();
    }

    @PostMapping("/user11")
    public String user11(MultipartHttpServletRequest request){
        System.out.println(request.getParameter("name"));
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((k,v) -> {
            System.out.println(k+":"+v);
        });

        System.out.println("/user请求处理完毕");
        return "";
    }

    @Autowired
    TransformService3 transformService3;

    @PostMapping("/transform")
    public Map transform(@RequestBody Map param){

        Map<String,Object> result = new HashMap<>();

        Object dataObj = param.get("data");
        Object configObj = param.get("config");

        System.out.println(JSON.toJSONString(dataObj));
        System.out.println(JSON.toJSONString(configObj));


        String data = JSON.toJSONString(dataObj);
        JSONObject dataJsonObject = JSON.parseObject(data);
        String config = JSON.toJSONString(configObj);
        JSONObject configJsonObject = JSON.parseObject(config);
        Map<String, Object> map = new HashMap<>();
        transformService3.analysisJson2(JSON.parseObject(data), "", JSON.parseObject(config),map);
        return map;

        //遍历配置
//        Set<Map.Entry<String, Object>> entries = configJsonObject.entrySet();
//        entries.forEach(entry -> {
//            String key = entry.getKey();
//            String value = (String) entry.getValue();
//            if(key.contains(".")){
//                String[] keyArr = key.split("\\.");
//                JSONObject tmp = dataJsonObject;
//                //user.name
//                for (String s : keyArr) {
//                    try{
//                        tmp = tmp.getJSONObject(s);
//                    }catch (Exception e1){
//                        try{
//                            JSONArray jsonArray = tmp.getJSONArray(s);
//
//                        }catch (Exception e2){
//                            if(value.contains(".")){
//                                String[] valueArr = value.split(".");
//                            }else {
//                                result.put(value,tmp.get(s));
//                            }
//                        }
//
//                    }
//                }
//            }else {
//                result.put(value,dataJsonObject.get(key));
//            }
//
//
//        });
//        return helloService.transform(dataJsonObject,configJsonObject);



//        return result;
    }


    private void solveArray(JSONArray array){

    }



    @PostMapping("/user")
    public Map user(@RequestBody Map body, HttpServletRequest request) throws IOException {
        System.out.println(request);

        ServletInputStream inputStream = request.getInputStream();
//        inputStream.read();
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((k,v) -> {
            System.out.println(k+":"+v);
        });
        System.out.println(body);
        System.out.println("/user请求处理完毕");
        return body;
    }

    @PostMapping("/user1")
    public String user(HttpServletRequest request, Map map){
        request.getParameter("");
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((k,v) -> {
            System.out.println(k+":"+v);
        });

        System.out.println("/user请求处理完毕");
        return "";
    }


    @GetMapping("/world")
    public String world(User user){
        throw new Error();
//        return "world:" + port + "user:" + user;
    }

    @PostMapping("/post1")
    public String post1(@RequestBody User user){
        return user.toString();
    }


    @GetMapping("/path/{id}")
    public String path(@PathVariable String id){
        return id;
    }




}
