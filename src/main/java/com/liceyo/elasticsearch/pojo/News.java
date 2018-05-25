package com.liceyo.elasticsearch.pojo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 新闻
 * @author lichy
 * @version 2018/5/2
 */
public class News extends Subject {

    /**
     * 文章摘要
     */
    @JSONField(name = "news_summary")
    private String newsSummary;

    /**
     * 文章内容
     */
    @JSONField(name = "news_content")
    private String newsContent;

    /**
     * 发布时间
     */
    @JSONField(name = "news_pub_time")
    private Long newsPubTime;

    /**
     * 来源链接
     */
    @JSONField(name = "news_source_url")
    private String newsSourceUrl;

    /**
     * 来源作者
     */
    @JSONField(name = "news_source_author")
    private String newsSourceAuthor;

    /**
     * 来源名称
     */
    @JSONField(name = "news_source_name")
    private String newsSourceName;

    public String getNewsSummary() {
        return newsSummary;
    }

    public void setNewsSummary(String newsSummary) {
        this.newsSummary = newsSummary;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public Long getNewsPubTime() {
        return newsPubTime;
    }

    public void setNewsPubTime(Long newsPubTime) {
        this.newsPubTime = newsPubTime;
    }

    public String getNewsSourceUrl() {
        return newsSourceUrl;
    }

    public void setNewsSourceUrl(String newsSourceUrl) {
        this.newsSourceUrl = newsSourceUrl;
    }

    public String getNewsSourceAuthor() {
        return newsSourceAuthor;
    }

    public void setNewsSourceAuthor(String newsSourceAuthor) {
        this.newsSourceAuthor = newsSourceAuthor;
    }

    public String getNewsSourceName() {
        return newsSourceName;
    }

    public void setNewsSourceName(String newsSourceName) {
        this.newsSourceName = newsSourceName;
    }
}
