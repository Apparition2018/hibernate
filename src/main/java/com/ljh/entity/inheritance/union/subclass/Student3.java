package com.ljh.entity.inheritance.union.subclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Student3
 * <p>
 * 演示 union-subclass 继承关系
 *
 * @author ljh
 * created on 2020/3/27 14:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Student3 extends Person3 {
    private String school;
}
