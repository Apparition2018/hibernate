package com.ljh.entity.query.way;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

/**
 * Department
 * <p>
 * 演示检索方式
 *
 * @author ljh
 * created on 2022/9/7 15:18
 */
@Getter
@Setter
@Accessors(chain = true)
public class Department3 {
    private Integer id;
    private String name;
    private Set<Employee3> emps = new HashSet<>();

    @Override
    public String toString() {
        return "Department3{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
