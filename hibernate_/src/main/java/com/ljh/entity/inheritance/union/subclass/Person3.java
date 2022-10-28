package com.ljh.entity.inheritance.union.subclass;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Person3
 * <p>
 * 演示 union-subclass 继承关系
 *
 * @author ljh
 * created on 2020/3/27 14:21
 */
@Data
@Accessors(chain = true)
public class Person3 {
    private Integer id;
    private String name;
    private int age;
}
