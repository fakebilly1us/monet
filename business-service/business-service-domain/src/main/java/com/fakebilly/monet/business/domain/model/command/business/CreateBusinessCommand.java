package com.fakebilly.monet.business.domain.model.command.business;

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
public class CreateBusinessCommand implements Serializable {

    private static final long serialVersionUID = -4466636932378631547L;

    /**
     * 名称
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


}
