package com.wisdom.client.context;

import com.wisdom.client.bean.AbstractWisdomBeanFactory;
import com.wisdom.client.event.WisdomListener;
import com.wisdom.client.model.ClientConfig;
import com.wisdom.client.model.ServiceConfig;
import com.wisdom.client.model.WisdomBean;
import com.wisdom.client.provider.AbstractProvider;
import com.wisdom.client.provider.Pull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWisdomApplicationContext implements WisdomContext {

    private List<WisdomBean> WISDOM_CONTEXT = new ArrayList<>();

    protected AbstractWisdomBeanFactory wisdomBeanFactory;

    protected AbstractProvider provider;

    public AbstractWisdomApplicationContext(AbstractWisdomBeanFactory wisdomBeanFactory) {
        this.wisdomBeanFactory = wisdomBeanFactory;
    }

    protected void load(String location, WisdomListener listener) throws Exception {
        loadRemoteConfig(loadLocalConfig(location), listener);
    }

    protected void pullRemoteConfigToRepository (Pull pull) throws Exception {
        this.wisdomBeanFactory.pull(pull);
    }

    protected abstract ClientConfig loadLocalConfig(String location) throws Exception;

    protected abstract void loadRemoteConfig(ClientConfig config, WisdomListener wisdomListener) throws Exception;

    public List<WisdomBean> getWisdomApplicationContext() {
        return WISDOM_CONTEXT;
    }

    public ServiceConfig getBean(String name) throws Exception {
        return wisdomBeanFactory.getBean(name);
    }
}
