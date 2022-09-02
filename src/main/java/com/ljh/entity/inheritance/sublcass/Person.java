package com.ljh.entity.inheritance.sublcass;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Person
 * <p>
 * 演示 subclass 继承关系
 *
 * @author ljh
 * created on 2020/3/26 11:29
 */
@Data
@Accessors(chain = true)
public class Person {
    private Integer id;
    private String name;
    private int age;
}
