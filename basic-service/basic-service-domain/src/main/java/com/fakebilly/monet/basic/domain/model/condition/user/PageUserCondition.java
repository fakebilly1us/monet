package com.fakebilly.monet.basic.domain.model.condition.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页条件
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Data
public class PageUserCondition implements Serializable {

    private static final long serialVersionUID = 4952105370544675216L;

    /**
     * 用户姓名
     */
    private String userName;


}
