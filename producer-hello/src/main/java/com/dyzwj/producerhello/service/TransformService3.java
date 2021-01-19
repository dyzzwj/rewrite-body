package com.dyzwj.producerhello.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TransformService3 {


    public static void maina(String[] args) {
        long start = System.currentTimeMillis();
        String json = "{\n" +
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
                "  \"userInfo.address.provice\": \"aa.provice\",\n" +
                "  \"userInfo.hobby.hobbyName\":\"interest.aa.cc.hobbyname\",\n" +
                "  \"userInfo.hobby.desc\":\"interest.aa.cc.detail\"\n" +
                "}";
        Map<String, Object> map = new HashMap<>();
        analysisJson2(JSON.parseObject(json), "", JSON.parseObject(config),map);
        System.out.println(JSON.toJSONString(map));
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end-start));

    }


    public static Map<String, Object> analysisJson2(Object objJson, String flag, JSONObject config,Map<String,Object> result) {
        Map<String,Object> aa = new HashMap<>();
        if (objJson instanceof JSONArray) {//如果obj为json数组

            JSONArray objArray = (JSONArray) objJson;
            for (int i = 0; i < objArray.size(); i++) {
                Map<String, Object> map = analysisJson2(objArray.get(i), flag, config,result);

                Set<String> keySet = map.keySet();
                Map<String, Object> map1 = new HashMap<>();

                map1 = map;
                System.out.println("=========" + map1);

                Set<String> keySet1 = map1.keySet();
                Map<String,Object> map2 = new HashMap<>();
                String path = "";


                for (String o : keySet1) {
                    result.remove(o);
                    if (!o.contains(".")) {
                        throw new RuntimeException("key不正确");
                    } else {
                        if(StringUtils.isBlank(path)){
                            path = o;
                        }else {

                        }
                        String[] split = o.split("\\.");
                        map2.put(split[split.length - 1],map1.get(o));
                    }
                }

                //aa.bb.cc  cc是数组对应的字段
                if (path.contains(".")) {
                    Map<String, Object> tmp = result;
                    String[] split = path.split("\\.");
                    int j = 0;
                    for (; j < split.length - 1; j++) {
                        if (tmp.containsKey(split[j])) {
                            if(j == split.length -2){
                                List<Map<String,Object>> list = (List<Map<String, Object>>) tmp.get(split[j]);
                                list.add(map2);
                            }else {
                                tmp = (Map<String, Object>) tmp.get(split[j]);
                            }

                        } else {
                            if(j == split.length -2){
                                List<Map<String,Object>> list = new ArrayList<>();
                                list.add(map2);
                                tmp.put(split[j],list);
                            }else {
                                tmp.put(split[j], new HashMap<>());
                                tmp = (Map<String, Object>) tmp.get(split[j]);
                            }

                        }
                    }

                    System.out.println(map2);
//                    if (tmp.containsKey(split[split.length - 2])) {
//                        List<Map<String, Object>> list1 = (List<Map<String, Object>>) tmp.get(split[j+1]);
//                        list1.add(map2);
//                    } else {
//                        List<Map<String, Object>> list1 = new ArrayList<>();
//                        list1.add(map2);
//                        tmp.put(split[split.length - 2], list1);
//                    }

                }
//                result.putAll();
            }
        } else if (objJson instanceof JSONObject) {//如果为json对象
            JSONObject jsonObject = (JSONObject) objJson;

            //key
            Iterator it = jsonObject.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next().toString();
                Object object = jsonObject.get(key);
                //如果得到的是数组
                if (object instanceof JSONArray) {
                    JSONArray objArray = (JSONArray) object;
                    String path = "";
                    if (StringUtils.isNotBlank(flag)) {
                        path = flag + "." + key;
                    } else {
                        path = key;
                    }
                    result.putAll(analysisJson2(objArray, path, config,result));
//                    result = analysisJson2(objArray, path, config,result);
                } else if (object instanceof JSONObject) {//如果key中是一个j
                    // son对象
                    String path = "";
                    if (StringUtils.isNotBlank(flag)) {
                        path = flag + "." + key;
                    } else {
                        path = key;
                    }
                    result.putAll(analysisJson2((JSONObject) object, path, config,result));
//                    result = analysisJson2((JSONObject) object, path, config,result);
                } else {//如果key中是其他
                    String path = "";
                    if (StringUtils.isNotBlank(flag)) {
                        path = flag + "." + key;
                    } else {
                        path = key;
                    }
                    result.put(config.getString(path), object.toString());
                    aa.put(config.getString(path), object.toString());
                    System.out.println(path + ":" + object.toString() + " ");
                }
            }
        } else {//如果key中是其他
            System.out.println("其他" + flag + ":" + objJson.toString() + " ");
        }

        return aa;
    }


