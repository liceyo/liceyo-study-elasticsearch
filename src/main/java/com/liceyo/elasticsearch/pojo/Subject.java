package com.liceyo.elasticsearch.pojo;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * 抽象出来的查询主体
 * @author lichy
 * @version 2018/5/2
 */
public class Subject{
    /**
     * ES id
     */
    private String id;

    /**
     * 数据标识
     */
    @JSONField(name = "data_id")
    private String dataId;

    /**
     * 数据类型
     */
    @JSONField(name = "data_type")
    private Integer type;

    /**
     * 数据标题
     */
    @JSONField(name = "data_title")
    private String title;

    /**
     * 数据正文
     */
    @JSONField(name = "data_content")
    private String content;

    /**
     * 点击次数
     */
    @JSONField(name = "hit_count")
    private Long hitCount;

    public String textual(){
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getHitCount() {
        return hitCount;
    }

    public void setHitCount(Long hitCount) {
        this.hitCount = hitCount;
    }
}
