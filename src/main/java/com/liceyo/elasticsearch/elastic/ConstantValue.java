package com.liceyo.elasticsearch.elastic;

import com.liceyo.elasticsearch.utils.PropertiesUtil;

import java.util.Properties;

/**
 * 配置文件的字段
 * @author liceyo
 * @version 2018/6/6
 */
public class ConstantValue {
    /**
     * 搜索索引
     */
    public static String SEARCH_INDEX_NAME;
    /**
     * 搜索类型
     */
    public static String SEARCH_INDEX_TYPE;
    /**
     * 自动提示索引
     */
    public static String AUTO_COMPLETION_INDEX_NAME;
    /**
     * 自动提示类型
     */
    public static String AUTO_COMPLETION_INDEX_TYPE;
    /**
     * 默认搜索字段
     */
    public static String[] DEFAULT_SEARCH_FILED;
    /**
     * 数据类型字段
     */
    public static String DATA_TYPE_FILED;

    static {
        Properties properties = PropertiesUtil.getProperties(ConstantField.ELASTICSEARCH_PROPERTIES_PATH);
        SEARCH_INDEX_NAME=properties.getProperty(ConstantField.SEARCH_INDEX_NAME_FIELD,"liceyo");
        SEARCH_INDEX_TYPE=properties.getProperty(ConstantField.SEARCH_INDEX_TYPE_FIELD,"liceyo");
        AUTO_COMPLETION_INDEX_NAME=properties.getProperty(ConstantField.AUTO_COMPLETION_INDEX_NAME_FIELD,"liceyo_auto_completion");
        AUTO_COMPLETION_INDEX_TYPE=properties.getProperty(ConstantField.AUTO_COMPLETION_INDEX_TYPE_FIELD,"auto_completion");
        String searchFiled = properties.getProperty(ConstantField.SEARCH_DEFAULT_FIELD);
        DEFAULT_SEARCH_FILED= searchFiled==null ? null : searchFiled.split(",");
        DATA_TYPE_FILED=properties.getProperty(ConstantField.SEARCH_TYPE_FIELD,"data_type");
    }
}
