package com.ljh.entity.many2one.ua;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Order
 * <p>
 * 演示单向多对一关联关系
 *
 * @author ljh
 * created on 2020/3/10 10:26
 */
@Data
@Accessors(chain = true)
public class Order {
    private Integer orderId;
    private String orderName;
    private Customer customer;
}
