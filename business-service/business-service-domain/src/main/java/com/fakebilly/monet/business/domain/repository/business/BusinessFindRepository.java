package com.fakebilly.monet.business.domain.repository.business;

import com.fakebilly.monet.business.domain.model.condition.business.ListBusinessCondition;
import com.fakebilly.monet.business.domain.model.condition.business.PageBusinessCondition;
import com.fakebilly.monet.business.domain.model.entity.business.BusinessDO;
import com.fakebilly.monet.business.domain.model.page.Page;

import java.util.List;

/**
 * 查询
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public interface BusinessFindRepository {

    /**
     * 查询
     * @param id
     * @return com.fakebilly.monet.business.domain.model.entity.business.BusinessDO
     **/
    BusinessDO find(Long id);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param condition
     * @return com.fakebilly.monet.business.domain.model.page.Page<com.fakebilly.monet.business.domain.model.entity.business.BusinessDO>
     **/
    Page<BusinessDO> findPage(int pageNum, int pageSize, PageBusinessCondition condition);

    /**
     * 条件查询
     * @param condition
     * @return java.util.List<com.fakebilly.monet.business.domain.model.entity.business.BusinessDO>
     **/
    List<BusinessDO> findList(ListBusinessCondition condition);

}
