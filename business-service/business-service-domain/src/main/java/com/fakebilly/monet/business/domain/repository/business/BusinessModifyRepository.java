package com.fakebilly.monet.business.domain.repository.business;

import com.fakebilly.monet.business.domain.model.command.business.ModifyBusinessCommand;

/**
 * 修改
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public interface BusinessModifyRepository {

    /**
     * 修改
     * @param command
     * @return boolean
     **/
    boolean modify(ModifyBusinessCommand command);


}
