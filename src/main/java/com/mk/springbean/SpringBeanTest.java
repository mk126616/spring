package com.mk.springbean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

//@Component
public class SpringBeanTest implements BeanDefinitionRegistryPostProcessor, BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
//        System.out.println("postProcessBeanDefinitionRegistry");
//        BeanDefinition beanDefinition = new RootBeanDefinition(Person.class);
//        beanDefinitionRegistry.registerBeanDefinition("person",beanDefinition);
//        beanDefinition.setAutowireCandidate(true);

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
