package com.fakebilly.monet.basic.infrastructure.repository.user;

import com.fakebilly.monet.basic.domain.adapter.IdWorkerAdapter;
import com.fakebilly.monet.basic.domain.enums.CodeEnum;
import com.fakebilly.monet.basic.domain.model.command.user.CreateUserCommand;
import com.fakebilly.monet.basic.domain.repository.user.UserCreateRepository;
import com.fakebilly.monet.basic.domain.utils.Assert;
import com.fakebilly.monet.basic.infrastructure.convert.UserInfraConvert;
import com.fakebilly.monet.basic.infrastructure.entity.User;
import com.fakebilly.monet.basic.infrastructure.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 新增
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Repository
public class UserCreateRepositoryImpl implements UserCreateRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserCreateRepositoryImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IdWorkerAdapter idWorkerAdapter;

    @Override
    public long create(CreateUserCommand command) {
        User user = UserInfraConvert.INSTANCE.toUser(command);
        long id = idWorkerAdapter.generateId();
        user.setId(id);
        int insert = userMapper.insert(user);
        Assert.isTrue(insert == 1, CodeEnum.ERROR_BIZ_LOGIC, "用户创建失败!");
        return id;
    }
}
