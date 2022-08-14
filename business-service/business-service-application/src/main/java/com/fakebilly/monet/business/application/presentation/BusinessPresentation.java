package com.fakebilly.monet.business.application.presentation;

import com.alibaba.fastjson2.JSON;
import com.fakebilly.monet.business.domain.adapter.IUserServiceAdapter;
import com.fakebilly.monet.business.domain.adapter.response.User;
import com.fakebilly.monet.business.domain.model.entity.business.BusinessDO;
import com.fakebilly.monet.business.domain.repository.business.BusinessFindRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BusinessPresentation
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Service
public class BusinessPresentation {

    private static final Logger logger = LoggerFactory.getLogger(BusinessPresentation.class);

    @Autowired
    private BusinessFindRepository businessFindRepository;

    @Autowired
    private IUserServiceAdapter userServiceAdapter;

    /**
     * 查询业务
     * @param id
     * @return com.fakebilly.monet.business.domain.model.entity.business.BusinessDO
     **/
    public BusinessDO findBusiness(Long id) {
        logger.info("查询业务,Start,id:{}", id);
        BusinessDO businessDO = businessFindRepository.find(id);
        Long userId = id;
        User user = userServiceAdapter.findUser(userId);
        logger.info("查询业务,查询用户,End,id:{},user:{}", id, JSON.toJSONString(user));
        return businessDO;
    }
}
