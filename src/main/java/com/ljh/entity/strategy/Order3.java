package com.ljh.entity.strategy;

import lombok.Getter;
import lombok.Setter;

/**
 * Order
 *
 * 演示双向一对多检索策略
 *
 * @author ljh
 * created on 2020/3/27 15:34
 */
@Getter
@Setter
public class Order3 {
    
    private Integer orderId;
    private String orderName;
    
    private Customer3 customer;
}
