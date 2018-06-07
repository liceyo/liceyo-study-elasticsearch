package com.liceyo.elasticsearch.analysis.aggs.result;

/**
 * 坐标
 * @author liceyo
 * @version 2018/6/1
 */
public class Coord<T extends Number> {
    /**
     * 坐标名称
     */
    private String key;
    /**
     * 坐标值
     */
    private T value;

    public Coord() {
    }

    public Coord(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
