package com.fakebilly.monet.basic.api.service.user;

import com.fakebilly.monet.basic.api.dto.user.UserDTO;
import com.fakebilly.monet.basic.api.request.user.query.FindUserRequest;
import com.fakebilly.monet.core.dto.Response;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * IUserQueryService
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public interface IUserQueryService {

    /**
     * 查询用户
     * @param request
     * @return com.fakebilly.monet.core.dto.Response<com.fakebilly.monet.basic.api.dto.user.UserDTO>
     **/
    Response<UserDTO> findUser(@Valid @NotNull(message = "查询条件不能为空") FindUserRequest request);

}
