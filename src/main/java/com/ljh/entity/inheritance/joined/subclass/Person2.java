package com.ljh.entity.inheritance.joined.subclass;

import lombok.Data;

/**
 * Person
 * <p>
 * 演示 joined-subclass 继承关系
 *
 * @author ljh
 * created on 2020/3/26 11:29
 */
@Data
public class Person2 {
    private Integer id;
    private String name;
    private int age;
}
