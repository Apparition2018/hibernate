package com.ljh.entity.one2one;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Department
 * 演示基于外键的一对一关联关系
 *
 * @author ljh
 * created on 2022/10/14 16:42
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "jpa_department")
public class Department {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Column(name = "dept_name")
    private String deptName;
    // @OneToOne 默认 FetchType.EAGER
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mgr_id", unique = true)
    private Manager mgr;
}
