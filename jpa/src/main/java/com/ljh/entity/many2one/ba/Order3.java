package com.ljh.entity.many2one.ba;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Order3
 * 演示双向多对一关联关系
 *
 * @author ljh
 * created on 2022/10/14 15:32
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "jpa_order3")
public class Order3 {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String orderName;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer3 customer;
}
