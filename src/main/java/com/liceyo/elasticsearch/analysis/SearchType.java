package com.liceyo.elasticsearch.analysis;

/**
 * @author liceyo
 * @version 2018/5/15
 */
public enum  SearchType {
    /**
     * 资讯
     */
    NEWS("news",1f),

    /**
     * 研究
     */
    RESEARCH("research",1f);

    /**
     * 对应的类型值
     */
    private String typeValue;

    /**
     * 基础权重
     */
    private float baseWeight;

    SearchType(String typeValue,float baseWeight) {
        this.typeValue=typeValue;
        this.baseWeight=baseWeight;
    }

    public String typeValue(){
        return this.typeValue;
    }

    public float baseWeight() {
        return baseWeight;
    }
}
