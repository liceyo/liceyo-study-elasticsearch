package com.liceyo.elasticsearch.analysis.token;

import com.liceyo.elasticsearch.analysis.ConstantField;
import com.liceyo.elasticsearch.analysis.ConstantValue;
import com.liceyo.elasticsearch.analysis.ElasticsearchClient;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liceyo
 * @version 2018/5/14
 */
public class Tokenizer {
    /**
     * 默认分词器
     */
    private final static String DEFAULT_TOKENIZER="ik_smart";

    private static TransportClient transportClient= ElasticsearchClient.INSTANCE.getInstance();

    /**
     * 分词
     * @param statement 待分词语句
     * @return 分词结果
     */
    public static AnalyzeResponse token(String statement) {
       return token(statement,DEFAULT_TOKENIZER);
    }

    /**
     * 分词
     * @param statement 待分词语句
     * @param tokenizer 分词器
     * @return 分词结果
     */
    public static AnalyzeResponse token(String statement, String tokenizer) {
        AnalyzeRequestBuilder requestBuilder = new AnalyzeRequestBuilder(transportClient, AnalyzeAction.INSTANCE, ConstantValue.SEARCH_INDEX_NAME, statement);
        requestBuilder.setTokenizer(tokenizer);
        return requestBuilder.execute().actionGet();
    }

    /**
     * 解析分词结果
     * @param response 分词结果
     * @return 解析结果
     */
    public static List<String> analysis(AnalyzeResponse response){
        return response.getTokens().stream().map(AnalyzeResponse.AnalyzeToken::getTerm).collect(Collectors.toList());
    }

    /**
     * 分词并解析结果
     * @param statement 待分词语句
     * @return 解析结果
     */
    public static List<String> tokenAndAnalysis(String statement){
        AnalyzeResponse response=token(statement);
        return response.getTokens().stream().map(AnalyzeResponse.AnalyzeToken::getTerm).collect(Collectors.toList());
    }

    /**
     * 分词并解析结果
     * @param statement 待分词语句
     * @param tokenizer 分词器
     * @return 解析结果
     */
    public static List<String> tokenAndAnalysis(String statement,String tokenizer){
        AnalyzeResponse response=token(statement,tokenizer);
        return response.getTokens().stream().map(AnalyzeResponse.AnalyzeToken::getTerm).collect(Collectors.toList());
    }
}
