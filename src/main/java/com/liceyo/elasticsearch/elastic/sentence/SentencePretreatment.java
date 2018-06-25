package com.liceyo.elasticsearch.elastic.sentence;

/**
 * @author liceyo
 * @version 2018/6/22
 */
public interface SentencePretreatment {
    /**
     * 语句预处理
     * @param sentence 原句
     */
    void pretreatment(String sentence);

    /**
     * 处理结果
     * @return 结果
     */
    String result();

    /**
     * 语句是否合法
     * @return 是否合法
     */
    boolean isLegal();

    /**
     * 是否需要分词解析
     * @return 是否需要分词解析
     */
    boolean canSegment();
}
