package com.mk.elasticsearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ("classpath:spring-mvc.xml"))
public class SpringEsTest
{
    @Autowired
    private ElasticsearchTemplate esTemplate;
    @Autowired
    private UserDao userDao;

    /**
     * 创建索引
     */
    @Test
    public void createIndex(){
        System.out.println(userDao.findById(1L).get());
        System.out.println(String.format("创建索引：%S",esTemplate.createIndex(User.class)));
    }
}
