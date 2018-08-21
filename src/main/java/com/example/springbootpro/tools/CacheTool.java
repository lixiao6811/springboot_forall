package com.example.springbootpro.tools;

import java.util.Map;

/**
 * 缓存方法
 */
public class CacheTool {
    public static String getCathe() {
        Singleton singleton = Singleton.getInstance();
        Map<String, String> map = singleton.getMap();
        String time = map.get("wxcard_time");
        String workGuide = map.get("workGuide");
        String workey = map.get("workey");
        Long nowDate = System.currentTimeMillis();
        if (workey != null && workGuide != null && time != null && nowDate - Long.parseLong(time) < 2600 * 24) {
            return workGuide;
        }else {
            //todo获取对应参数的逻辑
        }
        return workGuide;
    }
}
