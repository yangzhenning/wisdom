package com.wisdom.client.loader;

import com.wisdom.client.context.WisdomContext;

public interface WisdomLoader {

    void loadWisdomContext() throws Exception;

    WisdomContext getWisdomContext() throws Exception;
}
