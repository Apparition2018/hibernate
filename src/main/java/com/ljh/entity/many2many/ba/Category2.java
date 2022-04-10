package com.ljh.entity.many2many.ba;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Category
 * <p>
 * 演示双向多对多关联关系
 *
 * @author ljh
 * created on 2020/3/16 16:01
 */
@Data
public class Category2 {
    private Integer id;
    private String name;
    // 双向多对多在两个段都设置集合属性
    private Set<Item2> items = new HashSet<>();
}
