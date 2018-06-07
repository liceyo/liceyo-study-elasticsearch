package com.liceyo.elasticsearch.analysis.aggs.result;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 一般解析结果
 * @author liceyo
 * @version 2018/6/1
 */
public class AggResult<T extends Number> extends BaseAggResult {
    /**
     * 解析结果
     */
    private List<Coord<T>> data;

    /**
     * y坐标单位距离
     */
    private Double unitDistance;
    /**
     * 坐标下限
     */
    private Double coordLower;
    /**
     * 坐标上限
     */
    private Double coordUpper;


    public AggResult() {
    }

    public AggResult(String name, String field) {
        super(name,field);
    }

    /**
     * 计算坐标系
     * 只考虑0和正数
     */
    public void calculateCoordinate(){
        List<Number> values = data.stream().map(Coord::getValue).collect(Collectors.toList());
        Double max = values.stream().max(Comparator.comparingDouble(Number::doubleValue)).get().doubleValue();
        Double min = values.stream().min(Comparator.comparingDouble(Number::doubleValue)).get().doubleValue();
        Double dif=max-min;
        double v ;
        unitDistance= ( v=dif / values.size() )==0 ? 1d : v;
        if (dif==0d){
            coordLower=0d;
            coordUpper=max+unitDistance;
        }else {
            coordLower=min-unitDistance;
            coordLower=coordLower<0?0:coordLower;
            coordUpper=max+unitDistance;
        }
    }

    public List<Coord<T>> getData() {
        return data;
    }

    public void setData(List<Coord<T>> data) {
        this.data = data;
    }

    public Double getUnitDistance() {
        return unitDistance;
    }

    public void setUnitDistance(Double unitDistance) {
        this.unitDistance = unitDistance;
    }

    public Double getCoordLower() {
        return coordLower;
    }

    public void setCoordLower(Double coordLower) {
        this.coordLower = coordLower;
    }

    public Double getCoordUpper() {
        return coordUpper;
    }

    public void setCoordUpper(Double coordUpper) {
        this.coordUpper = coordUpper;
    }
}
