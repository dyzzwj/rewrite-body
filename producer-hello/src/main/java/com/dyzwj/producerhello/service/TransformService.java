package com.dyzwj.producerhello.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.wnameless.json.base.JsonObjectBase;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.util.Set;

public class TransformService {

    public static JSONObject changeJsonObj(JSONObject jsonObj,JSONObject keyMap) {
        JSONObject resJson = new JSONObject();
        Set<String> keySet = jsonObj.keySet();
        for (String key : keySet) {
            String resKey = keyMap.get(key) == null ? key : keyMap.getString(key);
            try {
                JSONObject jsonobj1 = jsonObj.getJSONObject(key);
                JSONObject keyMapNext = keyMap.getJSONObject("_next") == null ? keyMap : keyMap.getJSONObject("_next");

                resJson.put(resKey, changeJsonObj(jsonobj1, keyMapNext));
            } catch (Exception e) {
                try {
                    JSONArray jsonArr = jsonObj.getJSONArray(key);
                    JSONObject keyMapNext = keyMap.getJSONObject("_next") == null ? keyMap : keyMap.getJSONObject("_next");
                    resJson.put(resKey, changeJsonArr(jsonArr, keyMapNext));
                } catch (Exception x) {
                    resJson.put(resKey, jsonObj.get(key));
                }
            }
        }
        return resJson;
    }

    public static JSONArray changeJsonArr(JSONArray jsonArr, JSONObject keyMap) {
        JSONArray resJson = new JSONArray();
        for (int i = 0; i < jsonArr.size(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            resJson.add(changeJsonObj(jsonObj, keyMap));
        }
        return resJson;
    }

    public static void main(String[] args) {
//        String jsonStr = "{\"user\":{\"name\":\"张三\",\"sex\":\"男\",\"hobby\":[{\"name\":\"足球\",\"desc\":\"任性\"},{\"game\":\"英雄联盟\",\"desc\":\"就是这么任性\"}]}}";
        long start = System.currentTimeMillis();
                //使用json作为映射关系的配置
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
                "        \"desc\":\"游戏\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"hobbyName\": \"read\",\n" +
                "        \"desc\":\"读书\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        String config= "{\n" +
                "  \"userId\": \"user\",\n" +
                "  \"_next\": {\n" +
                "    \"name\": \"username\",\n" +
                "    \"_next\": {\n" +
                "      \"city\": \"cityName\",\n" +
                "      \"province\": \"province\",\n" +
                "      \"hobbyName\": \"name\",\n" +
                "      \"desc\": \"detail\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
//        String jsonKeyMap = "{'_next':{'name':'xingming','_next':{'name':'hobbyname','game':'hobbyname'}}}";
//        JSONObject jsonKeyObject = JSONObject.parseObject(jsonKeyMap);

//        JSONObject jsonObj = changeJsonObj(JSONObject.parseObject(jsonStr),jsonKeyObject);
        JSONObject jsonObject = changeJsonObj(JSONObject.parseObject(data),JSONObject.parseObject(config));
        System.out.println("换值结果 》》 " + jsonObject.toString());
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end -start) );
    }

}
