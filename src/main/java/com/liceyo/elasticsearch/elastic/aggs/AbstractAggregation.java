package com.liceyo.elasticsearch.elastic.aggs;

import com.liceyo.elasticsearch.elastic.aggs.result.BaseAggResult;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregations;

/**
 * @author liceyo
 * @version 2018/6/1
 */
public abstract class AbstractAggregation {

    /**
     * 聚合的名称
     */
    String name;

    /**
     * 聚合的字段
     */
    String field;

    public AbstractAggregation(String name) {
        this.name = name;
    }

    public AbstractAggregation(String name, String field) {
        this.name = name;
        this.field = field;
    }

    /**
     * 创建查询
     * @return 查询
     */
    public abstract AggregationBuilder builder();

    /**
     * 解析相应的查询
     * @return 解析结果
     */
   public abstract BaseAggResult analysis(Aggregations aggregations);

    public String name() {
        return name;
    }

    public String field(){
        return field;
    }
}
