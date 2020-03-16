package com.ljh.entity.many2many;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Category
 * 
 * 演示多对多关联关系
 *
 * @author ljh
 * created on 2020/3/16 16:01
 */
@Getter
@Setter
public class Category {
    
    private Integer id;
    private String name;
    
    private Set<Item> items = new HashSet<>();
}
