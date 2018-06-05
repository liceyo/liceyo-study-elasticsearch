package com.liceyo.elasticsearch.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liceyo
 * @version 2018/6/5
 */
public class DateUtil {

    /**
     * 按给定格式把时间戳转字符串
     * @param timestamp 时间戳
     * @param formatStr 格式
     * @return 结果
     */
    public static String stringToDate(long timestamp,String formatStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        Date date = new Date(timestamp);
        return simpleDateFormat.format(date);
    }

    /**
     * 默认的时间戳格式化
     * @param timestamp 时间戳
     * @return 结果
     */
    public static String defaultStringToDate(long timestamp){
        return stringToDate(timestamp,"yyyy-MM-dd HH:mm:ss");
    }
}
