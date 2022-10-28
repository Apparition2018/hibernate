package com.ljh.entity.many2many.ua;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Student
 * <p>
 * 演示单向多对多关联关系
 *
 * @author ljh
 * created on 2020/3/16 16:01
 */
@Data
@Accessors(chain = true)
public class Student {
    private Integer id;
    private String name;
}
