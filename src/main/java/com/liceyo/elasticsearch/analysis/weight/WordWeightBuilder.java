package com.liceyo.elasticsearch.analysis.weight;

import com.liceyo.elasticsearch.analysis.ElasticsearchClient;
import com.liceyo.elasticsearch.analysis.token.Tokenizer;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.index.query.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 搜索语句权重
 * @author lichy
 * @version 2018/4/19
 */
public class WordWeightBuilder {
    /**
     * 默认搜索词
     */
    private final static String DEFAULT_ANALYZER = "ik_smart";
    /**
     * 原句
     */
    private String statement;
    /**
     * 解析结果
     */
    private Map<String, SearchWord> analysisBodies;
    /**
     * 是否分词
     */
    private boolean isToken = false;
    /**
     * 是否有空格
     */
    private boolean hasSpace = false;
    /**
     * 替换空格后的语句
     */
    private String replaceSpace = null;
    /**
     * 按空格拆分后分结果
     */
    private String[] replaceSpaceArr = null;
    /**
     * 分词结果
     */
    private List<AnalyzeResponse.AnalyzeToken> tokens = null;

    /**
     * 查询结果
     */
    private BoolQueryBuilder resultQuery;

    /**
     * 初始化权重计算创建器
     * @param statement 句子
     */
    private WordWeightBuilder(String statement) {
        this.statement = statement.toLowerCase();
        if (this.statement.length() > 2) {
            this.isToken = true;
            this.tokens = Tokenizer.token(this.statement).getTokens().stream()
                    .filter(token -> !"OTHER_CJK".equals(token.getType()))
                    .collect(Collectors.toList());
        }
        if (this.statement.matches(".*\\s+.*")) {
            this.hasSpace = true;
            this.replaceSpace = this.statement.replaceAll("\\s+", "");
            this.replaceSpaceArr = this.statement.split("\\s");
        }
        this.resultQuery = QueryBuilders.boolQuery().minimumShouldMatch("75%");
        this.analysisBodies = new HashMap<>();
    }

    /**
     * 创建权重计算器
     * @param statement 原句
     * @return
     */
    public static WordWeightBuilder builder(String statement) {
        return new WordWeightBuilder(statement);
    }

    /**
     * 分析语句，按类型拆分成搜索词
     * 需要顺序
     * @param wordType 搜索词类型
     * @return this
     */
    public WordWeightBuilder analysis(WordType wordType) {
        switch (wordType) {
            case NOT_TOKENS_BOOST:
                this.addAnalysisBody(SearchWord.build(statement, WordType.NOT_TOKENS_BOOST));
                return this;
            case REPLACE_SPACE_BOOST:
                if (this.isToken && this.hasSpace) {
                    this.addAnalysisBody(SearchWord.build(this.replaceSpace, WordType.REPLACE_SPACE_BOOST));
                }
                return this;
            case SPLIT_BY_SPACE_BOOST:
                if (this.isToken && this.hasSpace) {
                    Arrays.stream(replaceSpaceArr).forEach(space -> this.addAnalysisBody(SearchWord.build(space, WordType.SPLIT_BY_SPACE_BOOST)));
                }
                return this;
            case TOKENS_BOOST:
                if (this.isToken) {
                    this.tokens.forEach(token -> this.addAnalysisBody(SearchWord.build(token.getTerm(), WordType.TOKENS_BOOST)));
                } else {
                    this.addAnalysisBody(SearchWord.build(statement, WordType.NOT_TOKENS_BOOST));
                }
                return this;
            default:
                return this;
        }
    }

    /**
     * 分析语句,并拆分成搜索词
     * 默认分析全部
     */
    public WordWeightBuilder defaultAnalysis() {
        analysis(WordType.NOT_TOKENS_BOOST);
        analysis(WordType.REPLACE_SPACE_BOOST);
        analysis(WordType.SPLIT_BY_SPACE_BOOST);
        analysis(WordType.TOKENS_BOOST);
        return this;
    }

    /**
     * 添加搜索词到分析集合
     * @param searchWord 搜索词
     */
    private void addAnalysisBody(SearchWord searchWord) {
        if (searchWord == null || searchWord.searchWord() == null || searchWord.searchWord().trim().length() == 0) {
            return;
        }
        this.analysisBodies.putIfAbsent(searchWord.searchWord(), searchWord);
    }

    /**
     * 计算搜索词类型权重
     * @return this
     */
    public WordWeightBuilder calculateTypeWeight() {
        this.analysisBodies.forEach((key, value) -> this.analysisBodies.put(key, value.calculateTypeWeight()));
        return this;
    }

    /**
     * 获取计算后的搜索词结果
     * @return 计算后的搜索词结果
     */
    public List<SearchWord> result() {
        return this.analysisBodies.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    /**
     * 排除查询
     * @param excludeQuery 排除的查询
     * @return this
     */
    public WordWeightBuilder excludeQuery(List<QueryBuilder> excludeQuery) {
        if (excludeQuery != null && excludeQuery.size() > 0) {
            excludeQuery.forEach(query -> this.resultQuery.mustNot(query));
        }
        return this;
    }

    /**
     * 按默认的分词器组装查询条件
     * @return 组装条件
     */
    public QueryBuilder resultQuery() {
        return resultQuery(DEFAULT_ANALYZER);
    }

    /**
     * 按分词组装查询条件
     * @param analyzer 分词器
     * @return 组装条件
     */
    public QueryBuilder resultQuery(String analyzer) {
        String result = this.analysisBodies.entrySet().stream()
                .map(Map.Entry::getValue)
                .map(sw -> "(" + sw.searchWord() + ")^" + sw.weight())
                .collect(Collectors.joining());
        QueryStringQueryBuilder builder = QueryBuilders.queryStringQuery(result)
                .analyzer(analyzer)
                .defaultOperator(Operator.OR)
                .minimumShouldMatch("75%");
        for (String filed : ElasticsearchClient.INSTANCE.searchFiled()) {
            builder.field(filed);
        }
        this.resultQuery.must(builder);
        return this.resultQuery;
    }
}
