package com.ljh.entity.one2one.foreign;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Department
 * <p>
 * 演示基于外键的一对一关联关系
 *
 * @author ljh
 * created on 2020/3/11 10:32
 */
@Getter
@Setter
@Accessors(chain = true)
public class Department {
    private Integer deptId;
    private String deptName;
    private Manager mgr;
}
