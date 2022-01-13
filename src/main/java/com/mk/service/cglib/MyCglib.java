package com.mk.service.cglib;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib动态生成
 */
public class MyCglib implements MethodInterceptor {
    private Object target;
    public  Object createProxy(Object o){
        this.target =  o;
        Enhancer enhancer  = new Enhancer();
        enhancer.setCallback(this);
        enhancer.setSuperclass(this.target.getClass());
//        enhancer.setSuperclass(User.class);
        Object proxy = enhancer.create();
        return proxy;
    }

    public Object intercept(Object proxy, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
        System.out.println("执行前增强");
        //直接调用被代理类方法
         Object o = method.invoke(this.target,arguments);
        //通过代理实现类调用被代理类方法
//        Object o = methodProxy.invokeSuper(proxy,arguments);
        System.out.println("执行后增强");
        return o;
    }

    public static void main(String[] args) {
            MyCglib myCglib = new MyCglib();
            UserAction userAction = new UserAction();
            userAction = (UserAction) myCglib.createProxy(userAction);
            userAction.say("你好");
//        User user = (User) myCglib.createProxy(userAction);
//        user.say();
    }
}
