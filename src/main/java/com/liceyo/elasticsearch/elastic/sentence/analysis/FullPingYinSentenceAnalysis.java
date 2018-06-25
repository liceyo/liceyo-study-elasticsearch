package com.liceyo.elasticsearch.elastic.sentence.analysis;

import com.liceyo.elasticsearch.elastic.ConstantValue;
import com.liceyo.elasticsearch.elastic.sentence.Analyzer;
import com.liceyo.elasticsearch.elastic.sentence.SentenceAnalysis;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;

/**
 * @author liceyo
 * @version 2018/6/22
 */
public class FullPingYinSentenceAnalysis implements SentenceAnalysis {
    @Override
    public QueryBuilder analysis(String statement) {
        QueryStringQueryBuilder builder = QueryBuilders.queryStringQuery(statement)
                .analyzer(Analyzer.TOKEN_FPY.getTokenizer())
                .defaultOperator(Operator.AND);
        //指定查询字段
        for (String filed : ConstantValue.DEFAULT_SEARCH_FILED) {
            builder.field(filed+".fpy");
        }
        return builder;
    }

    @Override
    public boolean needSegment() {
        return false;
    }
}
