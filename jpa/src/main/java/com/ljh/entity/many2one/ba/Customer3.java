package com.ljh.entity.many2one.ba;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Customer3
 * 演示双向多对一关联关系
 *
 * @author ljh
 * created on 2022/10/14 15:31
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "jpa_customer3")
public class Customer3 {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    // mappedBy：表示放弃维护关系，由另一方来维护关系；注：使用了 mappedBy，不能再使用 @JoinColumn
    @OneToMany(mappedBy = "customer")
    private Set<Order3> orders = new HashSet<>();
}
