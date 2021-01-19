package com.dyzwj.producerhello.service;

import com.github.wnameless.json.flattener.JsonFlattener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TransformService2 {




    public static void main(String[] args) {

//        String json = "{ \"a\" : { \"b\" : 1, \"c\": null, \"d\": [false, true] }, \"e\": \"f\", \"g\":2.3 }";
//        Map<String, Object> flattenJson = JsonFlattener.flattenAsMap(json);
//        System.out.println(flattenJson);
        Pattern pattern = Pattern.compile("\\[?\\d\\]?");
        Map<String,Object> result = new HashMap<>();
        String data = "{\"userInfo\":{\"address\":{\"city\":\"shenzhen\",\"provice\":\"guangdong\"},\"name\":\"zwj\",\"age\":\"20\",\"hobby\":[{\"hobbyName\":\"game\",\"desc\":\"游戏\"},{\"hobbyName\":\"read\",\"desc\":\"读书\"}]},\"userId\":\"1111\"}\n";
        Map<String, Object> flattenJson = JsonFlattener.flattenAsMap(data);
        System.out.println(flattenJson);
        Set<Map.Entry<String, Object>> entries = flattenJson.entrySet();
        Set<String> keySet = flattenJson.keySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(key.contains("[") && key.contains("]")){
                Matcher matcher = pattern.matcher(key);
                while (matcher.find()) {
                    //[0]
                    String group = matcher.group();

                    String _key = key.replace(group,"");
                    //查找包含[0]的key
                    List<String> collect = keySet.stream().filter(k -> k.contains(group)).collect(Collectors.toList());
                    for (String s : collect) {
                        if (s.contains(".")) {
                            Map<String, Object> tmp = result;
                            String[] split = s.split("\\.");
                            for (int i = 0; i < split.length - 1; i++) {
                                if (tmp.containsKey(split[i])) {
                                    tmp = (Map<String, Object>) tmp.get(split[i]);
                                } else {
                                    tmp.put(split[i], new HashMap<String, Object>());
                                    tmp = (Map<String, Object>) tmp.get(split[i]);
                                }
                            }

                        }
                    }
                    System.out.println(matcher.group());

                }
            }else {

            }
        }
    }

}
