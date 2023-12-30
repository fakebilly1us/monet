package com.fakebilly.monet.basic.domain.repository.user;

import com.fakebilly.monet.basic.domain.model.command.user.CreateUserCommand;

/**
 * 新增
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public interface UserCreateRepository {

    /**
     * 新增
     * @param command
     * @return long
     **/
    long create(CreateUserCommand command);

}
