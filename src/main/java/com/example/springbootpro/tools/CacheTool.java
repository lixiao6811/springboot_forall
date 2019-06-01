package com.example.springbootpro.tools;

import java.util.Map;

/**
 * 缓存方法
 */
public class CacheTool {

    public static String getCathe() {
        Singleton singleton = Singleton.getInstance();
        Map<String, String> map = singleton.getMap();
        String time = map.get("ValidTime");
        String token = map.get("Token");
        Long nowDate = System.currentTimeMillis();
        if (time != null && token != null && nowDate - Long.parseLong(time) < 3600 * 24) {
            return token;
        } else {
            //todo获取对应参数的逻辑
        }
        return token;
    }
}
