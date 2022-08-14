package com.fakebilly.monet.business.domain.adapter;

import com.fakebilly.monet.business.domain.adapter.response.User;

/**
 * IUserServiceAdapter
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public interface IUserServiceAdapter {

    /**
     * 查询用户
     * @param userId
     * @return com.fakebilly.monet.business.domain.adapter.response.User
     **/
    User findUser(long userId);


}
