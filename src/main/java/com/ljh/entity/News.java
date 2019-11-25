package com.ljh.entity;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * News
 *
 * @author ljh
 * created on 2019/11/21 16:08
 */
@ToString
@NoArgsConstructor
public class News {

    private Integer id;
    private String title;
    private String author;
    private Date date;

    public News(String title, String author, Date date) {
        this.title = title;
        this.author = author;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
