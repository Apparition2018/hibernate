package com.ljh.entity.one2many.ba;

import lombok.Data;

/**
 * Order
 * <p>
 * 演示双向一对多关联关系
 *
 * @author ljh
 * created on 2020/3/10 10:26
 */
@Data
public class Order2 {
    private Integer orderId;
    private String orderName;
    private Customer2 customer;
}
