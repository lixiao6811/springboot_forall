package com.example.springbootpro.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caishengtong on 2016/3/1.
 */
public class Singleton {
    //JspaiTicket 的Map  ,map中包含 一个JspaiTicket 和 缓存的时间戳
    //当然也可以分开成两个属性咯
    private Map<String, String> map = new HashMap<String, String>();

    private Singleton() {
    }

    private static Singleton single = null;

    // 静态工厂方法
    public static Singleton getInstance() {
        if (single == null) {
            single = new Singleton();
        }
        return single;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public static Singleton getSingle() {
        return single;
    }

    public static void setSingle(Singleton single) {
        Singleton.single = single;
    }

}
