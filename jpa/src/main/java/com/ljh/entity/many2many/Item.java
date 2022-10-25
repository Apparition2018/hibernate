package com.ljh.entity.many2many;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Item
 * 演示双向多对多关联关系
 *
 * @author ljh
 * created on 2022/10/19 14:54
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "jpa_item")
public class Item {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String itemName;
    // 中间表
    @JoinTable(name = "jpa_item_category",
            joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    // 不持有外键，所以 @ManyToMany 默认 fetch = FetchType.LAZY
    @ManyToMany
    private Set<Category> categories = new HashSet<>();
}
