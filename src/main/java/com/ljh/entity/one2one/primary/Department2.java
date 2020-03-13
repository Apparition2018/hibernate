package com.ljh.entity.one2one.primary;

import lombok.Getter;
import lombok.Setter;

/**
 * Department
 * 
 * 演示基于主键的一对一关联关系
 *
 * @author ljh
 * created on 2020/3/13 17:32
 */
@Getter
@Setter
public class Department2 {
    
    private Integer deptId;
    private String deptName;
    
    private Manager2 mgr;
}
