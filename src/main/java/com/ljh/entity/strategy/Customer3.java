package com.ljh.entity.strategy;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Customer
 * 
 * 演示双向一对多检索策略
 *
 * @author ljh
 * created on 2020/3/27 15:32
 */
@Getter
@Setter
public class Customer3 {
    
    private Integer customerId;
    private String customerName;
    
    private Set<Order3> orders = new HashSet<>();
}
