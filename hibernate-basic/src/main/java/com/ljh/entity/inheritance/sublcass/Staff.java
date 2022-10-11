package com.ljh.entity.inheritance.sublcass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Staff
 * <p>
 * 演示 subclass 继承关系
 *
 * @author ljh
 * created on 2020/3/26 11:30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Staff extends Person {
    private String company;
}
