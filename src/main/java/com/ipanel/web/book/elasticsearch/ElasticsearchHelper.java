package com.ipanel.web.book.elasticsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;


/**
 * 注意：从6.x开始，一个index只能对应一个type，因此别妄想像5.x那样一个index可以有多个type了
 * 
 * @author lvchao
 * @email chao9038@hnu.edu.cn
 * @createtime 2018年4月8日 下午5:56:31
 */
@Component
public class ElasticsearchHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchHelper.class);

    private TransportClient client = ElasticsearchClientFactory.getClient();

    
    
    /**
     * 创建一个空索引，并指定索引的字段属性
     * 
     * 定义索引的字段属性：
     * 	1、Mapping，就是对索引库中索引的字段名及其数据类型进行定义，类似于关系数据库中表建立时要定义字段名及其数据类型那样，不过es的mapping比数据库灵活很多，它可以动态添加字段
     * 	2、一般不需要要指定mapping都可以，因为es会自动根据数据格式定义它的类型，但是如果需要对某些字段添加特殊属性（如：定义使用其它分词器、是否分词、是否存储等），就必须手动添加mapping
     * 	3、有两种添加mapping的方法，一种是定义在配置文件中，一种是运行时手动提交mapping，两种选一种就行了（需要先建立空索引，然后使用mapping添加各个字段及其属性），这里直接写死，避免现场乱改
     * 
     * @param index
     * @param type
     * @return
     */
    public boolean createIndex(String index, String type) {
    	boolean flag = false;
        try {
        	// 创建一个空索引
        	if (!isIndexExist(index)) {
        		CreateIndexResponse response = client.admin().indices().prepareCreate(index).execute().actionGet();
                if(response.isAcknowledged()) {
            		LOGGER.info("createIndex->Index create success!");
            		
            		// 添加mapping，properties下面的为索引里面的字段，type为数据类型，store为是否true（是否单独存储该字段），index是否true（该字段是否被索引）
                	XContentBuilder mapping = XContentFactory.jsonBuilder()
                			.startObject()
                			.startObject("properties")
                			.startObject("id").field("type", "integer").field("index", "false").endObject()
							.startObject("entry_type_id").field("type", "integer").field("index", "false").endObject()
							.startObject("lang_id").field("type", "integer").field("index", "false").endObject()
							.startObject("app_id").field("type", "integer").field("index", "false").endObject()
							.startObject("attach_info_id").field("type", "integer").field("index", "false").endObject()
							
							.startObject("author").field("type", "text").field("analyzer","ik_smart").endObject()
							.startObject("original_author").field("type", "text").field("analyzer","ik_smart").endObject()
							.startObject("editor").field("type", "text").field("analyzer","ik_smart").endObject()
							.startObject("title").field("type", "text").field("analyzer","ik_smart").endObject()
							.startObject("short_name").field("type", "text").field("analyzer","ik_smart").endObject()
							.startObject("description").field("type", "text").field("analyzer","ik_smart").endObject()
							
							.startObject("content").field("type", "text").field("index", "false").endObject()
							.startObject("pub_org").field("type", "text").field("index", "false").endObject()
							.startObject("years_id").field("type", "integer").field("index", "false").endObject()
							.startObject("uid").field("type", "text").field("index", "false").endObject()
							.startObject("global_guid").field("type", "text").field("index", "false").endObject()
							.startObject("page_count").field("type", "integer").field("index", "false").endObject()
							.startObject("width").field("type", "text").field("index", "false").endObject()
							.startObject("height").field("type", "text").field("index", "false").endObject()
							.startObject("format_type").field("type", "text").field("index", "false").endObject()
							.startObject("path").field("type", "text").field("index", "false").endObject()
							.startObject("cover_image_id").field("type", "integer").field("index", "false").endObject()
							.startObject("cover_image").field("type", "text").field("index", "false").endObject()
							.startObject("cover_thumb_nail_id").field("type", "integer").field("index", "false").endObject()
							.startObject("cover_thumb_nail").field("type", "text").field("index", "false").endObject()
							.startObject("save_path").field("type", "text").field("index", "false").endObject()
							.startObject("content_path").field("type", "text").field("index", "false").endObject()
							.startObject("audio_path").field("type", "text").field("index", "false").endObject()
							.startObject("edition_type").field("type", "integer").field("index", "false").endObject()
							.startObject("is_prize").field("type", "integer").field("index", "false").endObject()
							.startObject("add_time").field("type", "text").field("index", "false").endObject()
							.startObject("modify_time").field("type", "text").field("index", "false").endObject()
							.startObject("operate_user_id").field("type", "integer").field("index", "false").endObject()
                	        .endObject()
                	        .endObject();
        	        PutMappingRequest mappingRequest = Requests.putMappingRequest(index).type(type).source(mapping);
        	        
        	        // 同一个索引里可以有不同的索引类型
        	        client.admin().indices().putMapping(mappingRequest).actionGet();
        	        flag = true;
	            } else {
	            	LOGGER.info("createIndex->Index create failed!");
	            }
            } else {
            	LOGGER.info("createIndex->Index exists, create failed!");
            }
        	
        } catch(Exception e) {
        	LOGGER.error("createIndex->Unknown error, index create failed!", e);
        }
        return flag;
    }
    
    
    
    /**
     * 创建一个空索引
     *
     * @param index
     * @return
     */
    public boolean createIndex(String index) {
        if (!isIndexExist(index)) {
            CreateIndexResponse response = client.admin().indices().prepareCreate(index).execute().actionGet();
            if(response.isAcknowledged()) {
            	LOGGER.info("createIndex->Index create success!");
            } else {
            	LOGGER.info("createIndex->Index create failed!");
            }
            return response.isAcknowledged();
        } else {
        	LOGGER.info("createIndex->Index exists, create failed!");
        	return false;
        }
    }
    

    
    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public boolean deleteIndex(String index) {
        if (isIndexExist(index)) {
            DeleteIndexResponse response = client.admin().indices().prepareDelete(index).execute().actionGet();
            if (response.isAcknowledged()) {
                LOGGER.info("deleteIndex->Index delete success!");
            } else {
                LOGGER.info("deleteIndex->Index delete failed!");
            }
            return response.isAcknowledged();
        } else {
        	LOGGER.info("deleteIndex->Index not exists, delete failed!");
        	return false;
        }
    }
    

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    public boolean isIndexExist(String index) {
        IndicesExistsResponse response = client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
        if (response.isExists()) {
            LOGGER.info("isIndexExist->Index [" + index + "] exists!");
        } else {
            LOGGER.info("isIndexExist->Index [" + index + "] not exists!");
        }
        return response.isExists();
    }

    
    /**
     * 添加数据，指定ID
     *
     * @param jsonObject 要增加的数据，必须是json格式
     * @param index      索引，数据库名
     * @param type       类型，表名
     * @param id         数据唯一ID
     * @return
     */
    public boolean addData(JSONObject jsonObject, String index, String type, String id) {
        IndexResponse response = client.prepareIndex(index, type, id).setSource(jsonObject).get();
        LOGGER.info("addData->response result:{}, id:{}", response.getResult(), response.getId());
        return "CREATED".equalsIgnoreCase(response.getResult().toString()) ? true : false;
    }


    /**
     * 通过ID 删除数据
     *
     * @param index 索引，类似数据库
     * @param type  类型，类似表
     * @param id    数据ID
     */
    public boolean deleteDataById(String index, String type, String id) {
        DeleteResponse response = client.prepareDelete(index, type, id).execute().actionGet();
        LOGGER.info("deleteDataById->response result:{}, id:{}", response.getResult(), response.getId());
        return "DELETED".equalsIgnoreCase(response.getResult().toString()) ? true : false;
    }

    
    /**
     * 通过ID 更新数据
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @param id         数据ID
     * @return
     */
    public boolean updateDataById(JSONObject jsonObject, String index, String type, String id) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index).type(type).id(id).doc(jsonObject);
        UpdateResponse response  = client.update(updateRequest).actionGet();
        LOGGER.info("updateDataById->response result:{}, id:{}", response.getResult(), response.getId());
        return "UPDATED".equalsIgnoreCase(response.getResult().toString()) ? true : false;
    }
    

    /**
     * 通过ID 获取数据
     *
     * @param index  索引，类似数据库
     * @param type   类型，类似表
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return
     */
    public Map<String, Object> getDataById(String index, String type, String id, String fields) {
        GetRequestBuilder getRequestBuilder = client.prepareGet(index, type, id);
        if (StringUtils.isNotEmpty(fields)) {
            getRequestBuilder.setFetchSource(fields.split(","), null);
        }
        GetResponse response =  getRequestBuilder.execute().actionGet();
        return response.getSource();
    }


    /**
     * 使用分词查询
     *
     * @param index    索引名称
     * @param type     类型名称,可传入多个type逗号分隔
     * @param fields   需要显示的字段，逗号分隔（缺省为全部字段）
     * @param matchCondition 过滤条件（field1=text1,field2=text2），The field name、The query text(to be analyzed)
     * @return
     */
    public List<Map<String, Object>> searchListData(String index, String type, String fields, String matchCondition) {
        return searchListData(index, type, 0, 0, null, fields, null, false, null, matchCondition);
    }
    

    /**
     * 使用分词查询，排序字段，开启短语精准匹配
     *
     * @param index       索引名称
     * @param type        类型名称,可传入多个type逗号分隔
     * @param fields      需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField   排序字段
     * @param matchPhrase true 使用，短语精准匹配
     * @param matchCondition    过滤条件（field1=text1,field2=text2），The field name、The query text(to be analyzed)
     * @return
     */
    public List<Map<String, Object>> searchListData(String index, String type, String fields, String sortField, boolean matchPhrase, String matchCondition) {
        return searchListData(index, type, 0, 0, null, fields, sortField, matchPhrase, null, matchCondition);
    }


    /**
     * 使用分词查询，排序字段，开启短语精准匹配，添加高亮字段，期望查询数量
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param size           期望查询数量
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param matchPhrase    true 使用，短语精准匹配
     * @param highlightFields 高亮字段
     * @param matchCondition       过滤条件（field1=text1,field2=text2），The field name、The query text(to be analyzed)
     * @return
     */
    public List<Map<String, Object>> searchListData(String index, String type, Integer size, String fields, String sortField, boolean matchPhrase, String highlightFields, String matchCondition) {
        return searchListData(index, type, 0, 0, size, fields, sortField, matchPhrase, highlightFields, matchCondition);
    }


    /**
     * 使用分词查询，排序字段，开启短语精准匹配，添加高亮字段，开始/结束时间，期望查询数量
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param size           期望查询数量
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param matchPhrase    true 使用，短语精准匹配
     * @param highlightField 高亮字段，用英文逗号分隔
     * @param matchCondition 过滤条件如"field1=text1,field2=text2"，（The field name、The query text to be analyzed）
     * @return
     */
    public List<Map<String, Object>> searchListData(String index, String type, long startTime, long endTime, Integer size, String fields, String sortField, boolean matchPhrase, String highlightFields, String matchCondition) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(","));
        }
        
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (startTime > 0 && endTime > 0) {
            boolQuery.must(QueryBuilders.rangeQuery("processTime")
                    .format("epoch_millis")
                    .from(startTime)
                    .to(endTime)
                    .includeLower(true)
                    .includeUpper(true));
        }

        /**
         * 组合查询
         * must(QueryBuilders) 		: AND
         * mustNot(QueryBuilders)	: NOT
         * should:                  : OR
         */
        if (StringUtils.isNotEmpty(matchCondition)) {
            for (String condition : matchCondition.split(",")) {
                if (condition.split("=").length > 1) {
                    if (matchPhrase == Boolean.TRUE) {
                        boolQuery.should(QueryBuilders.matchPhraseQuery(condition.split("=")[0], condition.split("=")[1]));
                    } else {
                        boolQuery.should(QueryBuilders.matchQuery(condition.split("=")[0], condition.split("=")[1]));
                    }
                }
            }
        }

        // 高亮结果集
        if (StringUtils.isNotEmpty(highlightFields)) {
	        HighlightBuilder highlightBuilder = new HighlightBuilder();
	        highlightBuilder.preTags("<span style='color:red' >"); // 前、后缀
	        highlightBuilder.postTags("</span>");
	        for(String highlightField : highlightFields.split(",")) { // 设置高亮字段
	        	highlightBuilder.field(highlightField);
	        }
	        searchRequestBuilder.highlighter(highlightBuilder);
        }
        
        searchRequestBuilder.setQuery(boolQuery);

        if (StringUtils.isNotEmpty(fields)) {
            searchRequestBuilder.setFetchSource(fields.split(","), null);
        }
        searchRequestBuilder.setFetchSource(true);

        if (StringUtils.isNotEmpty(sortField)) {
            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
        }

        if (size != null && size > 0) {
            searchRequestBuilder.setSize(size);
        }

        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        LOGGER.info("\n{}", searchRequestBuilder);

        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        long total = searchResponse.getHits().totalHits;
        long handled = searchResponse.getHits().getHits().length;

        LOGGER.info("共查询到[{}]条数据，共处理[{}]条数据", total, handled);

        if (searchResponse.status().getStatus() == 200) {
            // 解析对象
            return highlightSearchResponse(searchResponse, highlightFields);
        }

        return null;
    }

    
    /**
     * 使用分词查询，排序字段，开启短语精准匹配，添加高亮字段，开始/结束时间，分页
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param currentPage    当前页
     * @param pageSize       每页显示条数
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param matchPhrase    true 使用，短语精准匹配
     * @param highlightFields高亮字段，用英文逗号分隔
     * @param matchCondition 过滤条件如"field1=text1,field2=text2"，（The field name、The query text to be analyzed）
     * @return
     */
    public EsPage searchDataPage(String index, String type, int currentPage, int pageSize, long startTime, long endTime, String fields, String sortField, boolean matchPhrase, String highlightFields, String matchCondition) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(","));
        }
        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);

        // 需要显示的字段，逗号分隔（缺省为全部字段）
        if (StringUtils.isNotEmpty(fields)) {
            searchRequestBuilder.setFetchSource(fields.split(","), null);
        }

        //排序字段
        if (StringUtils.isNotEmpty(sortField)) {
            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
        }

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (startTime > 0 && endTime > 0) {
            boolQuery.must(QueryBuilders.rangeQuery("processTime")
                    .format("epoch_millis")
                    .from(startTime)
                    .to(endTime)
                    .includeLower(true)
                    .includeUpper(true));
        }

        /**
         * 组合查询
         * must(QueryBuilders) 		: AND
         * mustNot(QueryBuilders)	: NOT
         * should:                  : OR
         */
        if (StringUtils.isNotEmpty(matchCondition)) {
            for (String condition : matchCondition.split(",")) {
                if (matchPhrase == Boolean.TRUE) {
                    boolQuery.should(QueryBuilders.matchPhraseQuery(condition.split("=")[0], condition.split("=")[1]));
                } else {
                    boolQuery.should(QueryBuilders.matchQuery(condition.split("=")[0], condition.split("=")[1]));
                }
            }
        }

        // 高亮结果集
        if (StringUtils.isNotEmpty(highlightFields)) {
	        HighlightBuilder highlightBuilder = new HighlightBuilder();
	        highlightBuilder.preTags("<span style='color:red' >"); // 前、后缀
	        highlightBuilder.postTags("</span>");
	        for(String highlightField : highlightFields.split(",")) { // 设置高亮字段
	        	highlightBuilder.field(highlightField);
	        }
	        searchRequestBuilder.highlighter(highlightBuilder);
        }

        searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
        searchRequestBuilder.setQuery(boolQuery);

        // 分页应用
        searchRequestBuilder.setFrom(currentPage).setSize(pageSize);

        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);

        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        LOGGER.info("\n{}", searchRequestBuilder);

        // 执行搜索,返回搜索响应信息
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        long total = searchResponse.getHits().totalHits;
        long handled = searchResponse.getHits().getHits().length;

        LOGGER.debug("共查询到[{}]条数据,处理数据条数[{}]", total, handled);

        if (searchResponse.status().getStatus() == 200) {
            // 解析对象
            List<Map<String, Object>> sourceList = highlightSearchResponse(searchResponse, highlightFields);

            return new EsPage(currentPage, pageSize, (int) total, sourceList);
        }

        return null;
    }
    

    /**
     * 高亮结果集 特殊处理
     *
     * @param searchResponse
     * @param highlightField
     */
    private List<Map<String, Object>> highlightSearchResponse(SearchResponse searchResponse, String highlightFields) {
        List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>();

        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            searchHit.getSourceAsMap().put("id", searchHit.getId());
            if (StringUtils.isNotEmpty(highlightFields)) {
	            for(String field : highlightFields.split(",")) {
		            if (StringUtils.isNotEmpty(field)) {
		            	LOGGER.info("遍历高亮结果集，覆盖正常结果集 : " + searchHit.getSourceAsMap());
		            	
		            	if(searchHit.getHighlightFields().get(field) != null) {
			                Text[] hitTexts = searchHit.getHighlightFields().get(field).getFragments();
			                if (hitTexts != null) {
			                	StringBuffer sb = new StringBuffer();
			                    for (Text text : hitTexts) {
			                        sb.append(text.string());
			                    }
			                    //使用高亮结果集，覆盖正常结果集
			                    searchHit.getSourceAsMap().put(field, sb.toString());
			                }
		            	}
		            }
	            }
            }
            sourceList.add(searchHit.getSourceAsMap());
        }
        return sourceList;
    }
    
}