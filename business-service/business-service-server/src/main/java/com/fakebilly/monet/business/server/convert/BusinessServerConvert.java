package com.fakebilly.monet.business.server.convert;

import com.fakebilly.monet.business.domain.model.entity.business.BusinessDO;
import com.fakebilly.monet.business.server.vo.business.BusinessVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * BusinessServerConvert
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Mapper(componentModel = "spring")
public interface BusinessServerConvert {

    BusinessServerConvert INSTANCE = Mappers.getMapper(BusinessServerConvert.class);

    /**
     * BusinessDO -> BusinessVO
     * @param businessDO
     * @return com.fakebilly.monet.business.server.vo.business.BusinessVO
     **/
    BusinessVO toBusinessVO(BusinessDO businessDO);

}
