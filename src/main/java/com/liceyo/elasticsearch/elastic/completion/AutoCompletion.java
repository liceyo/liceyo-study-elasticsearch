package com.liceyo.elasticsearch.elastic.completion;

import com.liceyo.elasticsearch.elastic.ConstantValue;
import com.liceyo.elasticsearch.elastic.ElasticsearchClient;
import com.liceyo.elasticsearch.elastic.token.Tokenizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 获取自动补全处理类
 * @author liceyo
 * @version 2018/6/6
 */
public class AutoCompletion {
    private static Logger logger= LogManager.getLogger(AutoCompletion.class);

    private static TransportClient client= ElasticsearchClient.INSTANCE.getInstance();

    /**
     * 自动提示字段
     */
    private final static String AUTO_COMPLETION_FIELD="auto_completion";
    private final static String AUTO_COMPLETION_FIELD_IK="auto_completion.IK";
    private final static String AUTO_COMPLETION_FIELD_SPY="auto_completion.SPY";
    private final static String AUTO_COMPLETION_FIELD_FPY="auto_completion.FPY";

    /**
     * 分词器
     */
    private final static String LICEYO_NGRAM_SEARCH ="liceyo_ngram_search";
    private final static  String LICEYO_IK_SEARCH ="liceyo_ik_search";
    private final static  String LICEYO_PINYIN_SIMPLE_SEARCH ="liceyo_pinyin_simple_search";
    private final static String LICEYO_PINYIN_FULL_SEARCH ="liceyo_pinyin_full_search";

    private static Pattern pattern=Pattern.compile("^([\\u4e00-\\u9fa5]+).*$");

    /**
     * 获取简单自动补全结果
     * @param query 当前查询
     * @return 结果
     */
    public static List<String> autoCompletion(String query){
        //组装查询
        CompletionSuggestionBuilder builder = SuggestBuilders.completionSuggestion(AUTO_COMPLETION_FIELD)
                .text(query)
                .skipDuplicates(true)
                .size(5);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("autoCompletion",builder);
        //搜索
        SearchResponse response = client.prepareSearch(ConstantValue.AUTO_COMPLETION_INDEX_NAME)
                .suggest(suggestBuilder)
                .setSize(0)
                .execute()
                .actionGet();
        //解析结果
        Suggest.Suggestion<Suggest.Suggestion.Entry<Suggest.Suggestion.Entry.Option>> suggestion = response.getSuggest().getSuggestion("autoCompletion");
        if (suggestion!=null){
            List<Suggest.Suggestion.Entry<Suggest.Suggestion.Entry.Option>> entries = suggestion.getEntries();
            if (entries!=null){
                List<Suggest.Suggestion.Entry.Option> options=new ArrayList<>();
                entries.forEach(e->options.addAll(e.getOptions()));
                return options.stream()
                        .map(o->o.getText().toString())
                        .filter(Objects::nonNull)
                        .distinct()
                        .limit(5)
                        .collect(Collectors.toList());
            }
        }
        return null;
    }


    /**
     * 高级自动补全
     * 参考文献：https://www.cnblogs.com/clonen/p/6674888.html
     * @param q 查询
     * @return 结果
     */
    public static List<String> highAutoCompletion(String q){
        boolean fullChinese = q.matches("^[\\u4e00-\\u9fa5]*$");
        if (fullChinese){
            return highFullChineseAutoCompletion(q);
        }else {
            return highMixtureAutoCompletion(q);
        }
    }

    /**
     * 纯中文搜索
     * @param q 搜索词
     * @return 自动补全
     */
    private static List<String> highFullChineseAutoCompletion(String q){
        //前缀
        MatchQueryBuilder ngramQuery = QueryBuilders.matchQuery(AUTO_COMPLETION_FIELD, q).analyzer(LICEYO_NGRAM_SEARCH).boost(5);
        //ik全匹配
        MatchQueryBuilder ikQuery = QueryBuilders.matchQuery(AUTO_COMPLETION_FIELD_IK, q).analyzer(LICEYO_IK_SEARCH).minimumShouldMatch("100%");
        //组装查询
        DisMaxQueryBuilder disMaxQuery = QueryBuilders.disMaxQuery();
        disMaxQuery.add(ngramQuery);
        disMaxQuery.add(ikQuery);
        return searchAndAnalysis(disMaxQuery);
    }

