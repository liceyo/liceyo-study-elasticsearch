package com.liceyo.elasticsearch.analysis.score;

import com.liceyo.elasticsearch.analysis.ConstantValue;
import com.liceyo.elasticsearch.analysis.ElasticsearchClient;
import com.liceyo.elasticsearch.analysis.SearchType;
import com.liceyo.elasticsearch.analysis.weight.WordWeightBuilder;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.WeightBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 函数评分创建器
 *
 * @author liceyo
 * @version 2018/5/17
 */
public class FunctionScoreBuilder {
    /**
     * 搜索原句
     */
    private String statement;
    /**
     * 实际查询
     */
    private QueryBuilder query;

    /**
     * 只创建指定类型的评分
     */
    private SearchType onlyModule;

    /**
     * 是否只创建一个类型的评分查询
     */
    private boolean isOnly = false;

    /**
     * 评分函数集合
     */
    private List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders;

    /**
     * 使用默认查询
     *
     * @param statement
     */
    private FunctionScoreBuilder(String statement) {
        this.filterFunctionBuilders = new ArrayList<>();
        this.statement = statement;
        this.query = WordWeightBuilder.builder(statement)
                .defaultAnalysis()
                .calculateTypeWeight()
                .resultQuery();
    }

    private FunctionScoreBuilder(String statement, QueryBuilder query) {
        this.filterFunctionBuilders = new ArrayList<>();
        this.statement = statement;
        this.query = query;
    }

    /**
     * 使用默认查询
     *
     * @param statement
     */
    private FunctionScoreBuilder(String statement, SearchType module) {
        this.filterFunctionBuilders = new ArrayList<>();
        this.statement = statement;
        this.onlyModule = module;
        if (module != null) {
            this.isOnly = true;
        }
        this.query = WordWeightBuilder.builder(statement)
                .defaultAnalysis()
                .calculateTypeWeight()
                .resultQuery();
    }

    private FunctionScoreBuilder(String statement, QueryBuilder query, SearchType module) {
        this.filterFunctionBuilders = new ArrayList<>();
        this.statement = statement;
        this.query = query;
        this.onlyModule = module;
        if (module != null) {
            this.isOnly = true;
        }
    }

    public static FunctionScoreBuilder builder(String statement) {
        return new FunctionScoreBuilder(statement);
    }

    public static FunctionScoreBuilder builder(String statement, QueryBuilder query) {
        return new FunctionScoreBuilder(statement, query);
    }

    public static FunctionScoreBuilder builder(String statement, SearchType module) {
        return new FunctionScoreBuilder(statement, module);
    }

    public static FunctionScoreBuilder builder(String statement, QueryBuilder query, SearchType module) {
        return new FunctionScoreBuilder(statement, query, module);
    }

    /**
     * 向评分函数集合添加评分函数
     *
     * @param module
     * @param scoreFunctionBuilder
     * @return
     */
    public FunctionScoreBuilder addFunction(SearchType module, ScoreFunctionBuilder scoreFunctionBuilder) {
        this.add(module, scoreFunctionBuilder);
        return this;
    }

    /**
     * 向评分函数集合添加评分函数
     * 如果指定了类型，则不添加入评分
     *
     * @param module
     * @param scoreFunctionBuilder
     */
    private void add(SearchType module, ScoreFunctionBuilder scoreFunctionBuilder) {
        if (isOnly) {
            if (!module.equals(this.onlyModule)) {
                return;
            }
        }
        this.filterFunctionBuilders.add(this.toFilter(module, scoreFunctionBuilder));
    }

    /**
     * 添加类型过滤评分函数
     *
     * @return
     */
    public FunctionScoreBuilder addTypeFunction() {
        //如果指定了类型，不添加类型过滤评分
        if (isOnly) {
            return this;
        }
        for (SearchType module : SearchType.values()) {
            float weight = module.baseWeight();
            if (weight != 1) {
                WeightBuilder weightBuilder = ScoreFunctionBuilders.weightFactorFunction(weight);
                this.add(module, weightBuilder);
            }
        }
        return this;
    }

    /**
     * 默认评分函数
     *
     * @return
     */
    public FunctionScoreBuilder defaultFunction() {
        this.add(SearchType.RESEARCH, ScoreFunction.researchDateDecayFunction());
        //3.添加资讯时间指数衰减function
        this.add(SearchType.NEWS, ScoreFunction.articleDateDecayFunction());
        return this.addTypeFunction();
    }

    /**
     * 组装结果
     *
     * @return
     */
    public QueryBuilder resultQuery() {
        //如果评分过滤集合为空 使用默认评分
        if (this.filterFunctionBuilders.size() == 0) {
            this.defaultFunction();
        }
        //如果都被过滤了，则不使用评分函数
        if (this.filterFunctionBuilders.size() == 0) {
            return this.query;
        }
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filters = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
        filterFunctionBuilders.toArray(filters);
        return QueryBuilders.functionScoreQuery(this.query, filters).boostMode(CombineFunction.MULTIPLY);
    }

    /**
     * 根据类型创建对应function的FunctionScoreQuery
     * @param module
     * @param scoreFunctionBuilder
     * @return
     */
    private FunctionScoreQueryBuilder.FilterFunctionBuilder toFilter(SearchType module, ScoreFunctionBuilder scoreFunctionBuilder) {
        TermQueryBuilder termQuery = QueryBuilders.termQuery(ConstantValue.DATA_TYPE_FILED, module.typeValue());
        return new FunctionScoreQueryBuilder.FilterFunctionBuilder(termQuery, scoreFunctionBuilder);
    }
}
