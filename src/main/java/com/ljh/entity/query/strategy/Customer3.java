package com.ljh.entity.query.strategy;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

/**
 * Customer
 * <p>
 * 演示检索策略
 *
 * @author ljh
 * created on 2020/3/27 15:32
 */
@Getter
@Setter
@Accessors(chain = true)
public class Customer3 {
    private Integer customerId;
    private String customerName;
    private Set<Order3> orders = new HashSet<>();
}
