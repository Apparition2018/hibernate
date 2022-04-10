package com.ljh.entity.one2one.primary;

import lombok.Data;

/**
 * Department
 * <p>
 * 演示基于主键的一对一关联关系
 *
 * @author ljh
 * created on 2020/3/13 17:32
 */
@Data
public class Department2 {
    private Integer deptId;
    private String deptName;
    private Manager2 mgr;
}
