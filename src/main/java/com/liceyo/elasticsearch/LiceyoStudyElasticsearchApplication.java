package com.liceyo.elasticsearch;

import com.liceyo.elasticsearch.analysis.ElasticsearchClient;
import com.liceyo.elasticsearch.analysis.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @author lichy
 * @version 2018/5/14
 */
@SpringBootApplication
public class LiceyoStudyElasticsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiceyoStudyElasticsearchApplication.class, args);
    }
}
