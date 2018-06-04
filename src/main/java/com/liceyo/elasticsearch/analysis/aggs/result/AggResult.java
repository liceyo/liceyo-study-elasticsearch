package com.liceyo.elasticsearch.analysis.aggs.result;

import java.util.List;

/**
 * 一般解析结果
 * @author liceyo
 * @version 2018/6/1
 */
public class AggResult extends BaseAggResult {
    /**
     * 解析结果
     */
    private List<Coord> data;

    public AggResult() {
    }

    public AggResult(String name, String field) {
        super(name,field);
    }

    public List<Coord> getData() {
        return data;
    }

    public void setData(List<Coord> data) {
        this.data = data;
    }
}
