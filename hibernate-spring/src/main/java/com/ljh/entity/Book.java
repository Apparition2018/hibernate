package com.ljh.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Book
 *
 * @author ljh
 * created on 2022/10/11 10:51
 */
@Getter
@Setter
@Accessors(chain = true)
public class Book {

    private Integer id;
    private String bookName;
    private String isbn;
    private int price;
    private int stock;
}
