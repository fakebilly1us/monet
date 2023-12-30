package com.fakebilly.monet.business.infrastructure.config.rpc;

import com.fakebilly.monet.basic.api.service.user.IUserQueryService;
import lombok.Data;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Configuration;

/**
 * DubboReferenceConfig
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Configuration
@Data
public class DubboReferenceConfig {


    @Reference(version = "1.0.0", check = false, timeout = 30000)
    private IUserQueryService userQueryService;

}
