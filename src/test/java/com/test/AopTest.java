package com.test;

import com.mk.service.aop.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ("classpath:spring-mvc.xml"))
public class AopTest {
    @Autowired
    private UserService userService;
    @Test
    public void aopTest(){
        System.out.println("方法调用处获取到结果"+userService.getUserName("张三","18"));
    }
}
