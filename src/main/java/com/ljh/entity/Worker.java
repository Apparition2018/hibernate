package com.ljh.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Worker
 * 
 * 演示映射组成关系
 *
 * @author ljh
 * created on 2020/3/9 16:04
 */
@Getter
@Setter
public class Worker {
    
    private Integer id;
    private String name;
    
    private Pay pay;
}
