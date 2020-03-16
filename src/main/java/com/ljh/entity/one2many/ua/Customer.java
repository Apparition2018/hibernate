package com.ljh.entity.one2many.ua;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Customer
 * 
 * 演示单向一对多关联关系
 *
 * @author ljh
 * created on 2020/3/10 10:25
 */
@Getter
@Setter
public class Customer {

    private Integer customerId;
    private String customerName;

}
