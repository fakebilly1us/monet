package com.fakebilly.monet.es.convert;

import cn.hutool.core.collection.CollectionUtil;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import com.fakebilly.monet.es.entity.ESBaseEntity;
import com.fakebilly.monet.es.model.ESData;
import com.fakebilly.monet.es.model.ESDocument;

import java.util.ArrayList;
import java.util.List;

/**
 * ElasticSearchConvert
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ElasticSearchConvert {

    /**
     * HitsMetadata<T> -> <T> ESData<T>
     * @param hits
     * @param <T>
     * @return
     */
    public static <T> ESData<T> toESData(HitsMetadata<T> hits) {
        if (null == hits) {
            return null;
        }
        ESData<T> esData = new ESData<>();
        TotalHits total = hits.total();
        if (null != total) {
            esData.setTotal(total.value());
        }
        esData.setMaxScore(hits.maxScore());
        List<Hit<T>> hitList = hits.hits();
        if (CollectionUtil.isNotEmpty(hitList)) {
            esData.setDataList(toESDocumentList(hitList));
        }
        return esData;
    }

    /**
     * Hit<T> -> <T> ESDocument<T>
     * @param hit
     * @param <T>
     * @return
     */
    public static <T> ESDocument<T> toESDocument(Hit<T> hit) {
        if (null == hit) {
            return null;
        }
        ESDocument<T> esDocument = new ESDocument<>();
        esDocument.setData(hit.source());
        esDocument.setIndex(hit.index());
        esDocument.setId(hit.id());
        esDocument.setScore(hit.score());
        esDocument.setVersion(hit.version());
        return esDocument;
    }

    /**
     * List<Hit<T>> -> <T> List<ESDocument<T>>
     * @param hitList
     * @param <T>
     * @return
     */
    public static <T> List<ESDocument<T>> toESDocumentList(List<Hit<T>> hitList) {
        if (CollectionUtil.isEmpty(hitList)) {
            return null;
        }
        List<ESDocument<T>> esDocumentList = new ArrayList<>(hitList.size());
        hitList.forEach(h -> esDocumentList.add(toESDocument(h)));
        return esDocumentList;
    }

    /**
     * HitsMetadata<T> -> <T extends ESBaseEntity> ESData<T>
     * @param hits
     * @return com.fakebilly.monet.es.model.ESData<T>
     **/
    public static <T extends ESBaseEntity> ESData<T> toESDataEntity(HitsMetadata<T> hits) {
        if (null == hits) {
            return null;
        }
        ESData<T> esData = new ESData<>();
        TotalHits total = hits.total();
        if (null != total) {
            esData.setTotal(total.value());
        }
        esData.setMaxScore(hits.maxScore());
        List<Hit<T>> hitList = hits.hits();
        if (CollectionUtil.isNotEmpty(hitList)) {
            esData.setDataList(toESDocumentEntityList(hitList));
        }
        return esData;
    }

    /**
     * Hit<T> -> <T extends ESBaseEntity> ESDocument<T>
     * @param hit
     * @return com.fakebilly.monet.es.model.ESDocument<T>
     **/
    public static <T extends ESBaseEntity> ESDocument<T> toESDocumentEntity(Hit<T> hit) {
        if (null == hit) {
            return null;
        }
        ESDocument<T> esDocument = new ESDocument<>();
        esDocument.setData(hit.source());
        esDocument.setIndex(hit.index());
        esDocument.setId(hit.id());
        esDocument.setScore(hit.score());
        esDocument.setVersion(hit.version());
        return esDocument;
    }

    /**
     * List<Hit<T>> -> <T extends ESBaseEntity> List<ESDocument<T>>
     * @param hitList
     * @return java.util.List<com.fakebilly.monet.es.model.ESDocument < T>>
     **/
    public static <T extends ESBaseEntity> List<ESDocument<T>> toESDocumentEntityList(List<Hit<T>> hitList) {
        if (CollectionUtil.isEmpty(hitList)) {
            return null;
        }
        List<ESDocument<T>> esDocumentList = new ArrayList<>(hitList.size());
        hitList.forEach(h -> esDocumentList.add(toESDocumentEntity(h)));
        return esDocumentList;
    }


}
