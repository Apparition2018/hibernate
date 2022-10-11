package com.ljh.entity.query.way;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Employee
 * <p>
 * 演示检索方式
 *
 * @author ljh
 * created on 2022/9/7 15:18
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Employee3 {
    private Integer id;
    private String name;
    private float salary;
    private String email;
    private Department3 dept;

    public Employee3(float salary, String email, Department3 dept) {
        this.salary = salary;
        this.email = email;
        this.dept = dept;
    }
}