//    static Map<String, Object> analysisJson2(Object objJson, String flag, JSONObject config) {
//        Map<String, Object> result = new HashMap<>();
//        if (objJson instanceof JSONArray) {//如果obj为json数组
//
//            JSONArray objArray = (JSONArray) objJson;
//            for (int i = 0; i < objArray.size(); i++) {
//                Map<String, Object> map = analysisJson2(objArray.get(i), flag, config);
//                Set<String> keySet = map.keySet();
//                Map<String, Object> map1 = new HashMap<>();
////
////                for (String s : keySet) {
////                    System.out.println("key:" + s);
////                    String o = config.getString(s);
////                    map1.put(o, map.get(s));
////
////
////                }
//
//                map1 = map;
//                System.out.println("=========" + map1);
//
//                Set<String> keySet1 = map1.keySet();
//                Map<String,Object> map2 = new HashMap<>();
//                for (String o : keySet1) {
//
//                    if (!o.contains(".")) {
//                        throw new RuntimeException("key不正确");
//                    } else {
//                        String[] split = o.split("\\.");
//                        map2.put(split[1],map1.get(o));
//                        if (result.containsKey(split[0])) {
//                            List<Map<String,Object>> list1 = (List<Map<String, Object>>) result.get(split[0]);
//                            list1.add(map2);
//                        } else {
//                            List<Map<String,Object>> list1 = new ArrayList<>();
//                            result.put(split[0],list1);
//                        }
//                    }
//                }
////                result.putAll();
//            }
//        } else if (objJson instanceof JSONObject) {//如果为json对象
//            JSONObject jsonObject = (JSONObject) objJson;
//
//            //key
//            Iterator it = jsonObject.keySet().iterator();
//            while (it.hasNext()) {
//                String key = it.next().toString();
//                Object object = jsonObject.get(key);
//                //如果得到的是数组
//                if (object instanceof JSONArray) {
//                    JSONArray objArray = (JSONArray) object;
//                    String path = "";
//                    if (StringUtils.isNotBlank(flag)) {
//                        path = flag + "." + key;
//                    } else {
//                        path = key;
//                    }
////                    result.putAll(analysisJson2(objArray, path, config));
//                    result = analysisJson2(objArray, path, config);
//                } else if (object instanceof JSONObject) {//如果key中是一个j
//                    // son对象
//                    String path = "";
//                    if (StringUtils.isNotBlank(flag)) {
//                        path = flag + "." + key;
//                    } else {
//                        path = key;
//                    }
////                    result.putAll(analysisJson2((JSONObject) object, path, config));
//                    result = analysisJson2((JSONObject) object, path, config);
//                } else {//如果key中是其他
//                    String path = "";
//                    if (StringUtils.isNotBlank(flag)) {
//                        path = flag + "." + key;
//                    } else {
//                        path = key;
//                    }
//                    result.put(config.getString(path), object.toString());
//                    System.out.println(path + ":" + object.toString() + " ");
//                }
//            }
//        } else {//如果key中是其他
//            System.out.println("其他" + flag + ":" + objJson.toString() + " ");
//        }
//
//        return result;
//    }
}
