package com.ljh.entity.many2one.ba;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Customer
 * <p>
 * 演示双向多对一关联关系
 *
 * @author ljh
 * created on 2020/3/10 10:25
 */
@Getter
@Setter
public class Customer2 {
    private Integer customerId;
    private String customerName;
    /*
     * 1. 声明集合类型时，需使用接口类型，因为 Hibernate 在获取集合类型时，返回的是 Hibernate 内置的集合类型，而不是 JavaSE 的集合类型
     *  1.1 Hibernate 的内置集合类具有集合代理功能，和支持延迟检索策略
     *  1.2 Hibernate 的内置集合类封装了 JDK 中的集合类，这使得 Hibernate 能够对缓存中的集合对象进行脏检查，按照集合对象的状态来同步更新数据库
     * 2. 定义集合属性时，进行初始化，可以防止 NullPointerException
     */
    private Set<Order2> orders = new HashSet<>();
}
