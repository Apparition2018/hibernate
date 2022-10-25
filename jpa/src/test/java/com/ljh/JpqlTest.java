package com.ljh;

import com.ljh.entity.many2one.ba.Customer3;
import com.ljh.entity.many2one.ua.Customer;
import org.hibernate.jpa.QueryHints;
import org.junit.Test;

import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

/**
 * JPQL - Java Persistence Query Language
 *
 * @author ljh
 * created on 2022/10/20 14:48
 */
public class JpqlTest extends BaseTest {

    @Test
    public void testJpql() {
        String jpql = "FROM Customer c WHERE c.age > ?1";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1, 1);
        List<Customer> customerList = query.getResultList();
        System.err.println(customerList.size());
    }

    @Test
    public void testProjection() {
        // 投影查询返回 List<Object[]>
        String jpql = "SELECT c.lastName, c.age FROM Customer c WHERE c.id > ?1";
        List<Object[]> customerList = entityManager.createQuery(jpql).setParameter(1, 1).getResultList();
        customerList.forEach(c -> System.err.println(Arrays.toString(c)));

        // 投影查询返回 List<Entity>
        jpql = "SELECT new Customer(c.lastName, c.age) FROM Customer c WHERE c.id > ?1";
        List<Customer> customerList2 = entityManager.createQuery(jpql).setParameter(1, 1).getResultList();
        customerList2.forEach(c -> System.err.printf("%s，%s%n", c.getLastName(), c.getAge()));
    }

    @Test
    public void testNativeQuery() {
        String sql = "SELECT * FROM jpa_customer WHERE id = ?1";
        Query query = entityManager.createNativeQuery(sql, Customer.class).setParameter(1, 1);
        System.err.println(query.getSingleResult());

    }

    @Test
    public void testNamedQuery() {
        Query query = entityManager.createNamedQuery("queryById").setParameter(1, 1);
        System.err.println(query.getSingleResult());
    }

    @Test
    public void testSetHintCacheable() {
        String jpql = "FROM Customer c WHERE c.age > ?1";

        Query query = entityManager.createQuery(jpql)
                // 使用 query cache
                .setHint(QueryHints.HINT_CACHEABLE, true)
                .setParameter(1, 1);
        // 发送 SELECT 语句
        List<Customer> customerList = query.getResultList();
        System.err.println(customerList.size());

        // 不会发送 SELECT 语句
        customerList = query.getResultList();
        System.err.println(customerList.size());
    }

    @Test
    public void testJoinFetch() {
        String jpql = "FROM Customer3 c LEFT JOIN FETCH c.orders WHERE c.id = ?1";

        Customer3 customer = entityManager.createQuery(jpql, Customer3.class).setParameter(1, 1).getSingleResult();
        System.err.println(customer.getOrders().size());

        List<Object[]> customerList = entityManager.createQuery(jpql).setParameter(1, 1).getResultList();
        System.err.println(customerList);
    }
}
