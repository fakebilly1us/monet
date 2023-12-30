package com.fakebilly.monet.basic.api.request.user.query;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * FindUserRequest
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Data
public class FindUserRequest implements Serializable {

    private static final long serialVersionUID = -1999750228115067908L;

    /**
     * id
     */
    @NotNull(message = "用户ID不能为空")
    private Long id;
}
