package com.ljh.entity.inheritance.joined.subclass;

import lombok.Getter;
import lombok.Setter;

/**
 * Person
 * 
 * 演示 joined-subclass 继承关系
 *
 * @author ljh
 * created on 2020/3/26 11:29
 */
@Getter
@Setter
public class Person2 {
    
    private Integer id;
    private String name;
    private int age;
}
