package com.dyzwj.producerhello.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HelloService {




    public Map transform(JSONObject data, JSONObject config) {
        // data：原始数据     config：映射关系
        long start = System.currentTimeMillis();
        System.out.println(JSON.toJSONString(data));
        System.out.println(JSON.toJSONString(config));
        Map<String, Object> result = new HashMap<>();//返回结果
        Set<Map.Entry<String, Object>> entries = config.entrySet();
        for (Map.Entry<String, Object> entry : entries) { //遍历映射关系配置
            String key = entry.getKey();//user.name渠道方字段
            String value = (String) entry.getValue();//username标准字段
            if (value.contains(".")) {
                Map<String, Object> tmp = result;
                String[] split = value.split("\\.");
                for (int i = 0; i < split.length - 1; i++) {
                    if (tmp.containsKey(split[i])) {
                        tmp = (Map<String, Object>) tmp.get(split[i]);
                    } else {
                        tmp.put(split[i], new HashMap<String, Object>());
                        tmp = (Map<String, Object>) tmp.get(split[i]);
                    }
                }


                Object transform = transform(data, key);
                if(transform instanceof List){
                   List<Object> list = (List<Object>) transform;
                    for (Object o : list) {
                        tmp.putAll((Map<? extends String, ?>) o);
                    }
                }else {
                    tmp.put(split[split.length - 1],transform);
                }
//                tmp.put(split[split.length - 1], transform(data, key));
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
//            Set<Map.Entry<String, Object>> entries = jsonObj.entrySet();
//            for (Map.Entry<String, Object> entry : entries) {
//                Object transform = transform(jsonObj, entry.getKey());
//                result.add(transform);
//            }
            Map<String,Object> map = new HashMap<>();
            map.put(s,transform(jsonObj, s));
            result.add(map);
        }
        return result;
    }

}
