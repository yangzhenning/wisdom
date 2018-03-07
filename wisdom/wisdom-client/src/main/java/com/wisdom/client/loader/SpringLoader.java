package com.wisdom.client.loader;

import com.wisdom.client.constants.ConfigConstants;
import com.wisdom.client.context.PropertieWisdomApplicationContext;
import com.wisdom.client.context.WisdomContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Order(value = Integer.MIN_VALUE)
@Component
public class SpringLoader implements WisdomLoader, ApplicationListener<ApplicationContextEvent>, EnvironmentAware, DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLoader.class.getName());

    private volatile WisdomContext wisdomContext;

    public void loadWisdomContext() throws Exception {
        LOGGER.info("start load wisdom application context");
        synchronized (WisdomLoader.class) {
            if (null == wisdomContext) {
                wisdomContext = new PropertieWisdomApplicationContext(ConfigConstants.WISDOM_PROPERTIE_NAMED);
            }
        }
        LOGGER.info("load wisdom application context successful");
    }

    public WisdomContext getWisdomContext() throws Exception {
        return wisdomContext;
    }

    public void onApplicationEvent(ApplicationContextEvent event) {
        if (event instanceof ContextRefreshedEvent) {

        }
    }

    public void setEnvironment(Environment environment) {
        try {
            this.loadWisdomContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        this.wisdomContext.close();
    }
}
