package com.ljh.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * Employee
 *
 * @author ljh
 * created on 2022/10/26 14:50
 */
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "ssj_employee")
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String lastName;
    private String email;
    @Temporal(TemporalType.DATE)
    private Date birth;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @JoinColumn(name = "department_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Department dept;
}
