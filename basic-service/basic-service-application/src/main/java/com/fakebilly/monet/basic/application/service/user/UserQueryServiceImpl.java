package com.fakebilly.monet.basic.application.service.user;

import com.alibaba.fastjson2.JSON;
import com.fakebilly.monet.basic.api.dto.user.UserDTO;
import com.fakebilly.monet.basic.api.request.user.query.FindUserRequest;
import com.fakebilly.monet.basic.api.service.user.IUserQueryService;
import com.fakebilly.monet.basic.application.convert.UserAppConvert;
import com.fakebilly.monet.basic.application.convert.UserESAppConvert;
import com.fakebilly.monet.basic.domain.adapter.MqAdapter;
import com.fakebilly.monet.basic.domain.adapter.RedisAdapter;
import com.fakebilly.monet.basic.domain.constants.MQConstant;
import com.fakebilly.monet.basic.domain.model.command.user.es.ModifyUserESCommand;
import com.fakebilly.monet.basic.domain.model.entity.user.UserDO;
import com.fakebilly.monet.basic.domain.mq.builder.MQMessageBuilder;
import com.fakebilly.monet.basic.domain.repository.user.UserESRepository;
import com.fakebilly.monet.basic.domain.repository.user.UserFindRepository;
import com.fakebilly.monet.core.dto.Response;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * IUserQueryService.UserQueryServiceImpl
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Service(version = "1.0.0", retries = 0, timeout = 30000, validation = "true")
public class UserQueryServiceImpl implements IUserQueryService {

    private static final Logger logger = LoggerFactory.getLogger(UserQueryServiceImpl.class);

    @Autowired
    private UserFindRepository userFindRepository;

    @Autowired
    private RedisAdapter redisAdapter;

    @Autowired
    private UserESRepository userESRepository;

    @Autowired
    private MqAdapter mqAdapter;

    @Override
    public Response<UserDTO> findUser(FindUserRequest request) {
        logger.info("查询用户,Start,request:{}", JSON.toJSONString(request));
        Long id = request.getId();
        UserDO userDO = null;
        if (id == -1) {
            userDO = userFindRepository.find(1L);
            MQMessageBuilder builder = MQMessageBuilder.create()
                    .topic(MQConstant.TOPIC_BASIC)
                    .tag(MQConstant.TAG_PRODUCER)
                    .key("-1")
                    .message(new MQMessageBuilder.MQMessage("-1", com.alibaba.fastjson.JSON.parseObject(com.alibaba.fastjson.JSON.toJSONString(userDO))));
            mqAdapter.sendNormal(builder);
        } else if (id == 0) {
            long i = id / 0;
        } else if (id == 1) {
            userDO = redisAdapter.get("USER:" + id);
            logger.info("查询用户,Redis,userDO:{}", com.alibaba.fastjson.JSON.toJSONString(userDO));
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
                try {
                    userESRepository.delete(id);
                    Thread.sleep(2000);
                    userESRepository.create(UserESAppConvert.INSTANCE.toCreateUserESCommand(userDO));
                    Thread.sleep(2000);
                    ModifyUserESCommand command = UserESAppConvert.INSTANCE.toModifyUserESCommand(userDO);
                    command.setUserName("12345");
                    userESRepository.update(command);
                    Thread.sleep(2000);
                    userDO = userESRepository.get(id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        UserDTO userDTO = UserAppConvert.INSTANCE.toUserDTO(userDO);
        return Response.success(userDTO);
    }
}
