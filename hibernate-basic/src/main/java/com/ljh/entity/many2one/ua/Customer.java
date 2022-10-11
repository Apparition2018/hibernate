package com.ljh.entity.many2one.ua;

import lombok.Data;

/**
 * Customer
 * <p>
 * 演示单向多对一关联关系
 *
 * @author ljh
 * created on 2020/3/10 10:25
 */
@Data
public class Customer {
    private Integer customerId;
    private String customerName;
}
