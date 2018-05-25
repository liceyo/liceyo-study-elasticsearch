package com.liceyo.elasticsearch.analysis.weight;

import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.util.StringUtils;

/**
 * @author lichy
 * @version 2018/4/19
 * @desc 搜索词
 */
public class SearchWord {
    /**
     * 英文判断的正则
     */
    private final static String ENGLISH_REGEX = "^[a-zA-z\\s]+$|^.*[0-9]+.*$";
    /**
     * 默认分词器
     */
    private final static String DEFAULT_ANALYZER = "keyword";

    /**
     * 搜索词
     */
    private String word;
    /**
     * 搜索词权重
     */
    private Float weight = 1f;
    /**
     * 分词器
     */
    private String analyzer;

    /**
     * 字长基础权重
     */
    private WordType wordType;

    public static SearchWord build() {
        return new SearchWord();
    }

    public static SearchWord build(String word, WordType wordType) {
        if (word.matches(ENGLISH_REGEX)) {
            return new SearchWord(word, wordType);
        }
        if (WordType.NOT_TOKENS_BOOST.equals(wordType) || WordType.REPLACE_SPACE_BOOST.equals(wordType)) {
            return new SearchWord(word, wordType, "ik_smart");
        }
        return new SearchWord(word, wordType, DEFAULT_ANALYZER);
    }

    public static SearchWord build(String word, WordType wordType, String analyzer) {
        return new SearchWord(word, wordType, analyzer);
    }

    private SearchWord() {
    }

    private SearchWord(String word, WordType wordType) {
        this.word = word;
        this.wordType = wordType;
    }

    private SearchWord(String word, WordType wordType, String analyzer) {
        this.word = word;
        this.analyzer = analyzer;
        this.wordType = wordType;
    }

    /**
     * 计算字长权重
     *
     * @return
     */
    public SearchWord calculateTypeWeight() {
        int length = this.word.length();
        this.weight = WeightFunction.calculateTypeWeight(length, this.wordType.getBaseLengthWeight());
        return this;
    }

    public String searchWord() {
        return this.word;
    }

    public String analyzer() {
        return this.analyzer;
    }

    public Float weight() {
        return this.weight;
    }

    public WordType wordType() {
        return this.wordType;
    }


    @Override
    public String toString() {
        return "SearchWord{" +
                "word='" + word + '\'' +
                ", weight=" + weight +
                ", analyzer='" + analyzer + '\'' +
                ", wordType=" + wordType +
                '}';
    }
}
