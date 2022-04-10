package com.ljh.entity.many2many.ua;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Category
 * <p>
 * 演示单向多对多关联关系
 *
 * @author ljh
 * created on 2020/3/16 16:01
 */
@Data
public class Category {
    private Integer id;
    private String name;
    private Set<Item> items = new HashSet<>();
}
