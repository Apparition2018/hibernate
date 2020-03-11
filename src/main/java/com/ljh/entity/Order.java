package com.ljh.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Order
 * 
 * 演示一对多关联关系
 *
 * @author ljh
 * created on 2020/3/10 10:26
 */
@Getter
@Setter
public class Order {
    
    private Integer orderId;
    private String orderName;
    
    private Customer customer;
}
