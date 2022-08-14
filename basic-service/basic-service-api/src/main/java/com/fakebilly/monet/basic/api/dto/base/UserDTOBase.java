package com.fakebilly.monet.basic.api.dto.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * UserDTOBase
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Data
public abstract class UserDTOBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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

    /**
     * 修改人ID
     */
    private Long modifierId;

    /**
     * 修改人姓名
     */
    private String modifierName;

    /**
     * 修改人时间
     */
    private Date modifiedTime;


}
