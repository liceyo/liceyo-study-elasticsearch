package com.liceyo.elasticsearch.analysis.search;

import com.alibaba.fastjson.JSONObject;
import com.liceyo.elasticsearch.analysis.ConstantField;
import com.liceyo.elasticsearch.analysis.ElasticsearchClient;
import com.liceyo.elasticsearch.analysis.SearchType;
import com.liceyo.elasticsearch.analysis.highlight.Highlight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liceyo
 * @version 2018/5/14
 */
public class Searcher {
    private static Logger logger= LogManager.getLogger(Highlight.class);

    private static TransportClient client= ElasticsearchClient.INSTANCE.getInstance();


    public static SearchResponse search(SearchSourceBuilder source){
        SearchResponse response = client.prepareSearch(ConstantField.INDEX_NAME)
                .setSource(source)
                .execute().actionGet();
        logger.debug("搜索用时:" + response.getTook());
        return response;
    }

    /**
     * 搜索多类型列表 如全部列表
     * @param query 查询条件
     * @param from 分页起始值
     * @param size 当前分页条数
     * @param types 搜索类型
     * @return 搜索结果
     */
    public static SearchResponse searchList(QueryBuilder query, int from, int size, SearchType... types){
        //获取类型
        List<Integer> typeValues= Arrays.stream(types).map(SearchType::typeValue).collect(Collectors.toList());
        //获取列表包含的字段
        List<String> includeList=new ArrayList<>();
        Arrays.stream(types).filter(searchType -> searchType.includes()!=null)
                .forEach(searchType -> includeList.addAll(Arrays.asList(searchType.includes())));
        String[] includes=new String[includeList.size()];
        includeList.toArray(includes);
        //查询
        SearchSourceBuilder source=new SearchSourceBuilder()
                .fetchSource(includes,null)
                .query(query)
                .postFilter(QueryAssembler.typeFilter(typeValues))
                .highlighter(Highlight.highlight())
                .from(from)
                .size(size);
        return search(source);
    }

    /**
     * 搜索单类型列表
     * @param query 查询条件
     * @param from 分页起始值
     * @param size 当前分页条数
     * @param type 搜索类型
     * @return 搜索结果
     */
    public static SearchResponse searchOneList(QueryBuilder query, int from, int size, SearchType type){
        //查询
        SearchSourceBuilder source=new SearchSourceBuilder()
                .fetchSource(type.includes(),null)
                .query(query)
                .postFilter(QueryAssembler.typeFilter(type.typeValue()))
                .highlighter(Highlight.highlight())
                .from(from)
                .size(size);
        return search(source);
    }

    /**
     * 根据id查询详情
     * @param id es id
     * @return 查询结果
     */
    public static GetResponse get(String id){
        return client.prepareGet(ConstantField.INDEX_NAME,ConstantField.INDEX_TYPE,id).execute().actionGet();
    }


    /**
     * 聚合(无查询条件 比如nested聚合)
     * @param aggregation 聚合器
     * @return 聚合结果
     */
    public static SearchResponse searchByAggs(AggregationBuilder aggregation){
        return client.prepareSearch(ConstantField.INDEX_NAME)
                .setSize(0)
                .addAggregation(aggregation)
                .execute().actionGet();
    }

    /**
     * 聚合
     * @param query 条件
     * @param aggregations 聚合器
     * @return 聚合结果
     */
    public static SearchResponse searchByAggs(QueryBuilder query,AggregationBuilder...aggregations){
        SearchRequestBuilder builder = client.prepareSearch(ConstantField.INDEX_NAME)
                .setSize(0)
                .setQuery(query);
        Arrays.stream(aggregations).forEach(builder::addAggregation);
        return builder.execute().actionGet();
    }

}
