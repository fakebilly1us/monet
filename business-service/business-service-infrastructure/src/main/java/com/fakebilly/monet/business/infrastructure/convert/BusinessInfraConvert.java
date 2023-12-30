package com.fakebilly.monet.business.infrastructure.convert;

import com.fakebilly.monet.business.domain.model.command.business.CreateBusinessCommand;
import com.fakebilly.monet.business.domain.model.command.business.ModifyBusinessCommand;
import com.fakebilly.monet.business.domain.model.entity.business.BusinessDO;
import com.fakebilly.monet.business.domain.model.page.Page;
import com.fakebilly.monet.business.infrastructure.entity.Business;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * BusinessInfraConvert
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Mapper(componentModel = "spring")
public interface BusinessInfraConvert {

    BusinessInfraConvert INSTANCE = Mappers.getMapper(BusinessInfraConvert.class);

    /**
     * Business -> BusinessDO
     * @param business
     * @return com.fakebilly.monet.business.domain.model.entity.business.BusinessDO
     **/
    BusinessDO toBusinessDO(Business business);

    /**
     * CreateBusinessCommand -> Business
     * @param command
     * @return com.fakebilly.monet.business.infrastructure.entity.Business
     **/
    Business toBusiness(CreateBusinessCommand command);

    /**
     * ModifyBusinessCommand -> Business
     * @param command
     * @return com.fakebilly.monet.business.infrastructure.entity.Business
     **/
    Business toBusiness(ModifyBusinessCommand command);

    /**
     * List<Business> -> List<BusinessDO>
     * @param selectList
     * @return java.util.List<com.fakebilly.monet.business.domain.model.entity.business.BusinessDO>
     **/
    List<BusinessDO> toBusinessDOList(List<Business> selectList);

    /**
     * PageInfo<Business> -> Page<BusinessDO>
     * @param page
     * @return com.fakebilly.monet.business.domain.model.page.Page<com.fakebilly.monet.business.domain.model.entity.business.BusinessDO>
     **/
    @Mappings({
            @Mapping(source = "total", target = "total"),
            @Mapping(source = "pageNum", target = "current"),
            @Mapping(source = "pageSize", target = "size"),
            @Mapping(source = "list", target = "records")
    })
    Page<BusinessDO> toPageBusinessDO(PageInfo<Business> page);
}
