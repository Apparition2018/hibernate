package com.ljh.entity.inheritance.union.subclass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Staff
 * <p>
 * 演示 union-subclass 继承关系
 *
 * @author ljh
 * created on 2020/3/27 14:29
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Staff3 extends Person3 {
    private String company;
}
