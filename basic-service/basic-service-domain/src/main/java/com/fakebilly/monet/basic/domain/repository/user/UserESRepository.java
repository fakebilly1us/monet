package com.fakebilly.monet.basic.domain.repository.user;

import com.fakebilly.monet.basic.domain.model.command.user.es.CreateUserESCommand;
import com.fakebilly.monet.basic.domain.model.command.user.es.ModifyUserESCommand;
import com.fakebilly.monet.basic.domain.model.entity.user.UserDO;

import java.util.List;

/**
 * UserESRepository
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public interface UserESRepository {

    /**
     * get
     * @param userId
     * @return com.fakebilly.monet.basic.domain.model.entity.user.UserDO
     **/
    UserDO get(long userId);

    /**
     * create
     * @param command
     * @return boolean
     **/
    boolean create(CreateUserESCommand command);

    /**
     * delete
     * @param userId
     * @return boolean
     **/
    boolean delete(long userId);

    /**
     * update
     * @param command
     * @return boolean
     **/
    boolean update(ModifyUserESCommand command);

    /**
     * searchDemo
     * @param
     * @return java.util.List<com.fakebilly.monet.basic.domain.model.entity.user.UserDO>
     **/
    List<UserDO> searchDemo();

    /**
     * bulkDemo
     * @param
     * @return boolean
     **/
    boolean bulkDemo();

}
