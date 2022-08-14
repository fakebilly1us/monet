package com.fakebilly.monet.basic.infrastructure.repository.user;

import com.fakebilly.monet.basic.domain.enums.CodeEnum;
import com.fakebilly.monet.basic.domain.model.command.user.ModifyUserCommand;
import com.fakebilly.monet.basic.domain.repository.user.UserModifyRepository;
import com.fakebilly.monet.basic.domain.utils.Assert;
import com.fakebilly.monet.basic.infrastructure.convert.UserInfraConvert;
import com.fakebilly.monet.basic.infrastructure.entity.User;
import com.fakebilly.monet.basic.infrastructure.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 修改
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Repository
public class UserModifyRepositoryImpl implements UserModifyRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserModifyRepositoryImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean modify(ModifyUserCommand command) {
        User user = UserInfraConvert.INSTANCE.toUser(command);
        int update = userMapper.update(user);
        Assert.isTrue(update == 1, CodeEnum.ERROR_BIZ_LOGIC, "用户修改失败!");
        return true;
    }
}
