package com.fakebilly.monet.basic.application.convert;

import com.fakebilly.monet.basic.api.dto.user.UserDTO;
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
public interface UserAppConvert {

    UserAppConvert INSTANCE = Mappers.getMapper(UserAppConvert.class);

    /**
     * UserDO -> UserDTO
     * @param userDO
     * @return com.fakebilly.monet.basic.api.dto.user.UserDTO
     **/
    UserDTO toUserDTO(UserDO userDO);

}
