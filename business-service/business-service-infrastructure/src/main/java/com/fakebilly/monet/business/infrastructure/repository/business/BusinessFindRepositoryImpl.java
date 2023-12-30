package com.fakebilly.monet.business.infrastructure.repository.business;

import com.fakebilly.monet.business.domain.model.condition.business.ListBusinessCondition;
import com.fakebilly.monet.business.domain.model.condition.business.PageBusinessCondition;
import com.fakebilly.monet.business.domain.model.entity.business.BusinessDO;
import com.fakebilly.monet.business.domain.model.page.Page;
import com.fakebilly.monet.business.domain.repository.business.BusinessFindRepository;
import com.fakebilly.monet.business.infrastructure.convert.BusinessInfraConvert;
import com.fakebilly.monet.business.infrastructure.entity.Business;
import com.fakebilly.monet.business.infrastructure.mapper.BusinessMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 查询
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Repository
public class BusinessFindRepositoryImpl implements BusinessFindRepository {

    private static final Logger logger = LoggerFactory.getLogger(BusinessFindRepositoryImpl.class);

    @Autowired
    private BusinessMapper businessMapper;

    @Override
    public BusinessDO find(Long id) {
        return BusinessInfraConvert.INSTANCE.toBusinessDO(businessMapper.selectById(id));
    }

    @Override
    public Page<BusinessDO> findPage(int pageNum, int pageSize, PageBusinessCondition condition) {
        PageHelper.startPage(pageNum, pageSize);
        List<Business> businessList = businessMapper.selectPage(condition);
        PageInfo<Business> page = new PageInfo<>(businessList);
        return BusinessInfraConvert.INSTANCE.toPageBusinessDO(page);
    }

    @Override
    public List<BusinessDO> findList(ListBusinessCondition condition) {
        return BusinessInfraConvert.INSTANCE.toBusinessDOList(businessMapper.selectList(condition));
    }
}
