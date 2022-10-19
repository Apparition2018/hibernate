package com.ljh;

import com.ljh.entity.many2one.ua.Customer;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JpaTest
 *
 * @author ljh
 * created on 2022/10/12 16:48
 */
public class JpaTest {

    /**
     * JPA 开发基本步骤
     */
    @Test
    public void test() {
        // 1. 创建 EntityManagerFactory
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.show_sql", false);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa", properties);
        // 2. 创建 EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // 3. 开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 执行操作
        Customer customer = new Customer();
        customer.setLastName("LJH");
        customer.setEmail("ljh@163.com");
        customer.setAge(30);
        customer.setGender(Customer.GenderEnum.MALE);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        entityManager.persist(customer);
        // 5. 提交事务
        transaction.commit();
        // 6. 关闭 EntityManager
        entityManager.close();
        // 7. 关闭 EntityManagerFactory
        entityManagerFactory.close();
    }
}
