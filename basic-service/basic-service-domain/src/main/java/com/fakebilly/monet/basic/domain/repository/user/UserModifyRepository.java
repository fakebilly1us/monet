package com.fakebilly.monet.basic.domain.repository.user;

import com.fakebilly.monet.basic.domain.model.command.user.ModifyUserCommand;

/**
 * 修改
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public interface UserModifyRepository {

    /**
     * 修改
     * @param command
     * @return boolean
     **/
    boolean modify(ModifyUserCommand command);


}
