package com.liceyo.elasticsearch.analysis.aggs.result;

/**
 * 坐标
 * @author liceyo
 * @version 2018/6/1
 */
public class Coord {
    /**
     * 坐标名称
     */
    private String key;
    /**
     * 坐标值
     */
    private Long value;

    public Coord() {
    }

    public Coord(String key, Long value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
