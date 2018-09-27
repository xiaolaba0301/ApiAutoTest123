package com.itcast.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonToMap {
    public static HashMap<String,Object> jsonToMap(String json){
        HashMap<String,Object> map = new HashMap<String, Object>();
        if (json != null && json.length()>0){
            JSONObject jsonObject = JSONObject.parseObject(json);
            for (Map.Entry<String, Object> map1:jsonObject.entrySet()){
                map.put(map1.getKey().toString(),map1.getValue().toString());
            }
            return map;
        }
        return null;
    }
    public static void main(String[] args){
        String json = "{'name':'xiaowang','age':'20','sex':'man'}";
        Map<String,Object> map = jsonToMap(json);
        for (Map.Entry<String,Object> entry:map.entrySet()){
            System.out.println(entry.getKey());
            System.out.print(entry.getValue());
        }
    }
}
