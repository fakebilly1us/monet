package com.fakebilly.monet.business.server.controller;

import com.alibaba.fastjson.JSON;
import com.fakebilly.monet.business.application.presentation.BusinessPresentation;
import com.fakebilly.monet.business.domain.model.entity.business.BusinessDO;
import com.fakebilly.monet.business.server.convert.BusinessServerConvert;
import com.fakebilly.monet.business.server.parameter.business.GetBusinessParameter;
import com.fakebilly.monet.business.server.vo.business.BusinessVO;
import com.fakebilly.monet.core.dto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * BusinessController
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Validated
@RestController
@RequestMapping("/business")
public class BusinessController {

    private static final Logger logger = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private BusinessPresentation businessPresentation;

    /**
     * 查询业务
     * @param parameter
     * @return com.fakebilly.monet.core.dto.Response<com.fakebilly.monet.business.server.vo.business.BusinessVO>
     **/
    @ResponseBody
    @GetMapping(value = "/getBusiness", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<BusinessVO> getBusiness(@ModelAttribute GetBusinessParameter parameter) throws Exception {
        logger.info("查询业务,Start,parameter:{}", JSON.toJSONString(parameter));
        BusinessDO businessDO = businessPresentation.findBusiness(parameter.getId());
        return Response.success(BusinessServerConvert.INSTANCE.toBusinessVO(businessDO));
    }

}
