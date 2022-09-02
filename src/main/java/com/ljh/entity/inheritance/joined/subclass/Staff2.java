package com.ljh.entity.inheritance.joined.subclass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Staff
 * <p>
 * 演示 joined-subclass 继承关系
 *
 * @author ljh
 * created on 2020/3/26 11:30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Staff2 extends Person2 {
    private String company;
}
