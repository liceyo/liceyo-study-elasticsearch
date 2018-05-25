package com.liceyo.elasticsearch.pojo;

import java.util.List;

/**
 * 解析结果
 * @author liceyo
 * @version 2018/5/25
 */
public class AnalysisResult<T> {
    private long took;
    private long total;
    private List<T> data;

    public AnalysisResult(long took, long total, List<T> data) {
        this.took = took;
        this.total = total;
        this.data = data;
    }

    public long getTook() {
        return took;
    }

    public void setTook(long took) {
        this.took = took;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
