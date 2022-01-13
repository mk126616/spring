package com.mk.reflect;

import java.lang.reflect.Method;

public class MethodInvoke {
    public static void main(String[] args) {
        Child child = new Child();
        Parent parent = new Parent();
        try {
            Method method = Parent.class.getMethod("say");
            method.invoke(child);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
