package com.liceyo.elasticsearch.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.liceyo.elasticsearch.utils.DateUtil;

/**
 * 科学研究
 * @author lichy
 * @version 2018/5/2
 */
public class Research extends Subject {
    /**
     * 期刊杂志
     */
    @JSONField(name = "scr_magazine")
    private String scrMagazine;
    /**
     * 来源
     */
    @JSONField(name = "scr_source")
    private String scrSource;
    /**
     * 背景
     */
    @JSONField(name = "scr_background")
    private String scrBackground;
    /**
     * 结果
     */
    @JSONField(name = "scr_result")
    private String scrResult;
    /**
     * 研究方法
     */
    @JSONField(name = "scr_approach")
    private String scrApproach;
    /**
     * 结论
     */
    @JSONField(name = "scr_conclusion")
    private String scrConclusion;
    /**
     * 作者
     */
    @JSONField(name = "scr_author")
    private String scrAuthor;
    /**
     * 发布时间
     */
    @JSONField(name = "scr_pub_time")
    private String scrPubTime;
    /**
     * 来源网址
     */
    @JSONField(name = "scr_website")
    private String scrWebsite;

    @Override
    public String textual() {
        return scrPubTime+ "," +
                scrAuthor + "," +
                scrMagazine + "," +
                scrSource + "," +
                scrBackground + "," +
                scrApproach + "," +
                scrConclusion + "," +
                scrResult;
    }

    public String getScrMagazine() {
        return scrMagazine;
    }

    public void setScrMagazine(String scrMagazine) {
        this.scrMagazine = scrMagazine;
    }

    public String getScrSource() {
        return scrSource;
    }

    public void setScrSource(String scrSource) {
        this.scrSource = scrSource;
    }

    public String getScrBackground() {
        return scrBackground;
    }

    public void setScrBackground(String scrBackground) {
        this.scrBackground = scrBackground;
    }

    public String getScrResult() {
        return scrResult;
    }

    public void setScrResult(String scrResult) {
        this.scrResult = scrResult;
    }

    public String getScrApproach() {
        return scrApproach;
    }

    public void setScrApproach(String scrApproach) {
        this.scrApproach = scrApproach;
    }

    public String getScrConclusion() {
        return scrConclusion;
    }

    public void setScrConclusion(String scrConclusion) {
        this.scrConclusion = scrConclusion;
    }

    public String getScrAuthor() {
        return scrAuthor;
    }

    public void setScrAuthor(String scrAuthor) {
        this.scrAuthor = scrAuthor;
    }

    public String getScrPubTime() {
        return scrPubTime;
    }

    public void setScrPubTime(String scrPubTime) {
        this.scrPubTime = scrPubTime;
    }

    public String getScrWebsite() {
        return scrWebsite;
    }

    public void setScrWebsite(String scrWebsite) {
        this.scrWebsite = scrWebsite;
    }
}
