package com.ljh.entity;

import javax.persistence.*;

/**
 * Customer
 *
 * @author ljh
 * created on 2022/10/12 16:34
 */
@Table(name = "JPA_CUSTOMERS")
@Entity
public class Customer {

    private Integer id;
    private String lastName;
    private String email;
    private int age;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
