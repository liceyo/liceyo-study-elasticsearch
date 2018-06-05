package com.liceyo.elasticsearch.analysis;

/**
 * @author liceyo
 * @version 2018/5/14
 */
public class ConstantField {
    /**
     * elasticsearch配置路径
     */
    public final static String ELASTICSEARCH_PROPERTIES_PATH="/elasticsearch.properties";
    public final static String CLUSTER_NAME_FIELD="cluster.name";
    public final static String CLUSTER_IP_ADDRESS_FIELD="cluster.ip.address";
    public final static  String CLIENT_TRANSPORT_SNIFF_FIELD="client.transport.sniff";
    public final static  String SEARCH_TYPE_FIELD="search.type.field";
    public final static String SEARCH_DEFAULT_FIELD ="search.default.field";
    public final static String LIST_INCLUDES_FIELD =".list.includes";
    public final static String NEWS_LIST_INCLUDES ="news.list.includes";
    public final static String RESEARCH_LIST_INCLUDES ="research.list.includes";
    /**
     * 索引名称
     * 索引名称是别名，不停机重索引，所以不需要配置文件配置
     */
    public final static String INDEX_NAME="liceyo";

    /**
     * 索引类型
     */
    public final static String INDEX_TYPE="liceyo";
}
