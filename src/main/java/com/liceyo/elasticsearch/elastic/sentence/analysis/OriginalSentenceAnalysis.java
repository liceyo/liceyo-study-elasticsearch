package com.liceyo.elasticsearch.elastic.sentence.analysis;

import com.liceyo.elasticsearch.elastic.sentence.SentenceAnalysis;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * 原句搜索，不附加任何运算
 * @author liceyo
 * @version 2018/6/22
 */
public class OriginalSentenceAnalysis implements SentenceAnalysis {

    @Override
    public QueryBuilder analysis(String statement) {
        return QueryBuilders.queryStringQuery(statement).defaultOperator(Operator.AND);
    }

    @Override
    public boolean needSegment() {
        return false;
    }
}
