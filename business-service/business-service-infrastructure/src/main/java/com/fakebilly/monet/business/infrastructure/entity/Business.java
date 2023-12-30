package com.fakebilly.monet.business.infrastructure.entity;

import lombok.Data;

import java.util.Date;

/**
 * Business
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Data
public class Business {

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务名称
     */
    private String businessName;

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
