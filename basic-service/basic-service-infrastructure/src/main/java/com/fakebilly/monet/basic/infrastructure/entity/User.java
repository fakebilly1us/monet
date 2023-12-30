package com.fakebilly.monet.basic.infrastructure.entity;

import lombok.Data;

import java.util.Date;

/**
 * User
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Data
public class User {

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
