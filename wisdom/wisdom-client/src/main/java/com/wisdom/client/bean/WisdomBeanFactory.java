package com.wisdom.client.bean;

import com.wisdom.client.model.ServiceConfig;

public interface WisdomBeanFactory {

    String PREFIX = "&";

    ServiceConfig getBean(String name) throws Exception;
}
