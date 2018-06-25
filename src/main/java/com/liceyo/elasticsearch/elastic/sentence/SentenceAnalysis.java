package com.liceyo.elasticsearch.elastic.sentence;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * 语句分析
 * 比如特殊字符处理，和一些分析
 * @author liceyo
 * @version 2018/6/21
 */
public interface SentenceAnalysis {
    /**
     * 语句分析
     * @param statement 待分析语句
     * @return 分析结果
     */
    QueryBuilder analysis(String statement);

    /**
     * 该语句分析是否需要分词解析
     * @return 是否需要分词解析
     */
    boolean needSegment();
}
