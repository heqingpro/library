package com.ipanel.web.book.elasticsearch;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ipanel.web.utils.Constants;

/**
 * 
 * @author lvchao
 * @email chao9038@hnu.edu.cn
 * @createtime 2018年4月13日 下午4:00:34
 */
public class ElasticsearchClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchClientFactory.class);

    private static String host = Constants.ELASTICSEARCH_HOST;
    
    private static String port = Constants.ELASTICSEARCH_PORT;

    private static String clusterName = Constants.ELASTICSEARCH_CLUSTER_NAME;
    
    
    @SuppressWarnings("resource")
	public static TransportClient getClient() {
        TransportClient transportClient = null;
        try {
        	// 自定义配置
            Settings settings = Settings.builder()
            		/**
            		 * 设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，
            		 * 这样做的好处是一般你不用手动设置集群里所有集群的ip到连接客户端，它会自动帮你添加，并且自动发现新加入集群的机器。
            		 * 
            		 * 注意：当ES服务器监听使用内网服务器IP而访问使用外网IP时，不要使用client.transport.sniff为true，
            		 * 在自动发现时会使用内网IP进行通信，导致无法连接到ES服务器，而直接使用addTransportAddress方法进行指定ES服务器。
            		 */
                    .put("client.transport.sniff", true)
                    .put("cluster.name", clusterName)
                    .build();
            
            // 使用自定义配置
            TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(host), Integer.valueOf(port));
            transportClient = new PreBuiltTransportClient(settings).addTransportAddresses(transportAddress);
            
        } catch (Exception e) {
            LOGGER.error("Elasticsearch TransportClient Create Error!", e);
        }
        return transportClient;
    }
    
}