package com.ljh.dao;

import com.ljh.entity.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * PersonDao
 *
 * @author ljh
 * created on 2022/10/24 16:12
 */
@Repository
public class PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Person person) {
        entityManager.persist(person);
    }
}
