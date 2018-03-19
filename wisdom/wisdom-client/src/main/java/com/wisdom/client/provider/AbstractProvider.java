package com.wisdom.client.provider;

import com.wisdom.common.model.Url;
import org.springframework.util.Assert;

public abstract class AbstractProvider implements Register, Pull {

    private final Url url;

    public volatile boolean isStart = false;

    public AbstractProvider(Url url) {
        this.url = url;
    }

    public void register() throws Exception {
        Assert.notNull(url, "url must be not null");
        if (isStart) {
            doRegister();
        }
    }

    protected abstract void doRegister() throws Exception;

    public Url getUrl() {
        return url;
    }
}
