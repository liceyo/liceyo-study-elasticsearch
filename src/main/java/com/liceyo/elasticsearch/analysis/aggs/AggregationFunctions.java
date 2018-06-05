package com.liceyo.elasticsearch.analysis.aggs;

import com.liceyo.elasticsearch.analysis.aggs.result.BaseAggResult;
import com.liceyo.elasticsearch.analysis.aggs.result.AggResult;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;

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

    /**
     * 新闻发布时间段统计
     * @return 聚合对象
     */
    public static AbstractAggregation newsPubTimeAgg(){
        return new AbstractAggregation("newsPubTimeAgg","news_pub_time") {
            @Override
            public AggregationBuilder builder() {
                return AggregationBuilders.dateHistogram(name)
                        .field(field)
                        .dateHistogramInterval(DateHistogramInterval.DAY)
                        .format("yyyy-MM-dd");
            }

            @Override
            public BaseAggResult analysis(Aggregations aggregations) {
                AggResult result=new AggResult(name,field);
                result.setData(CommonAnalysis.histogramAnalysis(aggregations, name));
                return result;
            }
        };
    }
}
