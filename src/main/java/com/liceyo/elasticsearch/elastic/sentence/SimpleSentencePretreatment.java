package com.liceyo.elasticsearch.elastic.sentence;

import org.apache.lucene.queryparser.classic.QueryParser;

import java.util.stream.Stream;

/**
 * @author liceyo
 * @version 2018/6/22
 */
public class SimpleSentencePretreatment implements SentencePretreatment{
    /**
     * 最大长度
     */
    private final static int MAX_LENGTH =64;
    /**
     * 原句
     */
    private String original;
    /**
     * 处理结果
     */
    private String result;
    /**
     * 语句是否合法
     */
    private boolean legal=false;
    /**
     * 是否需要分词
     */
    private boolean tokenizer=true;

    @Override
    public void pretreatment(String sentence) {
        original=sentence;
        if (sentence==null||sentence.length()==0){
            return;
        }
        //小于等于2个字符不分词
        String trim = sentence.trim();
        if (trim.length()<=2){
            tokenizer=false;
        }
        //多于MAX_LENGTH的字符舍掉
        if (trim.length()>MAX_LENGTH){
            trim=trim.substring(0,MAX_LENGTH);
        }
        result=QueryParser.escape(trim);
        legal=true;
    }

    @Override
    public String result() {
        return result;
    }

    @Override
    public boolean isLegal() {
        return legal;
    }

    @Override
    public boolean canSegment() {
        return tokenizer;
    }


}
