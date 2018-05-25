package com.liceyo.elasticsearch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.liceyo.elasticsearch.analysis.ConstantField;
import com.liceyo.elasticsearch.analysis.EntityAnalysis;
import com.liceyo.elasticsearch.analysis.SearchType;
import com.liceyo.elasticsearch.analysis.score.FunctionScoreBuilder;
import com.liceyo.elasticsearch.analysis.search.Searcher;
import com.liceyo.elasticsearch.pojo.AnalysisResult;
import com.liceyo.elasticsearch.pojo.News;
import com.liceyo.elasticsearch.pojo.Subject;
import com.liceyo.elasticsearch.service.MainListService;
import com.liceyo.elasticsearch.service.NewsService;
import com.liceyo.elasticsearch.service.ResearchService;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liceyo
 * @version 2018/5/25
 */
@Service
public class MainListServiceImpl implements MainListService {

    @Autowired
    private NewsService newsService;

    @Autowired
    private ResearchService researchService;

    @Override
    public AnalysisResult<Subject> search(String statement, int page, int limit) {
        //组装条件
        QueryBuilder query = FunctionScoreBuilder.builder(statement).resultQuery();
        //搜索
        SearchResponse response = Searcher.searchList(query, (page - 1) * limit, limit, SearchType.values());
        //解析结果
        SearchHits hits = response.getHits();
        long totalHits = hits.getTotalHits();
        long millis = response.getTook().getMillis();
        List<Subject> data=null;
        if (totalHits >0){
            data= Arrays.stream(hits.getHits())
                    .map(this::analysis)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        return new AnalysisResult<>(millis,totalHits,data);
    }

    @Override
    public Subject analysis(SearchHit hit) {
        Map<String, Object> map = hit.getSourceAsMap();
        Integer type = (Integer) map.get(ConstantField.DATA_TYPE_FIELD);
        if (type==null){
            return null;
        }
        //按类型解析
        if (type.equals(SearchType.NEWS.typeValue())){
            return newsService.analysis(hit);
        }else if(type.equals(SearchType.NEWS.typeValue())){
            return researchService.analysis(hit);
        }else {
            return null;
        }
    }

    @Override
    public Subject getById(String id) {
        GetResponse response = Searcher.get(id);
       return EntityAnalysis.analysis(response,Subject.class);
    }
}
