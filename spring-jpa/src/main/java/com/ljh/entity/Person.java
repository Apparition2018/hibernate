package com.ljh.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Person
 *
 * @author ljh
 * created on 2022/10/24 15:58
 */
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "jpa_person")
@Entity
public class Person {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String lastName;
    private String email;
    private int age;
}
