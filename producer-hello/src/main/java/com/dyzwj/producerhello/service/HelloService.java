package com.dyzwj.producerhello.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HelloService {


    public static void main(String[] args) {
        String data = "{\n" +
                "  \"userId\": \"1111\",\n" +
                "  \"userInfo\": {\n" +
                "    \"name\": \"zwj\",\n" +
                "    \"age\": \"20\",\n" +
                "    \"address\": {\n" +
                "      \"city\": \"shenzhen\",\n" +
                "      \"provice\": \"guangdong\"\n" +
                "    },\n" +
                "    \"hobby\": [\n" +
                "      {\n" +
                "        \"hobbyName\": \"game\",\n" +
                "        \"desc\": \"游戏\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"hobbyName\": \"read\",\n" +
                "        \"desc\": \"读书\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        String config = "{\n" +
                "  \"userId\": \"id\",\n" +
                "  \"userInfo.name\": \"username\",\n" +
                "  \"userInfo.age\": \"age\",\n" +
                "  \"userInfo.address.city\": \"cityName\",\n" +
                "  \"userInfo.address.provice\": \"provice\",\n" +
                "  \"userInfo.hobby.hobbyName\":\"interest.hobbyname\",\n" +
                "  \"userInfo.hobby.desc\":\"interest.detail\"\n" +
                "}";

        HelloService helloService = new HelloService();
        Map transform = helloService.transform(JSON.parseObject(data), JSON.parseObject(config));
        System.out.println( transform);

    }


    /**
     * 根据层级把数据设置到result中

     * @return
     */

    public void changeResult(Map<String,Object> result,String path,Object v){


    }



    public Map transform(JSONObject data, JSONObject config) {
        // data：原始数据     config：映射关系
        long start = System.currentTimeMillis();
        System.out.println("原始数据："  + JSON.toJSONString(data));
        System.out.println("配置：" + JSON.toJSONString(config));
        Map<String, Object> result = new HashMap<>();//返回结果
        Set<Map.Entry<String, Object>> entries = config.entrySet();
        for (Map.Entry<String, Object> entry : entries) { //遍历映射关系配置
            String key = entry.getKey();//user.name渠道方字段
            String value = (String) entry.getValue();//username标准字段
            if (value.contains(".")) {
                Map<String, Object> tmp = result;
                String[] split = value.split("\\.");
                for (int i = 0; i < split.length - 2; i++) {
                    if (tmp.containsKey(split[i])) {
                        tmp = (Map<String, Object>) tmp.get(split[i]);
                    } else {
                        tmp.put(split[i], new HashMap<String, Object>());
                        tmp = (Map<String, Object>) tmp.get(split[i]);
                    }
                }

                Object transform = transform(data, key);
                if(transform instanceof List){
                   List<Map<String,Object>> list = (List<Map<String, Object>>) transform;
                   List<Map<String,Object>> maps = new ArrayList<>();

                    for (Map<String, Object> stringObjectMap : list) {
                        Map<String,Object> map = new HashMap<>();
                        Set<String> keySet = stringObjectMap.keySet();
                        for (String s : keySet) {
                            if(key.lastIndexOf(s) > 0 ){
                                map.put(split[split.length - 1],stringObjectMap.get(s));
                            }else {

                            }
                        }
                        maps.add(map);
                    }
                    tmp.put(split[split.length - 2],maps);
                }else {
                    tmp.put(split[split.length - 2],transform);
                }
//                tmp.put(split[split.length - 2], transform(data, key));
//                System.out.println();
            } else {
                result.put(value, transform(data, key));
            }

        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
        return result;
    }

    public Object transform(JSONObject data, String s) {
        Object result;
        String str = s;
        try {
            if (s.contains(".")) {
                str = s.substring(0, s.indexOf("."));
                s = s.substring(s.indexOf(".") + 1);
            }
            data = data.getJSONObject(str);

            result = transform(data, s);
        } catch (Exception e1) {
            try {
                JSONArray jsonArray = data.getJSONArray(str);
                result = transformArr(jsonArray, s);
            } catch (Exception e2) {
                result = data.get(str);
            }

        }
        return result;
    }


    public List<Object> transformArr(JSONArray jsonArr, String s) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < jsonArr.size(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            Set<String> keySet = jsonObj.keySet();
            Map<String,Object> map = new HashMap<>();
            for (String s1 : keySet) {

                map.put(s1,transform(jsonObj, s1));
                System.out.println();

            }
            result.add(map);
        }
        return result;
    }

}
