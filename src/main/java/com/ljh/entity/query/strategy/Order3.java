package com.ljh.entity.query.strategy;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * Order
 * <p>
 * 演示检索策略
 *
 * @author ljh
 * created on 2020/3/27 15:34
 */
@Getter
@Setter

@Accessors(chain = true)
public class Order3 {
    private Integer orderId;
    private String orderName;
    private Customer3 customer;

    /**
     * 重写 equals，如果 orderId 相等即表示对象相等
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order3 order3 = (Order3) o;
        return orderId.equals(order3.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
