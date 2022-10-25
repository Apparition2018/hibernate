package com.ljh.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Address
 *
 * @author ljh
 * created on 2019/11/15 10:17
 */
@Getter
@Setter
@ToString
@Table(name = "JPA_ADDRESS")
@Entity
public class Address {

    @GeneratedValue
    @Id
    private Integer id;
    private String province;
    private String city;
}
