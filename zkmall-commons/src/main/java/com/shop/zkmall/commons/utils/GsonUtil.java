/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.utils;

import com.google.gson.*;
import org.springframework.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
public class GsonUtil {

    // 不用创建对象,直接使用Gson.就可以调用方法
    private static final Gson GSON;

    // 判断gson对象是否存在了,不存在则创建对象
    static {
        // gson = new Gson();
        // 当使用GsonBuilder方式时属性为空的时候输出来的json字符串是有键值key的,显示形式是"key":null，而直接new出来的就没有"key":null的
        GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    // 无参的私有构造方法
    private GsonUtil() {
    }

    /**
     * 将对象转成json格式
     *
     * @return String
     */
    public static String objectToGson(Object object) {
        String gsonString = null;
        if (GSON != null) {
            gsonString = GSON.toJson(object);
        }
        return gsonString;
    }

    /**
     * 将json转成特定的cls的对象
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (GSON != null) {
            // 传入json对象和对象类型,将json转成对象
            t = GSON.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * json字符串转成list
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list = new ArrayList<>();
        if (GSON != null) {

            JsonParser parser = new JsonParser();
            JsonArray jsonarray = parser.parse(gsonString).getAsJsonArray();
            for (JsonElement element : jsonarray) {
                list.add(GSON.fromJson(element, cls));
            }

            // 根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
            /*
             * list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
             * }.getType());
             */
        }
        return list;
    }

    /**
     * list转为对象
     *
     * @return jsons
     */
    public static <T> String listToGson(List<T> ts) {
        return GSON.toJson(ts);
    }


    /**
     * 将对象装换为map
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(String.valueOf(key), beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

}
