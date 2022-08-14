package com.fakebilly.monet.basic.server.controller;

import com.alibaba.fastjson.JSON;
import com.fakebilly.monet.basic.application.presentation.user.UserPresentation;
import com.fakebilly.monet.basic.domain.model.entity.user.UserDO;
import com.fakebilly.monet.basic.domain.utils.LogUtil;
import com.fakebilly.monet.basic.server.convert.UserServerConvert;
import com.fakebilly.monet.basic.server.parameter.user.GetUserParameter;
import com.fakebilly.monet.basic.server.vo.user.UserVO;
import com.fakebilly.monet.core.dto.Response;
import com.fakebilly.monet.log.ILogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * UserController
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    private static final ILogger logger = LogUtil.getLogger(UserController.class);

    @Autowired
    private UserPresentation userPresentation;

    /**
     * 查询用户
     * @param parameter
     * @return com.fakebilly.monet.core.dto.Response<com.fakebilly.monet.basic.server.vo.user.UserVO>
     **/
    @ResponseBody
    @GetMapping(value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<UserVO> getUser(@ModelAttribute @Valid GetUserParameter parameter) throws Exception {
        logger.info("查询用户,Start,parameter:{}", JSON.toJSONString(parameter));
        UserDO userDO = userPresentation.findUser(parameter.getId());
        UserVO userVO = UserServerConvert.INSTANCE.toUserVO(userDO);
        logger.info("查询用户,End,userVO:{}", JSON.toJSONString(userVO));
        return Response.success(userVO);
    }

}
