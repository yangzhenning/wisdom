package com.wisdom.client.context;

import com.wisdom.client.bean.WisdomBeanFactory;

public interface WisdomContext extends WisdomBeanFactory {

    void initAssignment(Object object, String name) throws Exception;

    void modifyAssignment(String propertieName, String propertieVersion, String propertieValue) throws Exception;

    void close() throws Exception;
}
