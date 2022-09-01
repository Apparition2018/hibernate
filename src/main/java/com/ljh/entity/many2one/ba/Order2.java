package com.ljh.entity.many2one.ba;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Order
 * <p>
 * 演示双向多对一关联关系
 *
 * @author ljh
 * created on 2020/3/10 10:26
 */
@Getter
@Setter
@Accessors(chain = true)
public class Order2 {
    private Integer orderId;
    private String orderName;
    private Customer2 customer;
}
