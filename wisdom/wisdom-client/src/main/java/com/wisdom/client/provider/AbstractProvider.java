package com.wisdom.client.provider;

import com.wisdom.common.model.Url;

public abstract class AbstractProvider implements Register, Pull {

    private final Url url;

    public volatile boolean isStart = false;

    public AbstractProvider(Url url) {
        this.url = url;
    }

    public void register(Url url) throws Exception {
        if (isStart) {
            doRegister();
        }
    }

    protected abstract void doRegister() throws Exception;

    public Url getUrl() {
        return url;
    }






}
