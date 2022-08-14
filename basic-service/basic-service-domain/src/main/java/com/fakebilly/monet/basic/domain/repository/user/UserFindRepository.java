package com.fakebilly.monet.basic.domain.repository.user;

import com.fakebilly.monet.basic.domain.model.condition.user.ListUserCondition;
import com.fakebilly.monet.basic.domain.model.condition.user.PageUserCondition;
import com.fakebilly.monet.basic.domain.model.entity.user.UserDO;
import com.fakebilly.monet.basic.domain.model.page.Page;

import java.util.List;

/**
 * 查询
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public interface UserFindRepository {

    /**
     * 查询
     * @param id
     * @return com.fakebilly.monet.basic.domain.model.entity.user.UserDO
     **/
    UserDO find(Long id);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param condition
     * @return com.fakebilly.monet.basic.domain.model.page.Page<com.fakebilly.monet.basic.domain.model.entity.user.UserDO>
     **/
    Page<UserDO> findPage(int pageNum, int pageSize, PageUserCondition condition);

    /**
     * 条件查询
     * @param condition
     * @return java.util.List<com.fakebilly.monet.basic.domain.model.entity.user.UserDO>
     **/
    List<UserDO> findList(ListUserCondition condition);

}
