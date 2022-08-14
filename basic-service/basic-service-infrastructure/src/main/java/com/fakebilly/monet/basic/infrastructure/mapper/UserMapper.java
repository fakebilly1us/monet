package com.fakebilly.monet.basic.infrastructure.mapper;

import com.fakebilly.monet.basic.domain.model.condition.user.ListUserCondition;
import com.fakebilly.monet.basic.domain.model.condition.user.PageUserCondition;
import com.fakebilly.monet.basic.infrastructure.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserMapper
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public interface UserMapper {

    /**
     * 插入
     * @param user
     * @return int
     **/
    int insert(User user);

    /**
     * 修改
     * @param user
     * @return int
     **/
    int update(User user);

    /**
     * 查询
     * @param id
     * @return com.fakebilly.monet.basic.infrastructure.entity.User
     **/
    User selectById(@Param("id") Long id);

    /**
     * 条件查询
     * @param condition
     * @return java.util.List<com.fakebilly.monet.basic.infrastructure.entity.User>
     **/
    List<User> selectList(@Param("condition") ListUserCondition condition);

    /**
     * 分页查询
     * @param condition
     * @return java.util.List<com.fakebilly.monet.basic.infrastructure.entity.User>
     **/
    List<User> selectPage(@Param("condition") PageUserCondition condition);
}