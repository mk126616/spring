package com.mk.service.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class SessionListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("监听器创建");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("监听器销毁");
    }
}
