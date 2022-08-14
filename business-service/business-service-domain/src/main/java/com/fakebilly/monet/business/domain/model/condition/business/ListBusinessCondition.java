package com.fakebilly.monet.business.domain.model.condition.business;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 条件查询
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Data
public class ListBusinessCondition implements Serializable {

    private static final long serialVersionUID = 6419958494464570410L;

    /**
     * 业务ID 集合
     */
    private List<Long> idList;


}
