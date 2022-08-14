package com.fakebilly.monet.basic.infrastructure.entity.es;

import com.fakebilly.monet.es.entity.ESBaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * ESUserEntity
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Data
public class ESUserEntity extends ESBaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 姓名
     */
    private String user_name;

    /**
     * 创建人ID
     */
    private Long creator_id;

    /**
     * 创建人姓名
     */
    private String creator_name;

    /**
     * 创建时间
     */
    private Date created_time;

    /**
     * 修改人ID
     */
    private Long modifier_id;

    /**
     * 修改人姓名
     */
    private String modifier_name;

    /**
     * 修改人时间
     */
    private Date modified_time;


}
