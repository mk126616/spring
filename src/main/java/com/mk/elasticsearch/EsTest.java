//package com.mk.elasticsearch;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.delete.DeleteRequest;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.get.GetRequest;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.support.master.AcknowledgedResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.action.update.UpdateResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.elasticsearch.client.indices.GetIndexResponse;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.RangeQueryBuilder;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.aggregations.AggregationBuilder;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//import org.junit.Test;
//
//import java.io.IOException;
//
//public class EsTest
//{
//    @Test
//    public void createIndex() throws IOException
//    {
//        RestHighLevelClient client = getClient();
//        //创建索引
//        CreateIndexRequest createIndexRequest = new CreateIndexRequest("user");
//        CreateIndexResponse response = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
//        boolean status = response.isAcknowledged();
//        System.out.println(String.format("创建索引操作状态：%s", status));
//
//        client.close();
//    }
//
//    /**
//     * 查询索引
//     */
//    @Test
//    public void searchIndex() throws IOException
//    {
//        RestHighLevelClient client = getClient();
//        //创建索引
//        GetIndexRequest getIndexRequest = new GetIndexRequest("user");
//        GetIndexResponse response = client.indices().get(getIndexRequest, RequestOptions.DEFAULT);
//        System.out.println(String.format("索引别名：%s", response.getAliases()));
//        System.out.println(String.format("索引映射：%s", response.getMappings()));
//        System.out.println(String.format("索引配置：%s", response.getSettings()));
//
//        client.close();
//    }
//
//    /**
//     * 删除索引
//     */
//    @Test
//    public void deleteIndex() throws IOException
//    {
//        RestHighLevelClient client = getClient();
//        //创建索引
//        DeleteIndexRequest request = new DeleteIndexRequest("user");
//        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
//        System.out.println(String.format("删除索引：%s", response.isAcknowledged()));
//
//        client.close();
//    }
//
//    /**
//     * 插入数据
//     */
//    @Test
//    public void insertDoc() throws IOException
//    {
//        RestHighLevelClient client = getClient();
//
//        //插入数据
//        IndexRequest request = new IndexRequest();
//        request.index("user").id("110");
//        User user = new User();
//        String jsonStr = JSON.toJSONString(user);
//        request.source(jsonStr, XContentType.JSON);
//        //插入
//        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
//
//        System.out.println(String.format("创建数据：%s", response.getResult()));
//
//        client.close();
//    }
//
//    /**
//     * 修改数据
//     */
//    @Test
//    public void updateDoc() throws IOException
//    {
//        RestHighLevelClient client = getClient();
//
//        //插入数据
//        UpdateRequest request = new UpdateRequest();
//        request.index("user").id("110");
//        request.doc(XContentType.JSON, "sex", "女");
//
//        //插入
//        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
//
//        System.out.println(String.format("修改数据：%s", response.getResult()));
//
//        client.close();
//    }
//
//    /**
//     * 查询数据
//     */
//    @Test
//    public void selectDoc() throws IOException
//    {
//        RestHighLevelClient client = getClient();
//
//        GetRequest request = new GetRequest();
//        request.index("user").id("110");
//        //查询
//        GetResponse response = client.get(request, RequestOptions.DEFAULT);
//
//        System.out.println(String.format("查询数据：%s", response.getSourceAsString()));
//
//        client.close();
//    }
//
//    /**
//     * 删除数据
//     */
//    @Test
//    public void deleteDoc() throws IOException
//    {
//        RestHighLevelClient client = getClient();
//
//        DeleteRequest request = new DeleteRequest();
//        request.index("user").id("110");
//        //查询
//        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
//
//        System.out.println(String.format("删除数据：%s", response.getResult()));
//
//        client.close();
//    }
//
//    /**
//     * 批量插入
//     */
//    @Test
//    public void mInsertDoc() throws IOException
//    {
//        RestHighLevelClient client = getClient();
//
//        BulkRequest bulkRequest = new BulkRequest();
//        bulkRequest.add(new IndexRequest().index("user").id("111").source(XContentType.JSON, "name", "lily_v2", "age", "18", "sex", "女"));
//        bulkRequest.add(new IndexRequest().index("user").id("112").source(XContentType.JSON, "name", "danny_v2", "age", "89", "sex", "男"));
//        bulkRequest.add(new IndexRequest().index("user").id("113").source(XContentType.JSON, "name", "liming_v2", "age", "85", "sex", "男"));
//        bulkRequest.add(new IndexRequest().index("user").id("114").source(XContentType.JSON, "name", "zhangsan", "age", "19", "sex", "女"));
//        bulkRequest.add(new IndexRequest().index("user").id("115").source(XContentType.JSON, "name", "lisi", "age", "65", "sex", "男"));
//        bulkRequest.add(new IndexRequest().index("user").id("116").source(XContentType.JSON, "name", "wangwu", "age", "60", "sex", "男"));
//
//        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
//        System.out.println(String.format("批量插入数据：%s", bulkResponse.status()));
//
//        client.close();
//    }
//
//    /**
//     * 批量删除
//     */
//    @Test
//    public void mDeleteDoc() throws IOException
//    {
//        RestHighLevelClient client = getClient();
//
//        BulkRequest bulkRequest = new BulkRequest();
//
//        bulkRequest.add(new DeleteRequest().index("user").id("111"));
//        bulkRequest.add(new DeleteRequest().index("user").id("112"));
//        bulkRequest.add(new DeleteRequest().index("user").id("113"));
//
//        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
//        System.out.println(String.format("批量删除数据：%s", bulkResponse.status()));
//
//        client.close();
//    }
//
//    /**
//     * 批量修改
//     */
//    @Test
//    public void mUpdateDoc() throws IOException
//    {
//        RestHighLevelClient client = getClient();
//
//        BulkRequest bulkRequest = new BulkRequest();
//
//        bulkRequest.add(new UpdateRequest().index("user").id("111").doc(XContentType.JSON, "name", "lily_v2", "age", "18", "sex", "女"));
//        bulkRequest.add(new UpdateRequest().index("user").id("112").doc(XContentType.JSON, "name", "danny_v2", "age", "19", "sex", "男"));
//        bulkRequest.add(new UpdateRequest().index("user").id("113").doc(XContentType.JSON, "name", "liming_v2", "age", "20", "sex", "男"));
//
//        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
//        System.out.println(String.format("批量修改数据：%s", bulkResponse.status()));
//
//        client.close();
//    }
//
//    /**
//     * 复杂全量查询
//     */
//    @Test
//    public void mSelectDocAll() throws IOException
//    {
//        RestHighLevelClient client = getClient();
//
//        SearchRequest request = new SearchRequest();
//        //指定索引
//        request.indices("user");
//        //执行查询条件
//        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
//        //查询
//        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//
//        SearchHits hits = response.getHits();
//
//        System.out.println(String.format("查询数据：%s", response.getTook()));
//        for (SearchHit hit : hits)
//        {
//            System.out.println(String.format("数据项：%s", hit.getSourceAsString()));
//        }
//
//        client.close();
//    }
//
//    /**
//     * 分页查询
//     */
//    @Test
//    public void pageSelectDoc() throws IOException
//    {
//        RestHighLevelClient client = getClient();
//
//        SearchRequest request = new SearchRequest();
//        //指定索引
//        request.indices("user");
//        //指定查询条件
//        SearchSourceBuilder searchBuilder = new SearchSourceBuilder().
//                query(QueryBuilders.matchQuery("sex", "男")).
//                query(QueryBuilders.existsQuery("sex"));
//
//        searchBuilder.from(0).size(4); //分页
//        searchBuilder.sort("age", SortOrder.ASC);//升序
//        //指定查询字段
//        String[] includeFields = {"name", "age"};
//        //排除的字段
//        String[] excludeFields = {};
//        searchBuilder.fetchSource(includeFields, excludeFields);
//
//        //执行查询条件
//        request.source(searchBuilder);
//
//        //查询
//        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//
//        SearchHits hits = response.getHits();
//
//        System.out.println(String.format("查询数据：%s", response.getTook()));
//        for (SearchHit hit : hits)
//        {
//            System.out.println(String.format("数据项：%s", hit.getSourceAsString()));
//        }
//
//        client.close();
//    }
//
//    /**
//     * 范围查询（等值约束）
//     */
//    @Test
//    public void scopeSelectDoc() throws IOException
//    {
//        RestHighLevelClient client = getClient();
//
//        SearchRequest request = new SearchRequest();
//        //指定索引
//        request.indices("user");
//        //构建查询条件
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        SearchSourceBuilder searchBuilder = new SearchSourceBuilder().query(boolQueryBuilder);
//        boolQueryBuilder.must(QueryBuilders.matchQuery("sex", "女")); //必须时
//        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("age", "19"));//必须不是
//        boolQueryBuilder.should(QueryBuilders.matchQuery("sex", "男"));//或者是
//
//        //执行查询条件
//        request.source(searchBuilder);
//
//        //查询
//        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//
//        SearchHits hits = response.getHits();
//
//        System.out.println(String.format("查询数据：%s", response.getTook()));
//        for (SearchHit hit : hits)
//        {
//            System.out.println(String.format("数据项：%s", hit.getSourceAsString()));
//        }
//
//        client.close();
//    }
//
//    /**
//     * 范围查询（范围约束）
//     */
//    @Test
//    public void scopeSelectDoc2() throws IOException
//    {
//        RestHighLevelClient client = getClient();
//
//        SearchRequest request = new SearchRequest();
//        //指定索引
//        request.indices("user");
//        //范围查询
//        //        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
//        //        SearchSourceBuilder searchBuilder = new SearchSourceBuilder().query(rangeQueryBuilder);
//        ////        rangeQueryBuilder.from("18").to("100");//范围限制
//        //        rangeQueryBuilder.lt("20").gt("15");//范围限制
//        //        request.source(searchBuilder);
//
//        //        分组查询
//        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");
//        SearchSourceBuilder searchBuilder = new SearchSourceBuilder().aggregation(aggregationBuilder);
//        request.source(searchBuilder);
//
//        //查询
//        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//        SearchHits hits = response.getHits();
//
//        System.out.println(String.format("查询数据：%s", response.getAggregations().toString()));
//
//        client.close();
//    }
//
//    /**
//     * 创建es客户端
//     *
//     * @return
//     */
//    private RestHighLevelClient getClient()
//    {
//        return new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.62.130", 9200, "http")));
//    }
//}
