package com.liceyo.elasticsearch.elastic.search;

import com.liceyo.elasticsearch.elastic.ConstantField;
import com.liceyo.elasticsearch.elastic.ConstantValue;
import com.liceyo.elasticsearch.elastic.ElasticsearchClient;
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
            return typeFilter(types.get(0));
        }else {
            return QueryBuilders.termsQuery(ConstantValue.DATA_TYPE_FILED,types);
        }
    }

    /**
     * 类型过滤条件
     * @param type 类型
     * @return 过滤条件
     */
    public static QueryBuilder typeFilter(Integer type){
        return QueryBuilders.termQuery(ConstantValue.DATA_TYPE_FILED,type);
    }
}
