package com.fakebilly.monet.business.infrastructure.adapter;

import com.fakebilly.monet.business.domain.adapter.IdWorkerAdapter;
import com.fakebilly.monet.idworker.factory.IdWorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * IdWorkerAdapter.IdWorkerAdapterImpl
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component
public class IdWorkerAdapterImpl implements IdWorkerAdapter {

    @Autowired
    private IdWorkerFactory idWorkerFactory;

    @Override
    public long generateId() {
        return idWorkerFactory.nextId();
    }
}
