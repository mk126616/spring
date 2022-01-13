package com.test;

import com.mk.service.cglib.MyCglib;
import com.mk.service.cglib.UserAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ("classpath:spring-mvc.xml"))
public class CglibTest {
    @Test
    public void cglibTest(){
        UserAction userAction = new UserAction();
        MyCglib myCglib = new MyCglib();
        UserAction proxy = (UserAction) myCglib.createProxy(userAction);
        proxy.say("啊哈");
    }
}
