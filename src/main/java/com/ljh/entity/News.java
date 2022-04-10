package com.ljh.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.Date;

/**
 * News
 *
 * @author Arsenal
 * created on 2019/11/21 16:08
 */
@Data
@NoArgsConstructor
public class News {
    private Integer id;
    private String title;
    private String author;
    private Date date;
    // 该属性值为，title：author
    private String desc;
    // 大文本
    private String content;
    // 二进制数据
    private Blob image;

    public News(String title, String author, Date date) {
        this.title = title;
        this.author = author;
        this.date = date;
    }
}
