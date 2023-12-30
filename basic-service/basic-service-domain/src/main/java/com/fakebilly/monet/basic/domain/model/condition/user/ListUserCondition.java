package com.fakebilly.monet.basic.domain.model.condition.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 条件查询
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Data
public class ListUserCondition implements Serializable {

    private static final long serialVersionUID = 6419958494464570410L;

    /**
     * 用户ID 集合
     */
    private List<Long> idList;


}
