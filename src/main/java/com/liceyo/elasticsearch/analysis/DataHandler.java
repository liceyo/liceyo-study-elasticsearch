package com.liceyo.elasticsearch.analysis;

import com.alibaba.fastjson.JSONObject;
import com.liceyo.elasticsearch.analysis.ConstantField;
import com.liceyo.elasticsearch.analysis.ElasticsearchClient;
import com.liceyo.elasticsearch.analysis.highlight.Highlight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * @author liceyo
 * @version 2018/6/1
 */
public class DataHandler {

    private static Logger logger= LogManager.getLogger(Highlight.class);

    private static TransportClient client= ElasticsearchClient.INSTANCE.getInstance();


    /**
     * TODO 待优化，不必每次点击都更新es
     * 简单的点击量自增
     * @param id id
     * @param currentHit 当前点击量
     */
    public static void hitIncrease(String id,Long currentHit){
        JSONObject data=new JSONObject();
        long hit=1;
        if (currentHit!=null){
            hit=++currentHit;
        }
        data.put("hit_count",hit);
        update(id,data);
    }

    /**
     * 更新一条数据
     * @param id 数据id
     * @param json 更新数据
     * @return 更新结果
     */
    public static UpdateResponse update(String id, JSONObject json) {
        return client.prepareUpdate(ConstantField.INDEX_NAME, ConstantField.INDEX_TYPE, id)
                .setDoc(json.toJSONString(), XContentType.JSON)
                .execute().actionGet();
    }

    /**
     * 插入一条数据
     * @param json 插入数据
     * @return 更新结果
     */
    public static IndexResponse insert(JSONObject json) {
        IndexRequestBuilder index = client.prepareIndex(ConstantField.INDEX_NAME, ConstantField.INDEX_TYPE);
        index.setSource(json.toJSONString(),XContentType.JSON);
        IndexResponse indexResponse = index.execute().actionGet();
        logger.debug("插入数据:"+indexResponse.getId());
        return indexResponse;
    }
}