    /**
     * 混合搜索
     * @param q 搜索词
     * @return 自动补全
     */
    private static List<String> highMixtureAutoCompletion(String q){
        DisMaxQueryBuilder disMaxQuery = QueryBuilders.disMaxQuery();
        //1.源值搜索
        MatchQueryBuilder ngramQuery = QueryBuilders.matchQuery(AUTO_COMPLETION_FIELD, q).analyzer(LICEYO_NGRAM_SEARCH).boost(5f);
        disMaxQuery.add(ngramQuery);
        //2.拼音简写搜索
        //提取完整的简写词干
        AnalyzeRequestBuilder requestBuilder = new AnalyzeRequestBuilder(client, AnalyzeAction.INSTANCE, ConstantValue.AUTO_COMPLETION_INDEX_NAME, q);
        requestBuilder.setAnalyzer(LICEYO_PINYIN_SIMPLE_SEARCH);
        AnalyzeResponse analyzeTokens = requestBuilder.execute().actionGet();
        List<String> spy = Tokenizer.analysis(analyzeTokens);
        String fullStem = spy.stream().max(Comparator.comparingInt(String::length)).get();
        TermQueryBuilder fullStemQuery = QueryBuilders.termQuery(AUTO_COMPLETION_FIELD_SPY, fullStem);
        disMaxQuery.add(fullStemQuery);
        //3.拼音简写模糊搜索
        if (fullStem.length()>1){
            WildcardQueryBuilder wildcardStemQuery = QueryBuilders.wildcardQuery(AUTO_COMPLETION_FIELD_SPY, "*" + fullStem + "*").boost(0.8f);
            disMaxQuery.add(wildcardStemQuery);
        }
        //4.拼音全拼搜索
        if(fullStem.length()>1){
            MatchPhraseQueryBuilder fpyQuery = QueryBuilders.matchPhraseQuery(AUTO_COMPLETION_FIELD_FPY, q).analyzer(LICEYO_PINYIN_FULL_SEARCH);
            disMaxQuery.add(fpyQuery);
        }
        //5.ik分词全匹配
        QueryBuilder ikQuery=QueryBuilders.matchQuery(AUTO_COMPLETION_FIELD_IK, q).analyzer(LICEYO_IK_SEARCH).minimumShouldMatch("100%");
        disMaxQuery.add(ikQuery);
        //是否是以中文为前缀,是，则提取出来
        String chinesePrefix=null;
        Matcher matcher = pattern.matcher(q);
        if (matcher.find()){
            chinesePrefix=matcher.group(1);
        }
        //6.如果以中文开头，必须包含该中文
        if (chinesePrefix!=null){
            MatchQueryBuilder mustChineseQuery = QueryBuilders.matchQuery(AUTO_COMPLETION_FIELD_IK, q).analyzer(LICEYO_IK_SEARCH).minimumShouldMatch("100%");
            disMaxQuery.add(mustChineseQuery);
        }
        return searchAndAnalysis(disMaxQuery);
    }

    /**
     * 搜索并解析
     * @param query 查询条件
     * @return 解析结果
     */
    private static List<String> searchAndAnalysis(QueryBuilder query){
        //搜索
        SearchResponse response = client.prepareSearch(ConstantValue.AUTO_COMPLETION_INDEX_NAME)
                .setQuery(query)
                .setSize(10)
                .execute()
                .actionGet();
        //解析
        List<String> result=new LinkedList<>();
        response.getHits().forEach(hit ->{
            Object field = hit.getSourceAsMap().get(AUTO_COMPLETION_FIELD);
            if (field!=null){
                result.add(String.valueOf(field));
            }
        });
        return result;
    }

}
