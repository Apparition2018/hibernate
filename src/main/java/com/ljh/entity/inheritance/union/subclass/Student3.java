package com.ljh.entity.inheritance.union.subclass;

import lombok.Getter;
import lombok.Setter;

/**
 * Student3
 *
 * 演示 union-subclass 继承关系
 *
 * @author ljh
 * created on 2020/3/27 14:29
 */
@Getter
@Setter
public class Student3 extends Person3 {

    private String school;
}
