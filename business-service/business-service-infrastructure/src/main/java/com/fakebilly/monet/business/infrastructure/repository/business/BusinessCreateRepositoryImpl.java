package com.fakebilly.monet.business.infrastructure.repository.business;

import com.fakebilly.monet.business.domain.adapter.IdWorkerAdapter;
import com.fakebilly.monet.business.domain.enums.CodeEnum;
import com.fakebilly.monet.business.domain.model.command.business.CreateBusinessCommand;
import com.fakebilly.monet.business.domain.repository.business.BusinessCreateRepository;
import com.fakebilly.monet.business.domain.utils.Assert;
import com.fakebilly.monet.business.infrastructure.convert.BusinessInfraConvert;
import com.fakebilly.monet.business.infrastructure.entity.Business;
import com.fakebilly.monet.business.infrastructure.mapper.BusinessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 新增
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Repository
public class BusinessCreateRepositoryImpl implements BusinessCreateRepository {

    private static final Logger logger = LoggerFactory.getLogger(BusinessCreateRepositoryImpl.class);

    @Autowired
    private BusinessMapper businessMapper;
    @Autowired
    private IdWorkerAdapter idWorkerAdapter;

    @Override
    public long create(CreateBusinessCommand command) {
        Business business = BusinessInfraConvert.INSTANCE.toBusiness(command);
        long id = idWorkerAdapter.generateId();
        business.setId(id);
        int insert = businessMapper.insert(business);
        Assert.isTrue(insert == 1, CodeEnum.ERROR_BIZ_LOGIC, "业务创建失败!");
        return id;
    }
}
