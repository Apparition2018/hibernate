package com.ljh.entity.many2many.ba;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Item
 * <p>
 * 演示双向多对多关联关系
 *
 * @author ljh
 * created on 2020/3/16 16:01
 */
@Data
public class Item2 {
    private Integer id;
    private String name;
    private Set<Category2> categories = new HashSet<>();
}
