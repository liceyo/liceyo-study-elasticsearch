package com.liceyo.elasticsearch.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @desc: Json工具类
 * @author: 鱼唇的人类
 * @date: 2017/12/6 10:59
 */
public class JsonUtil {
    /**
     * 创建有序的JSONObject
     * @return
     */
    public static JSONObject createOrderly(){
        return new JSONObject(16,true);
    }

    /**
     * 将对象转为字符串
     * null类型都会展示出来
     * @param object
     * @return
     */
    public static String getNullFeature(Object object){
        return JSONObject.toJSONString(object, SerializerFeature.WRITE_MAP_NULL_FEATURES);
    }
}
