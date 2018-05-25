package com.liceyo.elasticsearch.service.impl;

import com.liceyo.elasticsearch.analysis.EntityAnalysis;
import com.liceyo.elasticsearch.analysis.SearchType;
import com.liceyo.elasticsearch.analysis.score.FunctionScoreBuilder;
import com.liceyo.elasticsearch.analysis.search.Searcher;
import com.liceyo.elasticsearch.pojo.AnalysisResult;
import com.liceyo.elasticsearch.pojo.News;
import com.liceyo.elasticsearch.service.NewsService;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
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
public class NewsServiceImpl implements NewsService {
    @Override
    public AnalysisResult<News> search(String statement, int page, int limit) {
        //组装条件
        QueryBuilder query = FunctionScoreBuilder.builder(statement,SearchType.NEWS).resultQuery();
        //搜索
        SearchResponse response = Searcher.searchOneList(query, (page - 1) * limit, limit, SearchType.NEWS);
        //解析
        SearchHits hits = response.getHits();
        long totalHits = hits.getTotalHits();
        long millis = response.getTook().getMillis();
        List<News> data=null;
        if (totalHits >0){
            data= Arrays.stream(hits.getHits())
                    .map(this::analysis)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        return new AnalysisResult<>(millis,totalHits,data);
    }

    @Override
    public News analysis(SearchHit hit) {
       return EntityAnalysis.analysis(hit,News.class);
    }

    @Override
    public News getById(String id) {
        GetResponse response = Searcher.get(id);
        return EntityAnalysis.analysis(response,News.class);
    }
}
