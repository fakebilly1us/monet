package com.fakebilly.monet.business.server.parameter.business;

import lombok.Data;

import java.io.Serializable;

/**
 * GetBusinessParameter
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Data
public class GetBusinessParameter implements Serializable {

    private static final long serialVersionUID = 5433616535524534891L;

    /**
     * id
     */
    private Long id;

}
