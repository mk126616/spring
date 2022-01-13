package com.mk.controller;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.annotation.Resource;
import javax.servlet.Servlet;
import javax.xml.ws.Service;

public class TestController {
    @Resource
    private String var1;
    public void testMethod(){
        //ioc容器
        ApplicationContext applicationContext;
        BeanDefinitionRegistryPostProcessor registryPostProcessor;
        BeanDefinitionRegistry var1;
        //资源读取
        ClassPathResource classPathResource = new ClassPathResource("");
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(classPathResource);
        FactoryBean factoryBean;
        BeanFactory beanFactory;
        DefaultListableBeanFactory defaultListableBeanFactory;
        //表达式解析器
        ExpressionParser expressionParser = new SpelExpressionParser();
        EvaluationContext evaluationContext = new StandardEvaluationContext();
//        expressionParser.
    }
}
