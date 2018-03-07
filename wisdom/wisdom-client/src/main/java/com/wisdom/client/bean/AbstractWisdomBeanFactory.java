package com.wisdom.client.bean;

import com.wisdom.client.model.ServiceConfig;
import com.wisdom.client.provider.Pull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractWisdomBeanFactory implements WisdomBeanFactory {

    private ConcurrentHashMap<String, ServiceConfig> serviceConfigs = new ConcurrentHashMap<String, ServiceConfig>();

    public ServiceConfig getBean(String name) throws Exception {
        return serviceConfigs.get(name);
    }

    public Map<String, ServiceConfig> getServiceConfig() throws Exception {
        return serviceConfigs;
    }

    public abstract void pull(Pull pull) throws Exception;

    public ConcurrentHashMap<String, ServiceConfig> getServiceConfigs() {
        return serviceConfigs;
    }
}
