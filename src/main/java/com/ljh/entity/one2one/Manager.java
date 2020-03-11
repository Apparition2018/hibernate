package com.ljh.entity.one2one;

import lombok.Getter;
import lombok.Setter;

/**
 * Manager
 * 
 * 演示一对一关联关系
 *
 * @author ljh
 * created on 2020/3/11 10:31
 */
@Getter
@Setter
public class Manager {
    
    private Integer mgrId;
    private String mgrName;
    
    private Department dept;
}
