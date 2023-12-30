package com.fakebilly.monet.business.infrastructure.mapper;

import com.fakebilly.monet.business.domain.model.condition.business.ListBusinessCondition;
import com.fakebilly.monet.business.domain.model.condition.business.PageBusinessCondition;
import com.fakebilly.monet.business.infrastructure.entity.Business;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BusinessMapper
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public interface BusinessMapper {

    /**
     * 插入
     * @param business
     * @return int
     **/
    int insert(Business business);

    /**
     * 修改
     * @param business
     * @return int
     **/
    int update(Business business);

    /**
     * 查询
     * @param id
     * @return com.fakebilly.monet.business.infrastructure.entity.Business
     **/
    Business selectById(@Param("id") Long id);

    /**
     * 条件查询
     * @param condition
     * @return java.util.List<com.fakebilly.monet.business.infrastructure.entity.Business>
     **/
    List<Business> selectList(@Param("condition") ListBusinessCondition condition);

    /**
     * 分页查询
     * @param condition
     * @return java.util.List<com.fakebilly.monet.business.infrastructure.entity.Business>
     **/
    List<Business> selectPage(@Param("condition") PageBusinessCondition condition);

}