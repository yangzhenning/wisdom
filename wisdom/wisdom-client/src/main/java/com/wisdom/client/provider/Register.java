package com.wisdom.client.provider;

import com.wisdom.common.model.Url;

public interface Register {

    void register(Url url) throws Exception;

    void close() throws Exception;
}
