package com.wisdom.client.processor;

import com.wisdom.client.loader.WisdomLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class WisdomBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(WisdomBeanPostProcessor.class.getName());

    @Autowired
    private WisdomLoader loader;

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        System.out.println(".............");
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            if (null != loader.getWisdomContext()) {
                loader.getWisdomContext().initAssignment(bean, beanName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("postProcessAfterInitialization error", e);
            return bean;
        }
        return bean;
    }











}
