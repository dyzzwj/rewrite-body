package com.dyzwj.consumerhelloribbon.ping;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

/**
 * @author zwj
 * @version 1.0.0
 * @ClassName MyPing.java
 * @Description 自定义检测服务是否可用接口
 * @createTime 2020年01月17日 10:45:00
 */


public class MyPing implements IPing {


    private RestTemplate restTemplate = new RestTemplate();

    /**
     * 通过检测服务的/actuator/health断点 判断服务是否可用
     * @param server
     * @return
     */
    @Override
    public boolean isAlive(Server server) {
        String host = server.getHost();
        int port = server.getPort();

        String url = "http://" + host + ":" + port + "/actuator/health";
        return HttpStatus.OK.equals(restTemplate.getForEntity(url,String.class).getStatusCode());
    }
}
