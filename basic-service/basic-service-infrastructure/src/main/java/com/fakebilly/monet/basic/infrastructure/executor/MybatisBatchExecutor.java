package com.fakebilly.monet.basic.infrastructure.executor;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiConsumer;

import static org.mybatis.spring.SqlSessionUtils.closeSqlSession;


/**
 * MybatisBatchExecutor
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Component
public class MybatisBatchExecutor {

    private static final Logger logger = LoggerFactory.getLogger(MybatisBatchExecutor.class);

    /**
     * BATCH_COUNT
     */
    private static final int BATCH_COUNT = 100;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public <T, M> boolean insertOrUpdateBatch(List<T> dbList, Class<M> mapperClass, BiConsumer<M, List<T>> func) {
        return insertOrUpdateBatch(dbList, mapperClass, func, BATCH_COUNT);
    }

    public <T, M> boolean insertOrUpdateBatch(List<T> dbList, Class<M> mapperClass, BiConsumer<M, List<T>> func, int batchCount) {
        if (CollectionUtil.isEmpty(dbList)) {
            return true;
        }
        int lastIndex = batchCount;
        int total = dbList.size();
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        M modelMapper = sqlSession.getMapper(mapperClass);
        try {
            if (total > batchCount) {
                for (int index = 0; index <= total; ) {
                    List<T> list = null;
                    if (lastIndex >= total) {
                        list = CollectionUtil.sub(dbList, index, total);
                    } else {
                        list = CollectionUtil.sub(dbList, index, lastIndex);
                    }
                    if (CollectionUtil.isEmpty(list)) {
                        continue;
                    }
                    func.accept(modelMapper, list);
                    sqlSession.flushStatements();
                    sqlSession.commit();

                    index = lastIndex;
                    lastIndex = index + batchCount;
                }
            } else {
                func.accept(modelMapper, dbList);
                sqlSession.flushStatements();
                sqlSession.commit();
            }
            return true;
        } catch (Exception e) {
            logger.error("mybatis 批量失败,e:{}", e);
            sqlSession.rollback(true);
            return false;
        } finally {
            closeSqlSession(sqlSession, sqlSessionFactory);
        }
    }
}

