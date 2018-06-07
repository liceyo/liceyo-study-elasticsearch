package com.liceyo.elasticsearch.service;

import com.liceyo.elasticsearch.analysis.aggs.result.BaseAggResult;

/**
 * @author liceyo
 * @version 2018/6/1
 */
public interface StatisticsService {
    /**
     * 各模块点击量统计
     * @return 统计结果
     */
    BaseAggResult hitAgg();

    /**
     * 模块点击量统计
     * @param type 统计类型
     * @return 统计结果
     */
    BaseAggResult hitAgg(Integer type);

    /**
     * 新闻发布时间段统计
     * @return 统计结果
     */
    BaseAggResult newsPubTimeAgg();
}
