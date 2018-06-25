package com.liceyo.elasticsearch.elastic.sentence.analysis;

import com.liceyo.elasticsearch.elastic.ConstantValue;
import com.liceyo.elasticsearch.elastic.sentence.Analyzer;
import com.liceyo.elasticsearch.elastic.sentence.SentenceAnalysis;
import com.liceyo.elasticsearch.elastic.token.Tokenizer;
import com.liceyo.elasticsearch.elastic.weight.WeightFunction;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用ik_smart对语句进行分词
 * @author liceyo
 * @version 2018/6/22
 */
public class IkSmartSentenceAnalysis implements SentenceAnalysis {
    private Operator operator;
    private String minimumShouldMatch;

    public IkSmartSentenceAnalysis(Operator operator) {
        this.operator = operator;
    }

    public IkSmartSentenceAnalysis(Operator operator, String minimumShouldMatch) {
        this.operator = operator;
        this.minimumShouldMatch = minimumShouldMatch;
    }

    @Override
    public QueryBuilder analysis(String statement) {
        //分词
        List<String> analysis = Tokenizer.analyzeAndAnalysis(statement, Analyzer.TOKEN_IK.getTokenizer());
        //组装查询语句
        String queryString = analysis.stream()
                .map(r -> "(" + r + ")^" + WeightFunction.wordLengthWeighting(r.length(), 3f))
                .collect(Collectors.joining());
        //构建查询
        QueryStringQueryBuilder builder = QueryBuilders.queryStringQuery(queryString)
                .defaultOperator(operator)
                .analyzer(Analyzer.TOKEN_IK.getTokenizer());
        //should查询需要判断是否使用minimumShouldMatch
        if (Operator.OR.equals(operator)&&minimumShouldMatch!=null){
            builder.minimumShouldMatch(minimumShouldMatch);
        }
        //指定查询字段
        for (String filed : ConstantValue.DEFAULT_SEARCH_FILED) {
            builder.field(filed);
        }
        return builder;
    }

    @Override
    public boolean needSegment() {
        return true;
    }
}
