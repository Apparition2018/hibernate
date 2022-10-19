package com.ljh.entity.one2many.ua;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Order2
 * 演示单向一对多关联关系
 *
 * @author ljh
 * created on 2022/10/14 14:33
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "jpa_order2")
public class Order2 {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Column(name = "order_name")
    private String orderName;
}
