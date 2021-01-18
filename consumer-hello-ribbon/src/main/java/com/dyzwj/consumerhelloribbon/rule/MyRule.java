package com.dyzwj.consumerhelloribbon.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.Random;

/**
 * @author zwj
 * @version 1.0.0
 * @ClassName MyRule.java
 * @Description TODO
 * @createTime 2020年01月17日 10:59:00
 */
public class MyRule extends AbstractLoadBalancerRule {

    private Random random = new Random();

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        ILoadBalancer loadBalancer = getLoadBalancer();
        List<Server> reachableServers = loadBalancer.getReachableServers();
        if(reachableServers.isEmpty()){
            return null;
        }
        int i = random.nextInt(reachableServers.size());
        //永远选择最后一台可用的服务器
        return reachableServers.get(i);
    }
}

