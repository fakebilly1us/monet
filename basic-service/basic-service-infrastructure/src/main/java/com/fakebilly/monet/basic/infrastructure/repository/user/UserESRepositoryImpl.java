package com.fakebilly.monet.basic.infrastructure.repository.user;

import cn.hutool.core.collection.CollectionUtil;
import com.fakebilly.monet.basic.domain.config.nacos.ESIndexConfig;
import com.fakebilly.monet.basic.domain.model.command.user.es.CreateUserESCommand;
import com.fakebilly.monet.basic.domain.model.command.user.es.ModifyUserESCommand;
import com.fakebilly.monet.basic.domain.model.entity.user.UserDO;
import com.fakebilly.monet.basic.domain.repository.user.UserESRepository;
import com.fakebilly.monet.basic.infrastructure.convert.UserESInfraConvert;
import com.fakebilly.monet.basic.infrastructure.entity.es.ESUserEntity;
import com.fakebilly.monet.es.request.bulk.ESBulkRequest;
import com.fakebilly.monet.es.request.search.ESSearchRequest;
import com.fakebilly.monet.es.enums.FieldClassEnum;
import com.fakebilly.monet.es.model.ESData;
import com.fakebilly.monet.es.model.ESDocument;
import com.fakebilly.monet.es.operation.*;
import com.fakebilly.monet.es.query.ESTermQuery;
import com.fakebilly.monet.es.service.IElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserESRepository.UserESRepositoryImpl
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Repository
public class UserESRepositoryImpl implements UserESRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserESRepositoryImpl.class);

    @Autowired
    private IElasticSearchService elasticSearchService;

    @Autowired
    private ESIndexConfig esIndexConfig;

    @Override
    public UserDO get(long userId) {
        ESUserEntity user = elasticSearchService.getEntity(esIndexConfig.getUser(), String.valueOf(userId), ESUserEntity.class);
        return UserESInfraConvert.INSTANCE.toUserDO(user);
    }

    @Override
    public boolean create(CreateUserESCommand command) {
        ESUserEntity entity = UserESInfraConvert.INSTANCE.toESUserEntity(command);
        return elasticSearchService.create(esIndexConfig.getUser(), String.valueOf(entity.getId()), entity);
    }

    @Override
    public boolean delete(long userId) {
        return elasticSearchService.delete(esIndexConfig.getUser(), String.valueOf(userId));
    }

    @Override
    public boolean update(ModifyUserESCommand command) {
        ESUserEntity entity = UserESInfraConvert.INSTANCE.toESUserEntity(command);
        return elasticSearchService.update(esIndexConfig.getUser(), String.valueOf(entity.getId()), entity);
    }

    @Override
    public List<UserDO> searchDemo() {
        ESTermQuery query = ESTermQuery.create().field("user_name").fieldClassEnum(FieldClassEnum.STRING_CLASS).value("Snow").build();
        ESSearchRequest builder = ESSearchRequest.create()
                .index(esIndexConfig.getUser())
                .from(0)
                .size(10)
                .esQuery(query);
        ESData<ESUserEntity> search = elasticSearchService.search(ESUserEntity.class, builder);
        if (null == search || CollectionUtil.isEmpty(search.getDataList())) {
            return null;
        }
        List<ESDocument<ESUserEntity>> dataList = search.getDataList();
        UserESInfraConvert convert = UserESInfraConvert.INSTANCE;
        List<UserDO> list = new ArrayList<>();
        for (ESDocument<ESUserEntity> document : dataList) {
            ESUserEntity data = document.getData();
            list.add(convert.toUserDO(data));
        }
        return list;
    }

    @Override
    public boolean bulkDemo() {
        ESUserEntity user1 = new ESUserEntity();
        user1.setId(22L);
        user1.setUser_name("Snow");
        ESUserEntity user2 = new ESUserEntity();
        user2.setId(18L);
        user2.setUser_name("Snow");
        ESUserEntity user3 = new ESUserEntity();
        user3.setId(19L);
        user3.setUser_name("Snow");

        ESUserEntity user4 = new ESUserEntity();
        user4.setId(20L);
        user4.setUser_name("Snow");

        ESCreateOperation<ESUserEntity> c1 = new ESCreateOperation<>();
        c1.setDocument(user1);
        c1.setIndex(esIndexConfig.getUser());
        c1.setId("22");

        ESCreateOperation<ESUserEntity> c2 = new ESCreateOperation<>();
        c2.setDocument(user2);
        c2.setIndex(esIndexConfig.getUser());
        c2.setId("18");

        ESCreateOperation<ESUserEntity> c3 = new ESCreateOperation<>();
        c3.setDocument(user3);
        c3.setIndex(esIndexConfig.getUser());
        c3.setId("19");

        ESIndexOperation<ESUserEntity> i4 = new ESIndexOperation<>();
        i4.setDocument(user4);
        i4.setIndex(esIndexConfig.getUser());

        ESDeleteOperation d1 = ESDeleteOperation.create().index(esIndexConfig.getUser()).id("17").build();
        ESDeleteOperation d2 = ESDeleteOperation.create().index(esIndexConfig.getUser()).id("18").build();

//        ESUpdateOperation<ESUserEntity> u1 = ESUpdateOperation.create(user3).index(esIndexConfig.getUser()).id("19").document(user3);

        Map<String, ESUserEntity> map2 = new HashMap<>();
        map2.put(ESUpdateOperation.DOCUMENT_KEY, user3);
        ESUpdateOperation<Map<String, ESUserEntity>> u2 = ESUpdateOperation.create(map2).index(esIndexConfig.getUser()).id("19").document(map2);

        List<ESBulkOperation> list = new ArrayList<>();
//        list.add(c1);
//        list.add(c2);
//        list.add(c3);

//        list.add(d1);
//        list.add(d2);

//        list.add(u1);
        list.add(u2);

//        list.add(i4);

        ESBulkRequest builder = ESBulkRequest.create()
                .index(esIndexConfig.getUser())
                .refresh(Boolean.TRUE)
                .operations(list);
        boolean bulk = elasticSearchService.bulk(builder);
        return true;
    }


}
