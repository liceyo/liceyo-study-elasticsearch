package com.liceyo.elasticsearch.elastic.sentence;

/**
 * 解析结果
 * @author liceyo
 * @version 2018/6/21
 */
public enum Analyzer {
    /**
     * 通过ik分词
     */
    TOKEN_IK("liceyo_ik_search"),

    /**
     * 通过拼音分词首字母
     */
    TOKEN_SPY("liceyo_pinyin_simple_search"),

    /**
     * 通过拼音分词全拼
     */
    TOKEN_FPY("liceyo_pinyin_full_search");

    /**
     * 分词器
     */
    private String tokenizer;

    Analyzer(String tokenizer) {
        this.tokenizer = tokenizer;
    }

    public String getTokenizer() {
        return tokenizer;
    }
}
