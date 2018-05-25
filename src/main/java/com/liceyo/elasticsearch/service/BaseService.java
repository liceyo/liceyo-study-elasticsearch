package com.liceyo.elasticsearch.service;

import com.liceyo.elasticsearch.pojo.AnalysisResult;
import org.elasticsearch.search.SearchHit;

import java.util.List;

/**
 * @author liceyo
 * @version 2018/5/25
 */
public interface BaseService<T> {
    /**
     * 分页搜索
     * @param statement 搜索原句
     * @param page 页数
     * @param limit 一页限制
     * @return 搜索结果
     */
    AnalysisResult<T> search(String statement, int page, int limit);

    /**
     * 解析单个搜索结果
     * @param hit 命中结果
     * @return 解析结果
     */
    T analysis(SearchHit hit);

    /**
     * 通过id查询
     * @param id es id
     * @return 查询结果
     */
    T getById(String id);
}
