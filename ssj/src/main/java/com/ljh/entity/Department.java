package com.ljh.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Department
 *
 * @author ljh
 * created on 2022/10/26 14:52
 */
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "ssj_department")
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String deptName;
}
