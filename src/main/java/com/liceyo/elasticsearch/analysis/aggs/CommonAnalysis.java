package com.liceyo.elasticsearch.analysis.aggs;

import com.liceyo.elasticsearch.analysis.aggs.result.Coord;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用聚合解析
 * @author liceyo
 * @version 2018/6/1
 */
public class CommonAnalysis {
    /**
     * 解析term聚合
     * @param aggregations 聚合结果
     * @param name 聚合name
     * @return 解析结果
     */
    public static List<Coord> termAnalysis(Aggregations aggregations, String name){
        Terms terms =aggregations.get(name);
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        return buckets.stream()
                .map(bucket -> new Coord(bucket.getKeyAsString(), bucket.getDocCount()))
                .collect(Collectors.toList());
    }

    /**
     * 解析histogram聚合结果
     * @param aggregations 聚合结果
     * @param name 聚合name
     * @return 解析结果
     */
    public static List<Coord> histogramAnalysis(Aggregations aggregations, String name){
        Histogram histogram = aggregations.get(name);
        List<? extends Histogram.Bucket> buckets = histogram.getBuckets();
        return buckets.stream()
                .map(bucket->new Coord(bucket.getKeyAsString(),bucket.getDocCount()))
                .collect(Collectors.toList());
    }
}
