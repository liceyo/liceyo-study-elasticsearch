package com.liceyo.elasticsearch;

import com.liceyo.elasticsearch.analysis.ElasticsearchClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * 启动类
 * @author lichy
 * @version 2018/5/14
 */
@SpringBootApplication
public class Application {
    private static Logger logger= LogManager.getLogger(Application.class);

	public static void main(String[] args) {
        List<DiscoveryNode> discoveryNodes = ElasticsearchClient.INSTANCE.getInstance().connectedNodes();
        logger.info("Elasticsearch已连接节点：");
        for (int i = 0; i < discoveryNodes.size(); i++) {
            DiscoveryNode discoveryNode = discoveryNodes.get(i);
            TransportAddress address = discoveryNode.getAddress();
            logger.info((i+1)
                    +".address-["+address.address()+"]" +
                    " node-["+discoveryNode.getName()+"]" +
                    " master-["+discoveryNode.isMasterNode()+"]" +
                    " data-["+discoveryNode.isDataNode()+"]" +
                    " ingest-["+discoveryNode.isIngestNode()+"]");
        }
        SpringApplication.run(Application.class, args);
    }
}
