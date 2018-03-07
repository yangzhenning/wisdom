package com.wisdom.client.bean;

import com.wisdom.client.model.ClientConfig;
import com.wisdom.client.resource.ResourceLoader;

public abstract class AbstractConfigReader implements ConfigReader {

    private ClientConfig clientConfig;

    private ResourceLoader resourceLoader;

    protected AbstractConfigReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void setClientConfig(ClientConfig clientConfig) throws Exception {
        // TODO 这里要对set内容做严格校检
        if (null == this.clientConfig) {
            this.clientConfig = clientConfig;
        }
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
