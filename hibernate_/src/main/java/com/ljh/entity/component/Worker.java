package com.ljh.entity.component;

import lombok.Data;

/**
 * Worker
 * <p>
 * 演示组成关系
 *
 * @author ljh
 * created on 2020/3/9 16:04
 */
@Data
public class Worker {
    private Integer id;
    private String name;
    private Pay pay;
}
