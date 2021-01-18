package com.dyzwj.hystrixfuturetimeout.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author zwj
 * @version 1.0.0
 * @ClassName FutureTimeoutCircuitBreaker.java
 * @Description 通过Future实现超时熔断
 * @createTime 2020年01月19日 17:54:00
 */
public class FutureTimeoutCircuitBreaker {



    public static void main(String[] args) throws InterruptedException,ExecutionException{

        FutureCommand futureCommand = new FutureCommand();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(futureCommand::run);
        String result = "";
        try {
            //正常执行
            //100ms超时时间
           result = future.get(100, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            //超时熔断
            result= futureCommand.fallback();
        }
        System.out.println(result);
        executorService.shutdown();
    }

    private static Random random = new Random();

    static class FutureCommand implements  Command<String>{

        @Override
        public String run() throws InterruptedException {
            int execTime = random.nextInt(200);
            System.out.println("执行时间："  + execTime);
            Thread.sleep(execTime);
            return "hello world";
        }

        @Override
        public String fallback() {

            return "fallback";
        }
    }


    interface Command<T>{

        /**
         * @title
         * @description 正常执行的方法
         * @author zwj
         * @updateTime 2020-01-19 17:56
         * @throws
         */
        T run() throws Exception;

        /**
         * @title
         * @description 熔断执行的方法
         * @author zwj
         * @updateTime 2020-01-19 17:56
         * @throws
         */
        T fallback();
    }

}



