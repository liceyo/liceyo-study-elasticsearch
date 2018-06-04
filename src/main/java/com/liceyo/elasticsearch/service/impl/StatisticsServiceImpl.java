package com.liceyo.elasticsearch.service.impl;

import com.liceyo.elasticsearch.analysis.aggs.AbstractAggregation;
import com.liceyo.elasticsearch.analysis.aggs.AggregationFunctions;
import com.liceyo.elasticsearch.analysis.aggs.result.BaseAggResult;
import com.liceyo.elasticsearch.analysis.search.QueryAssembler;
import com.liceyo.elasticsearch.analysis.search.Searcher;
import com.liceyo.elasticsearch.service.StatisticsService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

/**
 * @author liceyo
 * @version 2018/6/1
 */
@Service
public class StatisticsServiceImpl implements StatisticsService{

    @Override
    public BaseAggResult hitAgg() {
        AbstractAggregation hitAgg = AggregationFunctions.hitAgg();
        SearchResponse response = Searcher.searchByAggs(hitAgg.builder());
        return hitAgg.analysis(response.getAggregations());
    }

    @Override
    public BaseAggResult hitAgg(Integer type) {
        QueryBuilder filter = QueryAssembler.typeFilter(type);
        AbstractAggregation hitAgg = AggregationFunctions.hitAgg();
        SearchResponse response = Searcher.searchByAggs(filter,hitAgg.builder());
        return hitAgg.analysis(response.getAggregations());
    }
}
