package com.fakebilly.monet.es.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.alibaba.fastjson2.JSON;
import com.fakebilly.monet.es.request.bulk.ESBulkRequest;
import com.fakebilly.monet.es.request.search.ESSearchRequest;
import com.fakebilly.monet.es.convert.ElasticSearchConvert;
import com.fakebilly.monet.es.entity.ESBaseEntity;
import com.fakebilly.monet.es.exception.ElasticSearchException;
import com.fakebilly.monet.es.model.ESData;
import com.fakebilly.monet.es.request.*;
import com.fakebilly.monet.es.service.IElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

/**
 * ESDocumentService
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ElasticSearchServiceImpl implements IElasticSearchService {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

    @Autowired
    @Qualifier(value = "ESClient")
    private ElasticsearchClient elasticsearchClient;

    @Override
    public String index(String index, Map<String, Object> map) {
        IndexRequest<Map<String, Object>> request = IndexRequest.of(e ->
                e.index(index)
                        .document(map)
                        .refresh(Refresh.True)
        );
        try {
            IndexResponse response = elasticsearchClient.index(request);
            if (null == response || response.result() != Result.Created) {
                return null;
            }
            return response.id();
        } catch (Exception e) {
            logger.error("index exception, index:{}, map:{}, e:{}", index, JSON.toJSONString(map), e);
        }
        return null;
    }

    @Override
    public boolean indexAsync(String index, Map<String, Object> map) {
        IndexRequest<Map<String, Object>> request = IndexRequest.of(e ->
                e.index(index)
                        .document(map)
                        .refresh(Refresh.False)
        );
        try {
            IndexResponse response = elasticsearchClient.index(request);
            if (null == response) {
                return false;
            }
        } catch (Exception e) {
            logger.error("indexAsync exception, index:{}, map:{}, e:{}", index, JSON.toJSONString(map), e);
            return false;
        }
        return true;
    }

    @Override
    public String index(String index, ESBaseEntity entity) {
        IndexRequest<ESBaseEntity> request = IndexRequest.of(e ->
                e.index(index)
                        .document(entity)
                        .refresh(Refresh.True)
        );
        try {
            IndexResponse response = elasticsearchClient.index(request);
            if (null == response || response.result() != Result.Created) {
                return null;
            }
            return response.id();
        } catch (Exception e) {
            logger.error("index exception, index:{}, entity:{}, e:{}", index, JSON.toJSONString(entity), e);
        }
        return null;
    }

    @Override
    public boolean indexAsync(String index, ESBaseEntity entity) {
        IndexRequest<ESBaseEntity> request = IndexRequest.of(e ->
                e.index(index)
                        .document(entity)
                        .refresh(Refresh.False)
        );
        try {
            IndexResponse response = elasticsearchClient.index(request);
            if (null == response) {
                return false;
            }
        } catch (Exception e) {
            logger.error("indexAsync exception, index:{}, entity:{}, e:{}", index, JSON.toJSONString(entity), e);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String index(ESRequest request) {
        if (!(request instanceof ESIndexRequest)) {
            return null;
        }
        IndexRequest indexRequest = ((ESIndexRequest) request).toRequest();
        try {
            IndexResponse response = elasticsearchClient.index(indexRequest);
            if (null == response) {
                return null;
            }
            Boolean refresh = ((ESIndexRequest) request).getRefresh();
            if (null != refresh && refresh) {
                if (response.result() != Result.Created) {
                    return null;
                }
            }
            return response.id();
        } catch (Exception e) {
            logger.error("index exception, request:{}, e:{}", JSON.toJSONString(request), e);
        }
        return null;
    }

    @Override
    public boolean create(String index, String id, Map<String, Object> map) {
        CreateRequest<Map<String, Object>> request = CreateRequest.of(e ->
                e.index(index)
                        .id(id)
                        .document(map)
                        .refresh(Refresh.True)
        );
        try {
            CreateResponse response = elasticsearchClient.create(request);
            if (null == response || response.result() != Result.Created) {
                return false;
            }
        } catch (Exception e) {
            logger.error("create exception, index:{}, map:{}, e:{}", index, JSON.toJSONString(map), e);
            return false;
        }
        return true;
    }

    @Override
    public boolean createAsync(String index, String id, Map<String, Object> map) {
        CreateRequest<Map<String, Object>> request = CreateRequest.of(e ->
                e.index(index)
                        .id(id)
                        .document(map)
                        .refresh(Refresh.False)
        );
        try {
            CreateResponse response = elasticsearchClient.create(request);
            if (null == response) {
                return false;
            }
        } catch (Exception e) {
            logger.error("createAsync exception, index:{}, map:{}, e:{}", index, JSON.toJSONString(map), e);
            return false;
        }
        return true;
    }

    @Override
    public boolean create(String index, String id, ESBaseEntity entity) {
        CreateRequest<ESBaseEntity> request = CreateRequest.of(e ->
                e.index(index)
                        .id(id)
                        .document(entity)
                        .refresh(Refresh.True)
        );
        try {
            CreateResponse response = elasticsearchClient.create(request);
            if (null == response || response.result() != Result.Created) {
                return false;
            }
        } catch (Exception e) {
            logger.error("create exception, index:{}, id:{}, entity:{}, e:{}", index, id, JSON.toJSONString(entity), e);
            return false;
        }
        return true;
    }

    @Override
    public boolean createAsync(String index, String id, ESBaseEntity entity) {
        CreateRequest<ESBaseEntity> request = CreateRequest.of(e ->
                e.index(index)
                        .id(id)
                        .document(entity)
                        .refresh(Refresh.False)
        );
        try {
            CreateResponse response = elasticsearchClient.create(request);
            if (null == response) {
                return false;
            }
        } catch (Exception e) {
            logger.error("createAsync exception, index:{}, id:{}, entity:{}, e:{}", index, id, JSON.toJSONString(entity), e);
            return false;
        }
        return true;
    }

    @Override
    public boolean create(ESRequest request) {
        if (!(request instanceof ESCreateRequest)) {
            return false;
        }
        CreateRequest createRequest = ((ESCreateRequest) request).toRequest();
        try {
            CreateResponse response = elasticsearchClient.create(createRequest);
            if (null == response) {
                return false;
            }
            Boolean refresh = ((ESCreateRequest) request).getRefresh();
            if (null != refresh && refresh) {
                if (response.result() != Result.Created) {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("create exception, request:{}, e:{}", JSON.toJSONString(request), e);
            return false;
        }
        return true;
    }

    @Override
    public <T extends ESBaseEntity> T getEntity(String index, String id, Class<T> clazz) {
        GetRequest request = GetRequest.of(e ->
                e.index(index)
                        .id(id)
        );
        try {
            GetResponse<T> response = elasticsearchClient.get(request, clazz);
            if (null == response || !response.found()) {
                return null;
            }
            return response.source();
        } catch (Exception e) {
            logger.error("getEntity exception, index:{}, id:{}, clazz:{}, e:{}", index, id, clazz.getName(), e);
        }
        return null;
    }

    @Override
    public <T> T get(String index, String id, Class<T> clazz) {
        GetRequest request = GetRequest.of(e ->
                e.index(index)
                        .id(id)
        );
        try {
            GetResponse<T> response = elasticsearchClient.get(request, clazz);
            if (null == response || !response.found()) {
                return null;
            }
            return response.source();
        } catch (Exception e) {
            logger.error("get exception, index:{}, id:{}, clazz:{}, e:{}", index, id, clazz.getName(), e);
        }
        return null;
    }

    @Override
    public boolean delete(String index, String id) {
        DeleteRequest request = DeleteRequest.of(e ->
                e.index(index)
                        .id(id)
                        .refresh(Refresh.True)
        );
        try {
            DeleteResponse response = elasticsearchClient.delete(request);
            if (null == response || response.result() != Result.Deleted) {
                return false;
            }
        } catch (Exception e) {
            logger.error("delete exception, index:{}, id:{}, e:{}", index, id, e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAsync(String index, String id) {
        DeleteRequest request = DeleteRequest.of(e ->
                e.index(index)
                        .id(id)
                        .refresh(Refresh.False)
        );
        try {
            DeleteResponse response = elasticsearchClient.delete(request);
            if (null == response) {
                return false;
            }
        } catch (Exception e) {
            logger.error("deleteAsync exception, index:{}, id:{}, e:{}", index, id, e);
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(ESRequest request) {
        if (!(request instanceof ESDeleteRequest)) {
            return false;
        }
        DeleteRequest deleteRequest = ((ESDeleteRequest) request).toRequest();
        try {
            DeleteResponse response = elasticsearchClient.delete(deleteRequest);
            if (null == response) {
                return false;
            }
            Boolean refresh = ((ESDeleteRequest) request).getRefresh();
            if (null != refresh && refresh) {
                if (response.result() != Result.Deleted) {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("delete exception, request:{}, e:{}", JSON.toJSONString(request), e);
            return false;
        }
        return true;
    }

    @Override
    public boolean update(String index, String id, ESBaseEntity entity) {
        UpdateRequest<ESBaseEntity, ESBaseEntity> request = UpdateRequest.of(e ->
                e.index(index)
                        .id(id)
                        .doc(entity)
                        .refresh(Refresh.True)
        );
        try {
            UpdateResponse<ESBaseEntity> response = elasticsearchClient.update(request, ESBaseEntity.class);
            if (null == response || response.result() != Result.Updated) {
                return false;
            }
        } catch (Exception e) {
            logger.error("update exception, index:{}, id:{}, e:{}", index, id, e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateAsync(String index, String id, ESBaseEntity entity) {
        UpdateRequest<ESBaseEntity, ESBaseEntity> request = UpdateRequest.of(e ->
                e.index(index)
                        .id(id)
                        .doc(entity)
                        .refresh(Refresh.False)
        );
        try {
            UpdateResponse<ESBaseEntity> response = elasticsearchClient.update(request, ESBaseEntity.class);
            if (null == response) {
                return false;
            }
        } catch (Exception e) {
            logger.error("updateAsync exception, index:{}, id:{}, e:{}", index, id, e);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean update(ESRequest request, Class<?> clazz) {
        if (!(request instanceof ESUpdateRequest)) {
            return false;
        }
        UpdateRequest updateRequest = ((ESUpdateRequest) request).toRequest();
        try {
            UpdateResponse response = elasticsearchClient.update(updateRequest, clazz);
            if (null == response) {
                return false;
            }
            Boolean refresh = ((ESUpdateRequest) request).getRefresh();
            if (null != refresh && refresh) {
                if (response.result() != Result.Updated) {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("update exception, request:{}, e:{}", JSON.toJSONString(request), e);
            return false;
        }
        return true;
    }

    @Override
    public <T extends ESBaseEntity> ESData<T> searchEntity(Class<T> clazz, ESSearchRequest builder) {
        SearchRequest request = builder.toRequest();
        if (null == request) {
            throw new ElasticSearchException("build request is null");
        }
        try {
            logger.info("searchEntity, builder:{}, request:{}", JSON.toJSONString(builder), JSON.toJSONString(request));
            SearchResponse<T> response = elasticsearchClient.search(request, clazz);
            if (null == response || null == response.hits()) {
                return null;
            }
            HitsMetadata<T> hits = response.hits();
            return ElasticSearchConvert.toESDataEntity(hits);
        } catch (Exception e) {
            logger.error("searchEntity exception, clazz:{}, builder:{}, e:{}", clazz.getName(), JSON.toJSONString(builder), e);
        }
        return null;
    }

    @Override
    public <T> ESData<T> search(Class<T> clazz, ESSearchRequest builder) {
        SearchRequest request = builder.toRequest();
        if (null == request) {
            throw new ElasticSearchException("build request is null");
        }
        try {
            logger.info("search, builder:{}, request:{}", JSON.toJSONString(builder), JSON.toJSONString(request));
            SearchResponse<T> response = elasticsearchClient.search(request, clazz);
            if (null == response || null == response.hits()) {
                return null;
            }
            HitsMetadata<T> hits = response.hits();
            return ElasticSearchConvert.toESData(hits);
        } catch (Exception e) {
            logger.error("search exception, clazz:{}, builder:{}, e:{}", clazz.getName(), JSON.toJSONString(builder), e);
        }
        return null;
    }

    @Override
    public boolean bulk(ESBulkRequest builder) {
        BulkRequest request = builder.toRequest();
        if (null == request) {
            throw new ElasticSearchException("构造请求为空!");
        }
        try {
            logger.info("bulk, builder:{}, request:{}", JSON.toJSONString(builder), request);
            BulkResponse response = elasticsearchClient.bulk(request);
            if (null == response || response.errors()) {
                return false;
            }
        } catch (Exception e) {
            logger.error("bulk exception, builder:{}, e:{}", JSON.toJSONString(builder), e);
            return false;
        }
        return true;
    }


}
