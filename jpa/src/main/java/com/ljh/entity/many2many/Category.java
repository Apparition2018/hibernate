package com.ljh.entity.many2many;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Category
 * 演示双向多对多关联关系
 *
 * @author ljh
 * created on 2022/10/19 14:53
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "jpa_category")
public class Category {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Column(name = "category_name")
    private String categoryName;
    @ManyToMany(mappedBy = "categories")
    private Set<Item> items = new HashSet<>();
}
