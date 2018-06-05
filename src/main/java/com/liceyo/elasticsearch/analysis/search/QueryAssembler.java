package com.liceyo.elasticsearch.analysis.search;

import com.liceyo.elasticsearch.analysis.ConstantField;
import com.liceyo.elasticsearch.analysis.ElasticsearchClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.List;

/**
 * 组装查询
 * @author liceyo
 * @version 2018/5/25
 */
public class QueryAssembler {

    /**
     * 类型过滤条件
     * @param types 类型
     * @return 过滤条件
     */
    public static QueryBuilder typeFilter(List<Integer>types){
        if (types==null||types.size()==0){
            return null;
        }
        if (types.size()==1){
            return QueryBuilders.termQuery(ElasticsearchClient.INSTANCE.dataType(),types.get(0));
        }else {
            return QueryBuilders.termsQuery(ElasticsearchClient.INSTANCE.dataType(),types);
        }
    }

    /**
     * 类型过滤条件
     * @param type 类型
     * @return 过滤条件
     */
    public static QueryBuilder typeFilter(Integer type){
        return QueryBuilders.termQuery(ElasticsearchClient.INSTANCE.dataType(),type);
    }
}
