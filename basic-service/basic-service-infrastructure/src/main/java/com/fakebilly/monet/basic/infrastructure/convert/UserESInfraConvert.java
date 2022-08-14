package com.fakebilly.monet.basic.infrastructure.convert;

import com.fakebilly.monet.basic.domain.model.command.user.es.CreateUserESCommand;
import com.fakebilly.monet.basic.domain.model.command.user.es.ModifyUserESCommand;
import com.fakebilly.monet.basic.domain.model.entity.user.UserDO;
import com.fakebilly.monet.basic.infrastructure.entity.es.ESUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * UserInfraConvert
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Mapper(componentModel = "spring")
public interface UserESInfraConvert {

    UserESInfraConvert INSTANCE = Mappers.getMapper(UserESInfraConvert.class);

    /**
     * CreateUserESCommand -> ESUserEntity
     * @param command
     * @return com.fakebilly.monet.basic.infrastructure.entity.es.ESUserEntity
     **/
    @Mappings({
            @Mapping(source = "userName", target = "user_name"),
            @Mapping(source = "creatorId", target = "creator_id"),
            @Mapping(source = "creatorName", target = "creator_name"),
            @Mapping(source = "createdTime", target = "created_time")
    })
    ESUserEntity toESUserEntity(CreateUserESCommand command);

    /**
     * ModifyUserESCommand -> ESUserEntity
     * @param command
     * @return com.fakebilly.monet.basic.infrastructure.entity.es.ESUserEntity
     **/
    @Mappings({
            @Mapping(source = "userName", target = "user_name"),
            @Mapping(source = "modifierId", target = "modifier_id"),
            @Mapping(source = "modifierName", target = "modifier_name"),
            @Mapping(source = "modifiedTime", target = "modified_time")
    })
    ESUserEntity toESUserEntity(ModifyUserESCommand command);

    /**
     * ESUserEntity -> UserDO
     * @param entity
     * @return com.fakebilly.monet.basic.domain.model.entity.user.UserDO
     **/
    @Mappings({
            @Mapping(source = "user_name", target = "userName"),
            @Mapping(source = "creator_id", target = "creatorId"),
            @Mapping(source = "creator_name", target = "creatorName"),
            @Mapping(source = "created_time", target = "createdTime"),
            @Mapping(source = "modifier_id", target = "modifierId"),
            @Mapping(source = "modifier_name", target = "modifierName"),
            @Mapping(source = "modified_time", target = "modifiedTime")
    })
    UserDO toUserDO(ESUserEntity entity);


}
