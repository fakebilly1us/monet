package com.fakebilly.monet.basic.server.parameter.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * GetUserParameter
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Data
public class GetUserParameter implements Serializable {

    private static final long serialVersionUID = 5433616535524534891L;

    /**
     * id
     */
    @NotNull(message = "用户ID不能为空")
    private Long id;

}
