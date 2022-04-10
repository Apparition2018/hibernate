package com.ljh.entity.inheritance.joined.subclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Student
 * <p>
 * 演示 joined-subclass 继承关系
 *
 * @author ljh
 * created on 2020/3/26 11:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Student2 extends Person2 {
    private String school;
}
