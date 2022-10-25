package com.ljh.repositories.custom;

import com.ljh.entity.Person;
import com.ljh.repositories.custom.dao.PersonDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * PersonRepositoryImpl
 *
 * @author ljh
 * created on 2019/11/15 17:16
 */
public class PersonRepositoryImpl implements PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void custom() {
        Person person = entityManager.find(Person.class, 11);
        System.out.println("person = " + person);
    }
}
