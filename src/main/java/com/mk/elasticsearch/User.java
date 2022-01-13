package com.mk.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "user",shards = 3,replicas = 1)
public class User
{
    @Id
    @Field(index = true,store = true,type = FieldType.Long)
    private long id;
    @Field(index = true,store = false,analyzer = "ik_max_word")
    private String name;
    @Field(index = true,store = false,type = FieldType.Long)
    private int age;
    @Field(index = true,store = false,analyzer = "ik_max_word")
    private String sex;
    @Field(index = true,store = false,analyzer = "ik_max_word")
    private String address;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}
