package com.ljh.entity.many2many.ua;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

/**
 * Teacher
 * <p>
 * 演示单向多对多关联关系
 *
 * @author ljh
 * created on 2020/3/16 16:01
 */
@Data
@Accessors(chain = true)
public class Teacher {
    private Integer id;
    private String name;
    private Set<Student> students = new HashSet<>();
}
