package com.liceyo.elasticsearch.elastic.query;

import com.liceyo.elasticsearch.elastic.sentence.SentencePretreatment;
import com.liceyo.elasticsearch.elastic.sentence.SentenceAnalysis;
import com.liceyo.elasticsearch.elastic.sentence.SimpleSentencePretreatment;
import com.liceyo.elasticsearch.elastic.sentence.analysis.FullPingYinSentenceAnalysis;
import com.liceyo.elasticsearch.elastic.sentence.analysis.IkSmartSentenceAnalysis;
import com.liceyo.elasticsearch.elastic.sentence.analysis.OriginalSentenceAnalysis;
import com.liceyo.elasticsearch.elastic.sentence.analysis.SimplePingYinSentenceAnalysis;
import org.elasticsearch.index.query.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用查询创建
 * 包含语句解析、权重计算、查询语句组装
 * @author liceyo
 * @version 2018/6/22
 */
public class CommonQueryBuilder {
    /**
     * 语句
     */
    private String sentence;

    /**
     * 预处理
     */
    private SentencePretreatment sentencePretreatment;

    /**
     * 语句分析
     */
    private List<SentenceAnalysis> sentenceQueries;

    /**
     * 查询组装方式
     */
    private BuildMode buildMode;

    private CommonQueryBuilder(String sentence) {
        this.sentence = sentence;
        this.sentenceQueries=new ArrayList<>();
        this.buildMode=BuildMode.DIS_MAX;
    }

    public static CommonQueryBuilder build(String sentence){
        return new CommonQueryBuilder(sentence);
    }

    /**
     * 默认组装查询
     * @param sentence
     * @return
     */
    public static QueryBuilder defaultQuery(String sentence){
        SimpleSentencePretreatment pretreatment=new SimpleSentencePretreatment();
        OriginalSentenceAnalysis original = new OriginalSentenceAnalysis();
        IkSmartSentenceAnalysis ikAnd = new IkSmartSentenceAnalysis(Operator.AND);
        IkSmartSentenceAnalysis ikOr = new IkSmartSentenceAnalysis(Operator.OR, "75%");
        FullPingYinSentenceAnalysis fpy = new FullPingYinSentenceAnalysis();
        SimplePingYinSentenceAnalysis spy = new SimplePingYinSentenceAnalysis();
        return CommonQueryBuilder.build(sentence)
                .buildMode(BuildMode.DIS_MAX)
                .pretreatment(pretreatment)
                .addSentenceAnalysis(original)
                .addSentenceAnalysis(ikAnd)
                .addSentenceAnalysis(ikOr)
                .addSentenceAnalysis(fpy)
                .addSentenceAnalysis(spy)
                .result();
    }

    /**
     * 加载预处理类
     * @param pretreatment
     * @return
     */
    public CommonQueryBuilder pretreatment(SentencePretreatment pretreatment){
        this.sentencePretreatment=pretreatment;
        return this;
    }

    /**
     * 加载语句分析类
     * @param sentenceAnalysis
     * @return
     */
    public CommonQueryBuilder addSentenceAnalysis(SentenceAnalysis sentenceAnalysis){
        this.sentenceQueries.add(sentenceAnalysis);
        return this;
    }

    /**
     * 查询组装方式
     * @param buildMode
     * @return
     */
    public CommonQueryBuilder buildMode(BuildMode buildMode){
        this.buildMode=buildMode;
        return this;
    }

    /**
     * 结果
     * @return
     */
    public QueryBuilder result(){
        //预处理
        this.sentencePretreatment.pretreatment(sentence);
        //判断语句合法性
        if (!this.sentencePretreatment.isLegal()){
            return null;
        }
        //判断是否需要过滤分词
        boolean canSegment = this.sentencePretreatment.canSegment();
        //预处理结果
        String result = this.sentencePretreatment.result();
        //根据类型组装查询
        switch (buildMode){
            case DIS_MAX :
                DisMaxQueryBuilder disMaxQuery = QueryBuilders.disMaxQuery();
                this.sentenceQueries.stream()
                        .filter(a-> canSegment || a.needSegment())
                        .forEach(a->disMaxQuery.add(a.analysis(result)));
                return disMaxQuery;
            case BOOL_MAST :
                BoolQueryBuilder boolQueryMust = QueryBuilders.boolQuery();
                this.sentenceQueries.stream()
                        .filter(a-> canSegment || a.needSegment())
                        .forEach(a->boolQueryMust.must(a.analysis(result)));
                return boolQueryMust;
            case BOOL_SHOULD :
                BoolQueryBuilder boolQueryShould = QueryBuilders.boolQuery();
                this.sentenceQueries.stream()
                        .filter(a-> canSegment || a.needSegment())
                        .forEach(a-> boolQueryShould.should(a.analysis(result)));
                return boolQueryShould;
            default:
                return null;
        }
    }
}
