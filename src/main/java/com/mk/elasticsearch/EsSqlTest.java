package com.mk.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EsSqlTest
{
    private Logger logger = LoggerFactory.getLogger(EsSqlTest.class);
    /**
     * 创建es客户端
     *
     * @return
     */
    private RestHighLevelClient getClient()
    {
        return new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.62.135", 9200, "http")));
    }
    @Test
    public void createIndex() throws IOException
    {
        RestHighLevelClient client = getClient();
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("sqlindex");
        client.indices().create(createIndexRequest);
        System.out.println("索引创建成功");
    }

}
