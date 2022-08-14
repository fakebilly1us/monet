package com.fakebilly.monet.basic.domain.model.command.user.es;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 修改
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Data
public class ModifyUserESCommand implements Serializable {

    private static final long serialVersionUID = 8297748816434657864L;

    private Long id;

    /**
     * 姓名
     */
    private String userName;

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
