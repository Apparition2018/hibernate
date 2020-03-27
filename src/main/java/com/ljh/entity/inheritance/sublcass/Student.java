package com.ljh.entity.inheritance.sublcass;

import lombok.Getter;
import lombok.Setter;

/**
 * Student
 * 
 * 演示 subclass 继承关系
 *
 * @author ljh
 * created on 2020/3/26 11:30
 */
@Getter
@Setter
public class Student extends Person {
    
    private String school;
}
