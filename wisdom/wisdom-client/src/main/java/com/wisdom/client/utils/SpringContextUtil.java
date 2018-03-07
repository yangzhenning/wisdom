package com.wisdom.client.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 〈访问spring上下文工具类〉<br> 
 * 〈功能详细描述〉
 *
 * @author 杨振宁
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Component("springContextUtil")
public class SpringContextUtil implements ApplicationContextAware{

    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        context = ctx;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    /**
     * 
     * 功能描述: 根据Bean名称返回String类型的BEAN<br>
     * 〈功能详细描述〉
     *
     * @param beanName
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getStringBean(String beanName) {
        return context.getBean(beanName, String.class);
    }

}
