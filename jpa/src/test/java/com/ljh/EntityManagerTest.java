package com.ljh;

import com.ljh.entity.many2one.ua.Customer;
import org.junit.Test;

import java.util.Date;

/**
 * EntityManagerTest
 *
 * @author ljh
 * created on 2022/10/13 14:58
 */
public class EntityManagerTest extends BaseTest {

    /**
     * 等同于 hibernate 的 session.get()
     */
    @Test
    public void testFind() {
        Customer customer = entityManager.find(Customer.class, 1);
        System.out.println(customer.getClass().getName());
        System.err.println("--------------------");
        System.out.println(customer);
    }

    /**
     * 类似于 hibernate 的 session.load() {@link SessionMethodTest#testGetAndLoad()}
     * 查找对象的 OID 在数据不存在时，
     * entityManager.getReference() 抛出 EntityNotFoundException
     * session.load() 抛出 ObjectNotFoundException
     */
    @Test
    public void testGetReference() {
        Customer customer = entityManager.getReference(Customer.class, 1);
        // com.ljh.entity.many2one.ua.Customer$HibernateProxy$sDCmzSPf 代理对象
        System.out.println(customer.getClass().getName());
        System.err.println("--------------------");
        System.out.println(customer);

        customer = entityManager.getReference(Customer.class, -1);
        // javax.persistence.EntityNotFoundException: Unable to find com.ljh.entity.many2one.ua.Customer with id -1
        System.out.println(customer);
    }

    /**
     * 等同于 hibernate 的 session.persist()
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
     * entityManager.remove() 不能删除游离对象，session.delete() 可以
     */
    @Test
    public void testRemove() {
        Customer customer = entityManager.find(Customer.class, 1);
        // 持久化 → 游离
        entityManager.detach(customer);
        // IllegalArgumentException: Removing a detached instance com.ljh.entity.many2one.ua.Customer#1
        entityManager.remove(customer);
    }

    /**
     * 等同于 hibernate 的 session.merge()
     * 1. 临时对象：创建一个新的对象，把临时对象的属性复制到新的对象中，然后执行 INSERT 操作
     */
    @Test
    public void testMergeTransient() {
        Customer customer = new Customer();
        customer.setLastName("CC");
        customer.setEmail("cc@163.com");
        customer.setAge(18);
        customer.setGender(Customer.GenderEnum.MALE);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Customer customer2 = entityManager.merge(customer);
        System.out.println(customer.getId());   // null
        System.out.println(customer2.getId());  // 3
    }

    /**
     * 2 游离对象
     * -    2.1 若 EntityManager 缓存和数据库都没有，则和 merge 临时对象时相同
     */
    @Test
    public void testMergeDetached() {
        Customer customer = new Customer();
        customer.setLastName("DD");
        customer.setEmail("dd@163.com");
        customer.setAge(18);
        customer.setGender(Customer.GenderEnum.MALE);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        customer.setId(-1);
        Customer customer2 = entityManager.merge(customer);
        System.out.println(customer.getId());   // -1
        System.out.println(customer2.getId());  // 4
    }

    /**
     * 2 游离对象
     * -    2.2 若 EntityManager 缓存中没有，数据库中有，查询对应的记录并返回对象，把游离对象的属性复制到查询到的对象中，然后执行 UPDATE 操作
     */
    @Test
    public void testMergeDetached2() {
        Customer customer = new Customer();
        customer.setLastName("DDD");
        customer.setEmail("dd@163.com");
        customer.setAge(18);
        customer.setGender(Customer.GenderEnum.MALE);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        customer.setId(4);
        Customer customer2 = entityManager.merge(customer);
        System.out.println(customer == customer2); // false
    }

    /**
     * 2 游离对象
     * -    2.3 若 EntityManager 缓存中有，把游离对象的属性复制到缓存的对象中，然后执行 UPDATE 操作
     */
    @Test
    public void testMergeDetached3() {
        Customer customer = new Customer();
        customer.setLastName("DDD");
        customer.setEmail("ddd@163.com");
        customer.setAge(18);
        customer.setGender(Customer.GenderEnum.MALE);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        customer.setId(4);
        Customer customer2 = entityManager.find(Customer.class, 4);
        entityManager.merge(customer);
        System.out.println(customer == customer2); // false
    }
}
