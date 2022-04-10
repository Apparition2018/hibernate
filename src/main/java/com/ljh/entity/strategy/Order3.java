package com.ljh.entity.strategy;

import lombok.Data;

/**
 * Order
 * <p>
 * 演示双向一对多检索策略
 *
 * @author ljh
 * created on 2020/3/27 15:34
 */
@Data
public class Order3 {
    private Integer orderId;
    private String orderName;
    private Customer3 customer;
}
