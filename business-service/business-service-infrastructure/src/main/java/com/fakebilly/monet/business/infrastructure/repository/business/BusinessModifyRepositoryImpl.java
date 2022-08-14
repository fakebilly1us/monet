package com.fakebilly.monet.business.infrastructure.repository.business;

import com.fakebilly.monet.business.domain.enums.CodeEnum;
import com.fakebilly.monet.business.domain.model.command.business.ModifyBusinessCommand;
import com.fakebilly.monet.business.domain.repository.business.BusinessModifyRepository;
import com.fakebilly.monet.business.domain.utils.Assert;
import com.fakebilly.monet.business.infrastructure.convert.BusinessInfraConvert;
import com.fakebilly.monet.business.infrastructure.entity.Business;
import com.fakebilly.monet.business.infrastructure.mapper.BusinessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 修改
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Repository
public class BusinessModifyRepositoryImpl implements BusinessModifyRepository {

    private static final Logger logger = LoggerFactory.getLogger(BusinessModifyRepositoryImpl.class);

    @Autowired
    private BusinessMapper businessMapper;

    @Override
    public boolean modify(ModifyBusinessCommand command) {
        Business business = BusinessInfraConvert.INSTANCE.toBusiness(command);
        int update = businessMapper.update(business);
        Assert.isTrue(update == 1, CodeEnum.ERROR_BIZ_LOGIC, "业务修改失败!");
        return true;
    }
}
