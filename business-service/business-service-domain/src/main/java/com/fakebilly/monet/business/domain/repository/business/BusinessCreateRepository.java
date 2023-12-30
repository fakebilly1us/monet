package com.fakebilly.monet.business.domain.repository.business;

import com.fakebilly.monet.business.domain.model.command.business.CreateBusinessCommand;

/**
 * 新增
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public interface BusinessCreateRepository {

    /**
     * 新增
     * @param command
     * @return long
     **/
    long create(CreateBusinessCommand command);

}
