package com.ljh.entity.one2one.primary;

import lombok.Getter;
import lombok.Setter;

/**
 * Manager
 * 
 * 演示基于主键的一对一关联关系
 *
 * @author ljh
 * created on 2020/3/13 17:31
 */
@Getter
@Setter
public class Manager2 {
    
    private Integer mgrId;
    private String mgrName;
    
    private Department2 dept;
}
