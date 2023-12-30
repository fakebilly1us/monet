package com.fakebilly.monet.basic.application.convert;

import com.fakebilly.monet.basic.domain.model.command.user.es.CreateUserESCommand;
import com.fakebilly.monet.basic.domain.model.command.user.es.ModifyUserESCommand;
import com.fakebilly.monet.basic.domain.model.entity.user.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * UserAppConvert
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Mapper(componentModel = "spring")
public interface UserESAppConvert {

    UserESAppConvert INSTANCE = Mappers.getMapper(UserESAppConvert.class);

    /**
     * UserDO -> CreateUserESCommand
     * @param userDO
     * @return com.fakebilly.monet.basic.domain.model.command.user.es.CreateUserESCommand
     **/
    CreateUserESCommand toCreateUserESCommand(UserDO userDO);

    /**
     * UserDO -> ModifyUserESCommand
     * @param userDO
     * @return com.fakebilly.monet.basic.domain.model.command.user.es.ModifyUserESCommand
     **/
    ModifyUserESCommand toModifyUserESCommand(UserDO userDO);

}
