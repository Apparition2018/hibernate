package com.ljh.entity.many2one.ua;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Order
 * 演示单向多对一关联关系
 *
 * @author ljh
 * created on 2022/10/14 11:14
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "jpa_order")
public class Order {

    @TableGenerator(
            name = "orderGen",
            table = "id_gen",
            pkColumnName = "sequence_name",
            pkColumnValue = "order_id",
            valueColumnName = "next_val",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "orderGen")
    @Id
    private Integer id;
    private String orderName;
    // fetch = FetchType.EAGER：左关联查询出关联对象
    // fetch = FetchType.LAZY：使用时根据外键查询出关联对象
    // 多对一 ，持有外键，通过外键可查询出关联对象，所以 @ManyToOne 默认 fetch = FetchType.EAGER
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
