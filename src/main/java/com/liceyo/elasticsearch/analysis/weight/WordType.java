package com.liceyo.elasticsearch.analysis.weight;

/**
 * @author lichy
 * @version 2018/4/19
 * @desc 搜索词基本类型
 */
public enum WordType {
    /**
     * 原句
     */
    NOT_TOKENS_BOOST(10f),

    /**
     * 原句替换空格
     */
    REPLACE_SPACE_BOOST(8f),

    /**
     * 按空格拆分后
     */
    SPLIT_BY_SPACE_BOOST(6f),

    /**
     * 拆词后
     */
    TOKENS_BOOST(5f);
    /**
     * 基础长度权重
     */
    private Float baseLengthWeight;

    WordType(Float baseLengthWeight) {
        this.baseLengthWeight = baseLengthWeight;
    }

    public Float getBaseLengthWeight() {
        return baseLengthWeight;
    }
}
