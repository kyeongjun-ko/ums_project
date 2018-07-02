package com.bccns.umsserviceweb.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
 
public abstract class SpringUtils {
    private static ApplicationContext getApplicationContext() {
        return ContextLoader.getCurrentWebApplicationContext();
    }

    public static boolean containsBean(String beanName) {
        return getApplicationContext().containsBean(beanName);
    }

    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }
}
