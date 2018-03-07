package com.wisdom.client.bean;

import com.wisdom.client.model.ServiceConfig;
import com.wisdom.client.provider.Pull;
import com.wisdom.client.remote.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class AutowireCapableWisdomBeanFactory extends AbstractWisdomBeanFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutowireCapableWisdomBeanFactory.class.getName());

    @Override
    public void pull(Pull pull) throws Exception {

        List<Config> configs = pull.pull();
        LOGGER.info("get configs {}", configs);
        if (CollectionUtils.isEmpty(configs)) {
            return;
        }

        ServiceConfig serviceConfig = null;
        StringBuilder stringBuilder = null;
        for (Config config : configs) {
            stringBuilder = new StringBuilder().append(config.getPropertyName()).append(PREFIX).append(config.getPropertyVersion());
            serviceConfig = new ServiceConfig();
            serviceConfig.setApplicationName(config.getPropertyName());
            serviceConfig.setVersion(config.getPropertyVersion());
            serviceConfig.setApplicationValue(config.getPropertyValue());
            this.getServiceConfigs().put(stringBuilder.toString(), serviceConfig);
        }
        LOGGER.info("remote config pull successful");
        configs = null;
    }
}
