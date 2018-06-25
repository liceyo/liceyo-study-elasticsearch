package com.liceyo.elasticsearch.elastic;

import com.liceyo.elasticsearch.utils.PropertiesUtil;

import java.util.Properties;

/**
 * @author liceyo
 * @version 2018/5/15
 */
public enum  SearchType {
    /**
     * 资讯
     */
    NEWS(1,1f),

    /**
     * 研究
     */
    RESEARCH(2,1f);

    /**
     * 对应的类型值
     */
    private int typeValue;

    /**
     * 基础权重
     */
    private float baseWeight;

    /**
     * 默认的返回结果字段集合
     */
    private String[] includes;

    SearchType(int typeValue,float baseWeight) {
        this.typeValue=typeValue;
        this.baseWeight=baseWeight;
        Properties properties = PropertiesUtil.getProperties(ConstantField.ELASTICSEARCH_PROPERTIES_PATH);
        String name = this.name().toLowerCase();
        String property = properties.getProperty(name + ConstantField.LIST_INCLUDES_FIELD);
        this.includes=property==null? null : property.split(",");
    }

    public int typeValue(){
        return this.typeValue;
    }

    public float baseWeight() {
        return baseWeight;
    }

    public String[] includes() {
        return includes;
    }
}
