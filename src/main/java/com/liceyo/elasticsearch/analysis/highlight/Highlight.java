package com.liceyo.elasticsearch.analysis.highlight;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

/**
 * 高亮控制
 * @author liceyo
 * @version 2018/5/14
 */
public class Highlight {
    private static Logger logger= LogManager.getLogger(Highlight.class);

    /**
     * 前标签
     */
    private final static String PRE_TAGS ="<em>";

    /**
     * 后标签
     */
    private final static String POST_TAGS ="</em>";

    /**
     * 默认高亮字段
     */
    private final static String[] DEFAULT_HIGHLIGHT_FIELD = {"title","content"};

    /**
     * 默认高亮字段
     * @return
     */
    public static HighlightBuilder highlight() {
        return highlight(DEFAULT_HIGHLIGHT_FIELD);
    }


    /**
     * 构建高亮
     * @param fields  需要高亮的字段
     * @return
     */
    public static HighlightBuilder highlight(String... fields) {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        for (String field : fields) {
            highlightBuilder.field(field);
        }
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags(PRE_TAGS);
        highlightBuilder.postTags(POST_TAGS);
        highlightBuilder.highlighterType("plain");
        highlightBuilder.fragmentSize(50);
        highlightBuilder.numOfFragments(2);
        return highlightBuilder;
    }
}
