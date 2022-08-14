package com.fakebilly.monet.es.service;

import com.fakebilly.monet.es.request.bulk.ESBulkRequest;
import com.fakebilly.monet.es.request.search.ESSearchRequest;
import com.fakebilly.monet.es.entity.ESBaseEntity;
import com.fakebilly.monet.es.model.ESData;
import com.fakebilly.monet.es.request.ESRequest;

import java.util.Map;

/**
 * IElasticSearchService
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public interface IElasticSearchService {

    /**
     * index
     * @param index
     * @param map
     * @return java.lang.String
     **/
    String index(String index, Map<String, Object> map);

    /**
     * index
     * @param index
     * @param map
     * @return java.lang.String
     **/
    boolean indexAsync(String index, Map<String, Object> map);

    /**
     * index
     * @param index
     * @param entity
     * @return java.lang.String
     **/
    String index(String index, ESBaseEntity entity);

    /**
     * index
     * @param index
     * @param entity
     * @return java.lang.String
     **/
    boolean indexAsync(String index, ESBaseEntity entity);

    /**
     * index
     * @param request
     * @return
     */
    String index(ESRequest request);

    /**
     * create
     * @param index
     * @param id
     * @param map
     * @return boolean
     **/
    boolean create(String index, String id, Map<String, Object> map);

    /**
     * create
     * @param index
     * @param id
     * @param map
     * @return boolean
     **/
    boolean createAsync(String index, String id, Map<String, Object> map);

    /**
     * create
     * @param index
     * @param id
     * @param entity
     * @return boolean
     **/
    boolean create(String index, String id, ESBaseEntity entity);

    /**
     * create
     * @param index
     * @param id
     * @param entity
     * @return boolean
     **/
    boolean createAsync(String index, String id, ESBaseEntity entity);

    /**
     * create
     * @param request
     * @return
     */
    boolean create(ESRequest request);

    /**
     * get
     * @param index
     * @param id
     * @param clazz
     * @return T
     **/
    <T extends ESBaseEntity> T getEntity(String index, String id, Class<T> clazz);

    /**
     * get
     * @param index
     * @param id
     * @param clazz
     * @return T
     **/
    <T> T get(String index, String id, Class<T> clazz);

    /**
     * delete
     * @param index
     * @param id
     * @return boolean
     **/
    boolean delete(String index, String id);

    /**
     * delete
     * @param index
     * @param id
     * @return boolean
     **/
    boolean deleteAsync(String index, String id);

    /**
     * delete
     * @param request
     * @return
     */
    boolean delete(ESRequest request);

    /**
     * update
     * @param index
     * @param id
     * @param entity
     * @return boolean
     **/
    boolean update(String index, String id, ESBaseEntity entity);

    /**
     * update
     * @param index
     * @param id
     * @param entity
     * @return boolean
     **/
    boolean updateAsync(String index, String id, ESBaseEntity entity);

    /**
     * update
     * @param request
     * @param clazz
     * @return
     */
    boolean update(ESRequest request, Class<?> clazz);

    /**
     * search
     * @param clazz
     * @param builder
     * @return com.fakebilly.monet.es.model.ESData<T>
     **/
    <T extends ESBaseEntity> ESData<T> searchEntity(Class<T> clazz, ESSearchRequest builder);

    /**
     * search
     * @param clazz
     * @param builder
     * @return com.fakebilly.monet.es.model.ESData<T>
     **/
    <T> ESData<T> search(Class<T> clazz, ESSearchRequest builder);

    /**
     * Bulk
     * @param builder
     * @return
     */
    boolean bulk(ESBulkRequest builder);

}
