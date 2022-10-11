package com.ljh.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Account
 *
 * @author ljh
 * created on 2022/10/11 10:49
 */
@Getter
@Setter
@Accessors(chain = true)
public class Account {

    private Integer id;
    private String username;
    private int balance;
}
