package com.ljh.entity.many2one.ua;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Customer
 * 演示单向多对一关联关系
 *
 * @author ljh
 * created on 2022/10/12 16:34
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "jpa_customer")
@NamedQuery(name = "queryById", query = "FROM Customer c WHERE c.id = ?1")
public class Customer {

    @TableGenerator(
            name = "customerGen",
            // 存储生成主键值的表名，默认为 name 属性值，没有使用 @TableGenerator 则默认为 hibernate_sequences
            table = "id_gen",
            // 主键列名称，默认为 sequence_name
            pkColumnName = "sequence_name",
            // 主键列值，默认为当前表名，没有使用 @TableGenerator 则默认为 default
            pkColumnValue = "customer_id",
            // 存储下一个主键值的列名称，默认为 next_val
            valueColumnName = "next_val",
            // 主键值增长大小
            allocationSize = 1
    )
    // GenerationType.TABLE：使用表来生成并记录主键
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "customerGen")
    @Id
    private Integer id;
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
    @Basic
    private String email;
    private int age;
    // 不使用 @Enumerated 时，默认使用枚举的 ordinal 属性
    // 使用 @Enumerated 且指定 EnumType.STRING 时，使用枚举的 name 属性
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderEnum gender;
    @Temporal(TemporalType.DATE)
    private Date birth;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Customer(String lastName, int age) {
        this.lastName = lastName;
        this.age = age;
    }

    @Getter
    public enum GenderEnum {
        MALE, FEMALE
    }
}
