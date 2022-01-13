package com.mk.service.aop;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    public String getUserName(String name,String age) {
        System.out.println("目标方法被执行");
        return "姓名："+name+" 年龄：" + age;
    }

    @Override
    public String toString() {
        return "重写后的tostring";
    }
}
