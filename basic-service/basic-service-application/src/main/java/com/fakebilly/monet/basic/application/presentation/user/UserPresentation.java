package com.fakebilly.monet.basic.application.presentation.user;

import com.alibaba.fastjson.JSON;
import com.fakebilly.monet.basic.application.convert.UserESAppConvert;
import com.fakebilly.monet.basic.domain.adapter.MqAdapter;
import com.fakebilly.monet.basic.domain.adapter.RedisAdapter;
import com.fakebilly.monet.basic.domain.constants.MQConstant;
import com.fakebilly.monet.basic.domain.model.command.user.es.ModifyUserESCommand;
import com.fakebilly.monet.basic.domain.model.entity.user.UserDO;
import com.fakebilly.monet.basic.domain.mq.builder.MQMessageBuilder;
import com.fakebilly.monet.basic.domain.repository.user.UserESRepository;
import com.fakebilly.monet.basic.domain.repository.user.UserFindRepository;
import com.fakebilly.monet.basic.domain.utils.LogUtil;
import com.fakebilly.monet.log.ILogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserPresentation
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Service
public class UserPresentation {

    private static final ILogger logger = LogUtil.getLogger(UserPresentation.class);

    @Autowired
    private UserFindRepository userFindRepository;

    @Autowired
    private RedisAdapter redisAdapter;

    @Autowired
    private UserESRepository userESRepository;

    @Autowired
    private MqAdapter mqAdapter;

    /**
     * 查询用户
     * @param id
     * @return com.fakebilly.monet.basic.domain.model.entity.user.UserDO
     **/
    public UserDO findUser(Long id) throws InterruptedException {
        logger.info("查询用户,id:{}", id);
        UserDO userDO = null;
        if (id == -1) {
            userDO = userFindRepository.find(1L);
            MQMessageBuilder builder = MQMessageBuilder.create()
                    .topic(MQConstant.TOPIC_BASIC)
                    .tag(MQConstant.TAG_PRODUCER)
                    .key("-1")
                    .message(new MQMessageBuilder.MQMessage("-1", JSON.parseObject(JSON.toJSONString(userDO))));
            mqAdapter.sendNormal(builder);
        } else if (id == 0) {
            long i = id / 0;
        } else if (id == 1) {
            userDO = redisAdapter.get("USER:" + id);
            logger.info("查询用户,Redis,userDO:{}", JSON.toJSONString(userDO));
            if (null == userDO) {
                userDO = userFindRepository.find(id);
                if (null != userDO) {
                    logger.info("查询用户,Redis Set Start");
                    redisAdapter.set("USER:" + id, userDO, 1000);
                    logger.info("查询用户,Redis Set End");
                }
            }
        } else if (id == 2) {
            userESRepository.searchDemo();
            userDO = userESRepository.get(id);
            if (null == userDO) {
                userDO = userFindRepository.find(id);
                if (null != userDO) {
                    logger.info("查询用户,ES Set Start");
                    userESRepository.create(UserESAppConvert.INSTANCE.toCreateUserESCommand(userDO));
                    logger.info("查询用户,ES Set End");
                }
            } else {
                userESRepository.delete(id);
                Thread.sleep(2000);
                userESRepository.create(UserESAppConvert.INSTANCE.toCreateUserESCommand(userDO));
                Thread.sleep(2000);
                ModifyUserESCommand command = UserESAppConvert.INSTANCE.toModifyUserESCommand(userDO);
                command.setUserName("12345");
                userESRepository.update(command);
                Thread.sleep(2000);
                userDO = userESRepository.get(id);
            }
        } else if (id == 3) {
            userESRepository.bulkDemo();
            userESRepository.searchDemo();
        }
        return userDO;
    }
}
