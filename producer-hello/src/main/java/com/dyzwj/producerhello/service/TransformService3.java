package com.dyzwj.producerhello.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wnameless.json.base.JsonObjectBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TransformService3 {


    public static void main(String[] args) {


        JSONObject data = new JSONObject();
        JSONObject config = new JSONObject();

        Map<String,Object> result = new HashMap<>();
        Set<Map.Entry<String, Object>> entries = data.entrySet();

        for (Map.Entry<String, Object> entry : entries) {
            //原始数据
            String key = entry.getKey();
            Object value = entry.getValue();



        }


    }
}
