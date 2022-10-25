package com.ljh.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Person
 *
 * @author ljh
 * created on 2019/11/13 14:49
 */
@Getter
@Setter
@ToString
@Table(name = "JPA_PERSONS")
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Integer id;
    private String lastName;
    private String email;
    private Date birth;
    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;
    @Column(name = "ADD_ID")
    private Integer addressId;
}
