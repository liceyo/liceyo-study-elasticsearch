package com.liceyo.elasticsearch.elastic;

import com.alibaba.fastjson.JSONObject;
import com.liceyo.elasticsearch.elastic.highlight.Highlight;
import com.liceyo.elasticsearch.elastic.search.Searcher;
import com.liceyo.elasticsearch.pojo.News;
import com.liceyo.elasticsearch.pojo.Research;
import com.liceyo.elasticsearch.pojo.Subject;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.util.Map;

/**
 * 解析
 * @author liceyo
 * @version 2018/5/25
 */
public class EntityAnalysis {
    /**
     * 通用命中结果解析
     * @param hit 命中结果
     * @param clazz 解析实体class
     * @param <T> 解析实体类型
     * @return 解析结果
     */
    public static  <T extends Subject> T  analysis(SearchHit hit, Class<T> clazz){
        //解析实体
        String source = hit.getSourceAsString();
        T data = JSONObject.parseObject(source,clazz);
        data.setId(hit.getId());
        //解析高亮
        Map<String, HighlightField> highlightFields = hit.getHighlightFields();
        HighlightField title = highlightFields.get("data_title");
        title=title !=null ? title:highlightFields.get("data_title.fpy");
        if (title!=null){
            data.setTitle(Highlight.jointFragments(title));
        }
        HighlightField content = highlightFields.get("data_content");
        content= content!=null ? content:highlightFields.get("data_content.fpy");
        if (content!=null){
            data.setContent(Highlight.jointFragments(content));
        }
        return data;
    }

    /**
     * 通用GetResponse 结果解析
     * @param response get结果
     * @param clazz 解析实体class
     * @param <T> 解析实体类型
     * @return 解析结果
     */
    public static <T extends Subject> T analysis(GetResponse response,Class<T> clazz){
        if (response.isExists()){
            String source = response.getSourceAsString();
            T data = JSONObject.parseObject(source, clazz);
            data.setId(response.getId());
            //少传值
            data.setContent(null);
            return data;
        }
        return null;
    }
}
