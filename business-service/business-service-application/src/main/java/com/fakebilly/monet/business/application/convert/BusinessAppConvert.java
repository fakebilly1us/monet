package com.fakebilly.monet.business.application.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * BusinessAppConvert
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Mapper(componentModel = "spring")
public interface BusinessAppConvert {

    BusinessAppConvert INSTANCE = Mappers.getMapper(BusinessAppConvert.class);


}
