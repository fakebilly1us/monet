package com.fakebilly.monet.business.domain.model.command.business;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 修改
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Data
public class ModifyBusinessCommand implements Serializable {

    private static final long serialVersionUID = -7861612688473222504L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 名称
     */
    private String businessName;

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
