package com.ljh.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Blob;
import java.util.Date;

/**
 * News
 * <p>
 * 演示持久化 Java 类
 * 1.无参构造器：使 Hibernate 可以使用 Constructor.newInstance() 来实例化持久化类
 * 2.标识属性：identifier property，通常映射为数据表的主键字段
 * 3.声明持久化类字段的访问方法：getter/setter
 * 4.非 final 类：如果是 final 类，无法生成 cglib 代理
 * <p>
 * 因为不要求持久化类继承类或先实现接口，所以 Hibernate 被称为低侵入式设计
 *
 * @author Arsenal
 * created on 2019/11/21 16:08
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class News {
    private Integer id;
    private String title;
    private String author;
    // Java Type            SQL Type
    // java.sql.Date        DATE
    // java.sql.Time        TIME
    // java.sql.Timestamp   TIMESTAMP
    // java.util.Date 是以上三个类的父类
    private Date date;
    // 计算属性：title: author
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
