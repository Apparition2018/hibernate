package com.ljh.entity.one2one;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Manager
 * 演示基于外键的一对一关联关系
 *
 * @author ljh
 * created on 2022/10/14 16:43
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "jpa_manager")
public class Manager {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Column(name = "mgr_name")
    private String mgrName;
    // 没有外键的一方使用（不维护关系），使用 mappedBy
    @OneToOne(mappedBy = "mgr", fetch = FetchType.LAZY)
    private Department dept;
}
