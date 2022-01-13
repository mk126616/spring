package com.mk.service.proxy;

import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyUtils implements InvocationHandler {
    private Object target;
    public Object createProxy(Object o){
        target = o;
        Class [] classes = new Class[1];
        classes[0] = Person.class;
        return Proxy.newProxyInstance(o.getClass().getClassLoader(),classes,this);
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass().getMethods().toString());
//        System.out.println("say接口被代理");
//        method.invoke(target,args);
        return null;
    }

    public static void main(String[] args) {
        ProxyUtils proxyUtils = new ProxyUtils();
        Men men = new Men();
        Person person = (Person) proxyUtils.createProxy(men);
        person.say();

    }
}
