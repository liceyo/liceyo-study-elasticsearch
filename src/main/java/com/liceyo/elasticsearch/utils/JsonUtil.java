package com.liceyo.elasticsearch.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Json工具类
 * @author 鱼唇的人类
 * @date 2017/12/6 10:59
 */
public class JsonUtil {
    /**
     * 创建有序的JSONObject
     * @return 有序json
     */
    public static JSONObject createOrderly(){
        return new JSONObject(16,true);
    }

    /**
     * 将对象转为字符串
     * null类型都会展示出来
     * @param object 对象
     * @return 为空json
     */
    public static String getNullFeature(Object object){
        return JSONObject.toJSONString(object, SerializerFeature.WRITE_MAP_NULL_FEATURES);
    }
}
