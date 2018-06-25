package com.liceyo.elasticsearch.elastic;

import com.liceyo.elasticsearch.utils.PropertiesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * @author liceyo
 * @version 2018/5/14
 */
public enum  ElasticsearchClient {
    /**
     * 单例
     */
    INSTANCE;

    /**
     * 查询客户端
     */
    private TransportClient client;

    private Logger logger= LogManager.getLogger(ElasticsearchClient.class);

    public TransportClient getInstance(){
        return client;
    }

    ElasticsearchClient() {
        try {
            logger.info("start init elasticsearch");
            //配置初始化
            Properties properties = PropertiesUtil.getProperties(ConstantField.ELASTICSEARCH_PROPERTIES_PATH);
            //es配置初始化
            String clusterName = properties.getProperty(ConstantField.CLUSTER_NAME_FIELD);
            String clusterSniff= properties.getProperty(ConstantField.CLIENT_TRANSPORT_SNIFF_FIELD);
            Settings settings = Settings.builder()
                    .put(ConstantField.CLUSTER_NAME_FIELD, clusterName)
                    .put(ConstantField.CLIENT_TRANSPORT_SNIFF_FIELD, clusterSniff)
                    .build();
            //客户端
            client = new PreBuiltTransportClient(settings);
            String address = properties.getProperty(ConstantField.CLUSTER_IP_ADDRESS_FIELD);
            if (address != null && !address.isEmpty()) {
                String[] split = address.split(",");
                if (split.length > 0) {
                    for (String adr : split) {
                        client.addTransportAddress(getAddress(adr));
                    }
                }
            }
        } catch (UnknownHostException e) {
            logger.error("init elasticsearch error",e);
        }
        logger.info("init elasticsearch success");
    }

    /**
     * 解析地址
     * @param address
     * @return
     * @throws UnknownHostException
     */
    private TransportAddress getAddress(String address) throws UnknownHostException {
        if (address.matches("^.*:\\d+$")){
            String[] split = address.split(":");
            return new TransportAddress(InetAddress.getByName(split[0]),Integer.valueOf(split[1]));
        }else {
            return new TransportAddress(InetAddress.getByName(address), 9300);
        }
    }
}
