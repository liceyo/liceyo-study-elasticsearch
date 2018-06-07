package com.liceyo.elasticsearch.analysis;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.util.List;

/**
 * 批量数据操作
 * 不考虑多线程
 * @author liceyo
 * @version 2018/6/7
 */
public class BulkDataHandler {
    private static Logger logger= LogManager.getLogger(DataHandler.class);
    private static TransportClient client= ElasticsearchClient.INSTANCE.getInstance();
    private final static int DEFAULT_SUBMIT_UPPER =1000;
    /**
     * 提交上限
     */
    private Integer submitUpper;
    /**
     * 批量提交builder
     */
    private BulkRequestBuilder bulkRequestBuilder;

    /**
     * 当前的请求数量
     */
    private int currentCount;

    public BulkDataHandler() {
        this.submitUpper=DEFAULT_SUBMIT_UPPER;
        initBulkRequest();
    }

    public BulkDataHandler(Integer submitUpper) {
        this.submitUpper = submitUpper;
        initBulkRequest();
    }

    /**
     * 添加插入请求
     * @param builder 请求
     */
    public void add(IndexRequestBuilder builder){
        bulkRequestBuilder.add(builder);
        increaseAndCheck();
    }

    /**
     * 添加删除请求
     * @param builder 请求
     */
    public void add(DeleteRequestBuilder builder){
        bulkRequestBuilder.add(builder);
        increaseAndCheck();
    }

    /**
     * 添加更新请求
     * @param builder 请求
     */
    public void add(UpdateRequestBuilder builder){
        bulkRequestBuilder.add(builder);
        increaseAndCheck();
    }

    /**
     * 增加和检查是否到达提交上线
     * 是：提交并初始化
     */
    private void increaseAndCheck(){
        this.currentCount++;
        if (currentCount==submitUpper){
            submit();
        }
    }

    /**
     * 提交请求
     */
    public void submit(){
        this.bulkRequestBuilder.execute().actionGet();
        initBulkRequest();
    }

    /**
     * 初始化批量builder
     */
    private void initBulkRequest(){
        this.bulkRequestBuilder=client.prepareBulk();
        this.currentCount=0;
    }

}
