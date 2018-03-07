package com.wisdom.client.provider;

import com.wisdom.common.model.Url;
import org.springframework.util.Assert;

public abstract class AbstractProvider implements Register, Pull {

    private final Url url;

    public volatile boolean isRegister = false;

    public AbstractProvider(Url url) {
        this.url = url;
    }

    public void register(Url url) throws Exception {
        Assert.notNull(url, "provider object url must be not null");
        Assert.notNull(url.getUrl(), "provider url must be not null");
        if (isRegister) {
            doRegister();
        }
    }

    protected abstract void doRegister() throws Exception;

    public Url getUrl() {
        return url;
    }






}
