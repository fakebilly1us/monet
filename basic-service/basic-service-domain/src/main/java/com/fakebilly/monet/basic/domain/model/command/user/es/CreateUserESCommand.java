package com.fakebilly.monet.basic.domain.model.command.user.es;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Data
public class CreateUserESCommand implements Serializable {

    private static final long serialVersionUID = 107043855370092969L;

    private Long id;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private Date createdTime;


}
