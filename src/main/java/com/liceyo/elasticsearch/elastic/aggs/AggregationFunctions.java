package com.liceyo.elasticsearch.elastic.aggs;

import com.liceyo.elasticsearch.elastic.aggs.result.BaseAggResult;
import com.liceyo.elasticsearch.elastic.aggs.result.AggResult;
import com.liceyo.elasticsearch.elastic.aggs.result.Coord;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;

import java.util.List;
import java.util.stream.Collectors;

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
        return new AbstractAggregation("hitAgg","data_type") {
            @Override
            public AggregationBuilder builder() {
                TermsAggregationBuilder builder = AggregationBuilders.terms(name).field(field).size(10);
                SumAggregationBuilder hitCount = AggregationBuilders.sum("hit").field("hit_count");
                builder.subAggregation(hitCount);
                return builder;
            }
            @Override
            public BaseAggResult analysis(Aggregations aggregations) {
                AggResult<Double> result= new AggResult<>(name, field);
                Terms terms =aggregations.get(name);
                List<? extends Terms.Bucket> buckets = terms.getBuckets();
                List<Coord<Double>> data = buckets.stream()
                        .map(bucket -> {
                            String key = bucket.getKeyAsString();
                            Sum hit = bucket.getAggregations().get("hit");
                            Double count = hit.getValue();
                            return new Coord<>(key, count);
                        }).collect(Collectors.toList());
                result.setData(data);
                result.calculateCoordinate();
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
                AggResult<Long> result=new AggResult<>(name,field);
                result.setData(CommonAnalysis.histogramAnalysis(aggregations, name));
                result.calculateCoordinate();
                return result;
            }
        };
    }
}
