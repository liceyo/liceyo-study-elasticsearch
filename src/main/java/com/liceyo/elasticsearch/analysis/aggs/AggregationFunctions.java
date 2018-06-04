package com.liceyo.elasticsearch.analysis.aggs;

import com.liceyo.elasticsearch.analysis.aggs.result.BaseAggResult;
import com.liceyo.elasticsearch.analysis.aggs.result.AggResult;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;

/**
 * 聚合函数
 * @author liceyo
 * @version 2018/6/1
 */
public class AggregationFunctions {

    /**
     * 点击率结果聚合
     * @return 聚合对象
     */
    public static AbstractAggregation hitAgg(){
        return new AbstractAggregation("hitAgg","hit_count") {
            @Override
            public AggregationBuilder builder() {
                return AggregationBuilders.terms(name).field(field).size(10);
            }
            @Override
            public BaseAggResult analysis(Aggregations aggregations) {
                AggResult result=new AggResult(name,field);
                result.setData(CommonAnalysis.termAnalysis(aggregations, name));
                return result;
            }
        };
    }
}
