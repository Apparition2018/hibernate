package com.ljh.entity.one2many.ua;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Customer2
 * 演示单向一对多关联关系
 *
 * @author ljh
 * created on 2022/10/14 14:32
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "jpa_customer2")
public class Customer2 {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    // 不持有外键，所以 @OneToMany 默认 fetch = FetchType.LAZY
    @OneToMany
    @JoinColumn(name = "customer_id")
    private Set<Order2> orders = new HashSet<>();
}
