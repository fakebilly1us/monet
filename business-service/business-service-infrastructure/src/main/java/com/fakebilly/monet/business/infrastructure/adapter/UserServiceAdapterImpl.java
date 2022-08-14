package com.fakebilly.monet.business.infrastructure.adapter;

import com.alibaba.fastjson2.JSON;
import com.fakebilly.monet.basic.api.dto.user.UserDTO;
import com.fakebilly.monet.basic.api.request.user.query.FindUserRequest;
import com.fakebilly.monet.business.domain.adapter.IUserServiceAdapter;
import com.fakebilly.monet.business.domain.adapter.response.User;
import com.fakebilly.monet.business.infrastructure.config.rpc.DubboReferenceConfig;
import com.fakebilly.monet.core.dto.Response;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * IUserServiceAdapter.UserServiceAdapterImpl
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Component
public class UserServiceAdapterImpl implements IUserServiceAdapter {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceAdapterImpl.class);

    @Autowired
    private DubboReferenceConfig dubboReferenceConfig;

    @Override
    public User findUser(long userId) {
        logger.info("查询用户,Start,userId:{}", userId);
        FindUserRequest request = new FindUserRequest();
        request.setId(userId);
        Response<UserDTO> response = dubboReferenceConfig.getUserQueryService().findUser(request);
        logger.info("查询用户,End,userId:{},response:{}", userId, JSON.toJSONString(response));
        if (null == response || !response.isSuccess() || null == response.getData()) {
            return null;
        }
        return UserConvert.INSTANCE.toUser(response.getData());
    }


    @Mapper
    interface UserConvert {

        UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

        /**
         * UserDTO -> User
         * @param userDTO
         * @return com.fakebilly.monet.business.domain.adapter.response.User
         **/
        User toUser(UserDTO userDTO);
    }


}
