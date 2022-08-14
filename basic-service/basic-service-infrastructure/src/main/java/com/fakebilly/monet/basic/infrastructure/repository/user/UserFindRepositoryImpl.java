package com.fakebilly.monet.basic.infrastructure.repository.user;

import com.fakebilly.monet.basic.domain.model.condition.user.ListUserCondition;
import com.fakebilly.monet.basic.domain.model.condition.user.PageUserCondition;
import com.fakebilly.monet.basic.domain.model.entity.user.UserDO;
import com.fakebilly.monet.basic.domain.model.page.Page;
import com.fakebilly.monet.basic.domain.repository.user.UserFindRepository;
import com.fakebilly.monet.basic.domain.utils.LogUtil;
import com.fakebilly.monet.basic.infrastructure.convert.UserInfraConvert;
import com.fakebilly.monet.basic.infrastructure.entity.User;
import com.fakebilly.monet.basic.infrastructure.mapper.UserMapper;
import com.fakebilly.monet.log.ILogger;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 查询
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Repository
public class UserFindRepositoryImpl implements UserFindRepository {

    private static final ILogger logger = LogUtil.getLogger(UserFindRepositoryImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDO find(Long id) {
        logger.info("查询用户,id:{}", id);
        return UserInfraConvert.INSTANCE.toUserDO(userMapper.selectById(id));
    }

    @Override
    public Page<UserDO> findPage(int pageNum, int pageSize, PageUserCondition condition) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.selectPage(condition);
        PageInfo<User> page = new PageInfo<>(userList);
        return UserInfraConvert.INSTANCE.toPageUserDO(page);
    }

    @Override
    public List<UserDO> findList(ListUserCondition condition) {
        return UserInfraConvert.INSTANCE.toUserDOList(userMapper.selectList(condition));
    }
}
