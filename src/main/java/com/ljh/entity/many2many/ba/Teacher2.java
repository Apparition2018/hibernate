package com.ljh.entity.many2many.ba;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

/**
 * Teacher
 * <p>
 * 演示双向多对多关联关系
 *
 * @author ljh
 * created on 2020/3/16 16:01
 */
@Getter
@Setter
@Accessors(chain = true)
public class Teacher2 {
    private Integer id;
    private String name;
    private Set<Student2> students = new HashSet<>();
}
