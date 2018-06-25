package com.liceyo.elasticsearch.elastic.aggs.result;

/**
 * @author liceyo
 * @version 2018/6/1
 */
public abstract class BaseAggResult {
    /**
     * 统计名称
     */
    private String name;
    /**
     * 聚合字段
     */
    private String field;

    public BaseAggResult() {
    }

    public BaseAggResult(String name, String field) {
        this.name = name;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
