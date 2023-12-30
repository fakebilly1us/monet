package com.fakebilly.monet.business.domain.model.condition.business;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页条件
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Data
public class PageBusinessCondition implements Serializable {

    private static final long serialVersionUID = 4952105370544675216L;

    /**
     * 业务名称
     */
    private String businessName;


}
