package com.wisdom.client.context;

import com.google.common.base.Strings;
import com.wisdom.change.UrlChange;
import com.wisdom.client.annotation.WisdomComponent;
import com.wisdom.client.annotation.WisdomValue;
import com.wisdom.client.bean.AbstractWisdomBeanFactory;
import com.wisdom.client.bean.AutowireCapableWisdomBeanFactory;
import com.wisdom.client.bean.PropertieConfigReader;
import com.wisdom.client.constants.ConfigConstants;
import com.wisdom.client.event.NodeUpdateEvent;
import com.wisdom.client.event.WisdomEvent;
import com.wisdom.client.event.WisdomListener;
import com.wisdom.client.model.ClientConfig;
import com.wisdom.client.model.FieldWisdomInfo;
import com.wisdom.client.model.ServiceConfig;
import com.wisdom.client.model.WisdomBean;
import com.wisdom.client.provider.ZooKeeperProvider;
import com.wisdom.client.remote.Config;
import com.wisdom.client.resource.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PropertieWisdomApplicationContext extends AbstractWisdomApplicationContext implements WisdomListener<WisdomEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertieWisdomApplicationContext.class.getName());

    private String configLocation;

    public PropertieWisdomApplicationContext(String configLocation) throws Exception {
        this(configLocation, new AutowireCapableWisdomBeanFactory());
    }

    public PropertieWisdomApplicationContext(String configLocation, AbstractWisdomBeanFactory wisdomBeanFactory) throws Exception {
        super(wisdomBeanFactory);
        this.configLocation = configLocation;
        this.load(configLocation, this);
    }

    @Override
    protected ClientConfig loadLocalConfig(String location) throws Exception {
        PropertieConfigReader propertieConfigReader = new PropertieConfigReader(new ResourceLoader());
        propertieConfigReader.loadConfig(this.configLocation);
        return propertieConfigReader.getClientConfig();
    }

    @Override
    protected void loadRemoteConfig(ClientConfig config, WisdomListener wisdomListener) throws Exception {
        if (null != config) {
            if (null == provider) {
                provider = new ZooKeeperProvider(
                        UrlChange.getUrl(ConfigConstants.CLIENT_ROLE, config.getAppId(), config.getIp(), config.getRegisterPath()),
                        wisdomListener);
                provider.register(provider.getUrl());
                pullRemoteConfigToRepository(provider);
            }
        }
    }


    public void initAssignment(Object object, String name) throws Exception {
        if (!object.getClass().isAnnotationPresent(WisdomComponent.class)) {
            return;
        }

        Field[] fields = object.getClass().getDeclaredFields();
        Map<Field, FieldWisdomInfo> fieldWisdomInfoMap = new HashMap<>();
        HashSet<String> referenceProperty = new HashSet<String>();
        StringBuilder sb = null;
        for (Field field : fields) {
            field.setAccessible(true);
            WisdomValue wisdomValue = field.getAnnotation(WisdomValue.class);
            if (null == wisdomValue) {
                continue;
            }

            // build wisdomn beanName
            sb = new StringBuilder();
            sb.append(wisdomValue.propertyName()).append(PREFIX).append(wisdomValue.version());
            String beanName = sb.toString();

            // reflect assignment
            String value = setValue(object, field, wisdomValue, beanName);

            // collect field
            collectField(fieldWisdomInfoMap, field, wisdomValue, value);

            // collect property
            referenceProperty.add(beanName);
        }

        // create wisdom bean
        createWisdomBean(fieldWisdomInfoMap, referenceProperty, object, name);
    }

    private String setValue(Object object, Field field, WisdomValue wisdomValue, String beanName) throws Exception {
        ServiceConfig serviceConfig = this.wisdomBeanFactory.getBean(beanName);
        String value = null;
        if (null == serviceConfig || Strings.isNullOrEmpty(serviceConfig.getApplicationValue())) {
            value = wisdomValue.value();
        } else {
            value = serviceConfig.getApplicationValue();
        }
        field.set(object, value);
        LOGGER.info("field :{} set value : {}", field.getName(), value);
        return value;
    }

    private void collectField(Map<Field, FieldWisdomInfo> fieldWisdomInfoMap, Field field, WisdomValue wisdomValue, String setValue) {
        FieldWisdomInfo fieldWisdomInfo = new FieldWisdomInfo();
        fieldWisdomInfo.setCanModify(wisdomValue.canModify());
        fieldWisdomInfo.setCurrentValue(setValue);
        fieldWisdomInfo.setDescription(wisdomValue.description());
        fieldWisdomInfo.setOldValue(setValue);
        fieldWisdomInfo.setPropertyName(wisdomValue.propertyName());
        fieldWisdomInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        fieldWisdomInfo.setVersion(wisdomValue.version());
        fieldWisdomInfoMap.put(field, fieldWisdomInfo);
    }

    private void createWisdomBean(Map<Field, FieldWisdomInfo> fieldWisdomInfoMap, HashSet<String> referenceProperty, Object object, String name) {
        if (fieldWisdomInfoMap.size() > 0) {
            // 一个object
            WisdomBean wisdomBean = new WisdomBean();
            wisdomBean.setRegisterTime(new Timestamp(System.currentTimeMillis()));
            wisdomBean.setSpringBeanName(name);
            wisdomBean.setSpringObject(object);
            wisdomBean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            wisdomBean.setFieldWisdomInfoMap(fieldWisdomInfoMap);
            wisdomBean.setReferenceProperty(referenceProperty);
            this.getWisdomApplicationContext().add(wisdomBean);
            LOGGER.info("spring beanName : {}, wisdom object : {} add wisdomContext successful", name, wisdomBean);
        }
    }


    public void modifyAssignment(String propertieName, String propertieVersion, String propertieValue) throws Exception {

        StringBuilder sb = new StringBuilder();
        final String beanName = sb.append(propertieName).append(PREFIX).append(propertieVersion).toString();
        synchronized (beanName) {
            modifyBeanFactory(beanName, propertieName, propertieValue, propertieVersion);
            modifyReference(beanName, propertieName, propertieValue, propertieVersion);
        }
    }

    @Override
    public void close() throws Exception {
        if (null != provider) {
            provider.close();
        }
    }

    private void modifyBeanFactory(String beanName, String propertieName, String propertieValue, String propertieVersion) throws Exception {
        ServiceConfig serviceConfig = this.wisdomBeanFactory.getBean(beanName);
        if (null != serviceConfig) {
            serviceConfig.setApplicationValue(propertieValue);
        } else {
            ServiceConfig newConfig = new ServiceConfig();
            newConfig.setApplicationValue(propertieValue);
            newConfig.setVersion(propertieVersion);
            newConfig.setApplicationName(propertieName);
            this.wisdomBeanFactory.getServiceConfigs().put(beanName, newConfig);
        }
        LOGGER.info("local repository beanName :{} update value {} successful", beanName, propertieValue);
    }

    private void modifyReference(String beanName, String propertieName, String propertieValue, String propertieVersion) throws Exception {
        for (WisdomBean wisdomBean : this.getWisdomApplicationContext()) {
            if (wisdomBean.getReferenceProperty().contains(beanName)) {
                for (Map.Entry<Field, FieldWisdomInfo> entity : wisdomBean.getFieldWisdomInfoMap().entrySet()) {
                    FieldWisdomInfo fieldWisdomInfo = entity.getValue();
                    if (fieldWisdomInfo.getPropertyName().equals(propertieName)
                            && fieldWisdomInfo.getVersion().equals(propertieVersion)) {
                        Field field = entity.getKey();
                        field.setAccessible(true);
                        field.set(wisdomBean.getSpringObject(), propertieValue);
                        fieldWisdomInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                        fieldWisdomInfo.setOldValue(fieldWisdomInfo.getCurrentValue());
                        fieldWisdomInfo.setCurrentValue(propertieValue);
                        fieldWisdomInfo.updateCount();
                        LOGGER.info("spring beanName :{} field : {} update value : {} successful", wisdomBean.getSpringBeanName(), field.getName(), propertieValue);
                    }
                }
                wisdomBean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            }
        }
    }

    @Override
    public void onWisdomEvent(WisdomEvent event) {
        LOGGER.info("request event {}", event);
        if (event instanceof NodeUpdateEvent) {
            if (null != event.getSource()) {
                try {
                    Config config = (Config) event.getSource();
                    modifyAssignment(config.getPropertyName(), config.getPropertyVersion(), config.getPropertyValue());
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("modify config is error on listener , event : {}", event, e);
                }
            }
        }
    }
}
