package com.liceyo.elasticsearch.analysis.completion;

import com.liceyo.elasticsearch.analysis.ConstantValue;
import com.liceyo.elasticsearch.analysis.ElasticsearchClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
     * 获取自动补全结果
     * @param query 当前查询
     * @return 结果
     */
    public static List<String> autoCompletion(String query){
        //组装查询
        CompletionSuggestionBuilder builder = SuggestBuilders.completionSuggestion("autoCompletion")
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
}
