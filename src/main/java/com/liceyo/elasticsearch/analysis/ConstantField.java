package com.liceyo.elasticsearch.analysis;

/**
 * 配置文件的字段
 * @author liceyo
 * @version 2018/5/14
 */
public class ConstantField {
    /**
     * elasticsearch配置路径
     */
    public final static String ELASTICSEARCH_PROPERTIES_PATH="/elasticsearch.properties";
    /**
     * 集群名
     */
    public final static String CLUSTER_NAME_FIELD="cluster.name";
    /**
     * 集群IP
     */
    public final static String CLUSTER_IP_ADDRESS_FIELD="cluster.ip.address";
    /**
     * 自动嗅探集群
     */
    public final static  String CLIENT_TRANSPORT_SNIFF_FIELD="client.transport.sniff";
    /**
     * 搜索类型
     */
    public final static  String SEARCH_TYPE_FIELD="search.type.field";
    /**
     * 默认搜索字段
     */
    public final static String SEARCH_DEFAULT_FIELD ="search.default.field";
    /**
     * 各模块列表搜索的默认包含字段
     */
    public final static String LIST_INCLUDES_FIELD =".list.includes";
    /**
     * 搜索索引
     */
    public final static String SEARCH_INDEX_NAME_FIELD="search.index.name";
    /**
     * 搜索类型
     */
    public final static String SEARCH_INDEX_TYPE_FIELD="search.index.type";
    /**
     * 自动提示索引
     */
    public final static String AUTO_COMPLETION_INDEX_NAME_FIELD="auto.completion.index.name";
    /**
     * 自动提示类型
     */
    public final static String AUTO_COMPLETION_INDEX_TYPE_FIELD="auto.completion.index.type";
}
