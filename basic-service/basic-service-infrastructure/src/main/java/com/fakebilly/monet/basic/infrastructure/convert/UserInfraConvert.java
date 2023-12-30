package com.fakebilly.monet.basic.infrastructure.convert;

import com.fakebilly.monet.basic.domain.model.command.user.CreateUserCommand;
import com.fakebilly.monet.basic.domain.model.command.user.ModifyUserCommand;
import com.fakebilly.monet.basic.domain.model.entity.user.UserDO;
import com.fakebilly.monet.basic.domain.model.page.Page;
import com.fakebilly.monet.basic.infrastructure.entity.User;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * UserInfraConvert
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Mapper(componentModel = "spring")
public interface UserInfraConvert {

    UserInfraConvert INSTANCE = Mappers.getMapper(UserInfraConvert.class);

    /**
     * User -> UserDO
     * @param user
     * @return com.fakebilly.monet.basic.domain.model.entity.user.UserDO
     **/
    UserDO toUserDO(User user);

    /**
     * CreateUserCommand -> User
     * @param command
     * @return com.fakebilly.monet.basic.infrastructure.entity.User
     **/
    User toUser(CreateUserCommand command);

    /**
     * ModifyUserCommand -> User
     * @param command
     * @return com.fakebilly.monet.basic.infrastructure.entity.User
     **/
    User toUser(ModifyUserCommand command);

    /**
     * List<User> -> List<UserDO>
     * @param selectList
     * @return java.util.List<com.fakebilly.monet.basic.domain.model.entity.user.UserDO>
     **/
    List<UserDO> toUserDOList(List<User> selectList);

    /**
     * PageInfo<User> -> Page<UserDO>
     * @param page
     * @return com.fakebilly.monet.basic.domain.model.page.Page<com.fakebilly.monet.basic.domain.model.entity.user.UserDO>
     **/
    @Mappings({
            @Mapping(source = "total", target = "total"),
            @Mapping(source = "pageNum", target = "current"),
            @Mapping(source = "pageSize", target = "size"),
            @Mapping(source = "list", target = "records")
    })
    Page<UserDO> toPageUserDO(PageInfo<User> page);
}
