package com.fakebilly.monet.basic.server.convert;

import com.fakebilly.monet.basic.domain.model.entity.user.UserDO;
import com.fakebilly.monet.basic.server.vo.user.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * UserServerConvert
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Mapper(componentModel = "spring")
public interface UserServerConvert {

    UserServerConvert INSTANCE = Mappers.getMapper(UserServerConvert.class);

    /**
     * UserDO -> UserVO
     * @param userDO
     * @return com.fakebilly.monet.basic.server.vo.user.UserVO
     **/
    UserVO toUserVO(UserDO userDO);

}
