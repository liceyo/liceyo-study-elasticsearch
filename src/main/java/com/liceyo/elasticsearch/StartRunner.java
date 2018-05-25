package com.liceyo.elasticsearch;


import com.liceyo.elasticsearch.analysis.ElasticsearchClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @desc: 启动运行
 * 检查是否有超级账户，没有就新加个
 * @author: fuxiang
 * @date: 2017/8/9 9:10
 */
@Component
@Order(1)
public class StartRunner implements CommandLineRunner {
    private static Logger logger= LogManager.getLogger(StartRunner.class);
    @Override
    public void run(String... strings) {
    }
}
