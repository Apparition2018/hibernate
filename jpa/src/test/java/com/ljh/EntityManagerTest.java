package com.ljh;

import com.ljh.entity.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

/**
 * EntityManagerTest
 *
 * @author ljh
 * created on 2022/10/13 14:58
 */
public class EntityManagerTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @After
    public void destroy() {
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * 类似于 hibernate 的 session.get()
     */
    @Test
    public void testFind() {
        Customer customer = entityManager.find(Customer.class, 1);
        System.out.println(customer.getClass().getName());
        System.err.println("--------------------");
        System.out.println(customer);
    }

    /**
     * 类似于 hibernate 的 session.load()
     */
    @Test
    public void testGetReference() {
        Customer customer = entityManager.getReference(Customer.class, 1);
        // com.ljh.entity.Customer$HibernateProxy$sDCmzSPf 代理对象
        System.out.println(customer.getClass().getName());
        System.err.println("--------------------");
        System.out.println(customer);
    }

    /**
     * 类似于 hibernate 的 session.persist()
     */
    @Test
    public void testPersist() {
        Customer customer = new Customer();
        customer.setLastName("BB");
        customer.setEmail("bb@163.com");
        customer.setAge(15);
        customer.setGender(Customer.GenderEnum.MALE);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        entityManager.persist(customer);
        entityManager.flush();
        System.out.println(customer.getId());
    }

    /**
     * 类似于 hibernate 的 session.delete()
     * 但 delete() 删除游离对象会抛出 IllegalArgumentException
     */
    @Test
    public void testRemove() {
        Customer customer = new Customer();
        customer.setId(2);
        // IllegalArgumentException: Removing a detached instance
        entityManager.remove(customer);
    }
}
