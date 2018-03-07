package com.wisdom.client.bean;

import com.google.common.base.Strings;
import com.wisdom.client.constants.ConfigConstants;
import com.wisdom.client.model.ClientConfig;
import com.wisdom.client.resource.ResourceLoader;
import com.wisdom.client.utils.IPUtil;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Properties;

public class PropertieConfigReader extends AbstractConfigReader implements ConfigReader {

    public PropertieConfigReader(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    public void loadConfig(String location) throws Exception {
        Assert.notNull(location, "location must not null");
        InputStream inputStream = this.getResourceLoader().getResource(location).getInputStream();
        try {
            doLoadConfig(inputStream);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void doLoadConfig(InputStream inputStream) throws Exception {
        Properties prop = new Properties();
        prop.load(inputStream);
        registerConfig(prop);
    }

    public void registerConfig(Properties prop) throws Exception {
        String appId = prop.getProperty(ConfigConstants.WISDOM_CONFIG_ID_NAMED);
        if (Strings.isNullOrEmpty(appId)) {
            return;
        }

        String path = prop.getProperty(ConfigConstants.WISDOM_CONFIG_ZOOKEEPER_NAMED);
        if (Strings.isNullOrEmpty(path)) {
            return;
        }

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegisterTime(new Timestamp(System.currentTimeMillis()));
        clientConfig.setAppId(appId);
        clientConfig.setIp(IPUtil.getIpAddress());
        clientConfig.setRegisterPath(path);
        this.setClientConfig(clientConfig);
    }
}
