package com.liceyo.elasticsearch.elastic.weight;

/**
 * @author lichy
 * @version 2018/4/19
 * @desc 权重计算函数
 */
public class WeightFunction {
    /**
     * 计算类型权重
     * @param length 搜索词长
     * @param baseWeight 基础权重
     * @return 计算结果
     */
    public static Float wordLengthWeighting(Integer length, Float baseWeight) {
        return (float) (baseWeight * 10 * Math.log10(length) * Math.sqrt(baseWeight * length) + 1);
    }
}
