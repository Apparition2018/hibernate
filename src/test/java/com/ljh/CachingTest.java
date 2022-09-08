package com.ljh;

import com.ljh.entity.query.strategy.Customer3;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * 缓存 Caching
 *
 * @author ljh
 * created on 2022/9/8 11:24
 */
public class CachingTest extends BaseTest {

    /**
     * 二级缓存 类级别
     * <class-cache class usage/>
     */
    @Test
    public void testSecondLevelCachingClass() {
        // 第一次查询，发送 SELECT 语句
        Customer3 customer = session.get(Customer3.class, 1);
        System.out.println(customer);

        this.closeAndOpenNewSession();

        // 第二次查询，从二级缓存获取，不发送 SELECT 语句
        customer = session.get(Customer3.class, 1);
        System.out.println(customer);
    }

    /**
     * 二级缓存 集合级别
     * <class-cache class usage/>
     * <collection-cache collection usage/>
     */
    @Test
    public void testSecondLevelCachingCollection() {
        // 第一次查询 Customer3，发送 SELECT 语句
        Customer3 customer = session.get(Customer3.class, 1);
        System.out.println(customer.getCustomerName());
        // 第一次访问 orders，发送 SELECT 语句
        System.out.println(customer.getOrders().size());

        this.closeAndOpenNewSession();

        // 第二次查询 Customer3，从二级缓存获取，不发送 SELECT 语句
        customer = session.get(Customer3.class, 1);
        System.out.println(customer.getCustomerName());
        // 第二次访问 orders，从二级缓存获取，不发送 SELECT 语句
        System.out.println(customer.getOrders().size());
    }

    /**
     * 查询缓存
     */
    @Test
    public void testQueryCache() {
        Query<Customer3> query = session.createQuery("FROM Customer3");
        // 方式一：Jakarta Persistence
        // query.setHint("org.hibernate.cacheable", true);
        // 方式二：Hibernate native API
        query.setCacheable(true);
        // 第一次查询，发送 SELECT 语句
        List<Customer3> customers = query.list();
        System.out.println(customers.size());
        // 由于开启了查询缓存，且 query.setCacheable(true)
        // 所以第二次查询，从二级缓存获取，不发送 SELECT 语句
        customers = query.list();
        System.out.println(customers.size());
    }

    /**
     * 时间戳缓存区域
     * 存放了对查询结果相关的表进行插入、更新或删除操作的时间戳
     * Hibernate 通过时间戳缓存区域来判断被缓存的查询结果是否过期
     * 栗子
     * 1. T1 时刻对表A SELECT，把结果放在 QueryCache，并记录时间戳 T1
     * 2. T2 时刻对表A UPDATE，在 UpdateTimestampCache 记录时间戳 T2
     * 3. T3 时刻对表A SELECT，比较 T1 和 T2。若 T2 > T1，则丢弃 QueryCache 的查询结果，重新查询并缓存新的结果。若 T2 < T1，直接返回 QueryCache 中的结果
     */
    @Test
    public void testUpdateTimestampCache() {
        Query<Customer3> query = session.createQuery("FROM Customer3");
        query.setCacheable(true);
        // 查询 CUSTOMER3 表，缓存结果
        List<Customer3> customers = query.list();
        System.out.println(customers.size());
        // 更新 CUSTOMER3 一条记录
        Customer3 customer = session.get(Customer3.class, 2);
        customer.setCustomerName("BBB");
        // 由于在查询后有更新，判断缓存结果过期，重新查询并缓存新的结果
        customers = query.list();
        System.out.println(customers.size());
    }

    /**
     * iterate
     */
    @Test
    public void testIterate() {
        Customer3 customer = session.get(Customer3.class, 1);
        System.out.println(customer.getCustomerName());
        System.out.println(customer.getOrders().size());

        Query<Customer3> query = session.createQuery("FROM Customer3 c WHERE c.customerId = 1");
        // 没有指定查询字段时，list() 会查询所有字段
        // select CUSTOMER_ID, CUSTOMER_NAME from CUSTOMER3 where CUSTOMER_ID=1
        List<Customer3> customers = query.list();
        System.out.println(customers.size());

        // 没有指定查询字段时，iterate() 只会查询 OID 字段
        // select CUSTOMER_ID from CUSTOMER3 where CUSTOMER_ID in (1, 2, 3)
        // 得到 CUSTOMER_ID 结果集后，先到 Session 缓存和二级缓存中查看是否存在 OID 对应的对象，存在则直接返回，不存在则根据 CUSTOMER_ID 从数据库查询
        query = session.createQuery("FROM Customer3 c WHERE c.customerId in (1, 2, 3)");
        Iterator<Customer3> customerIterator = query.iterate();
        while (customerIterator.hasNext()) {
            // CUSTOMER_ID=1，直接返回缓存数据
            // CUSTOMER_ID=2，select CUSTOMER_ID, CUSTOMER_NAME from CUSTOMER3 where CUSTOMER_ID=2
            // CUSTOMER_ID=3，select CUSTOMER_ID, CUSTOMER_NAME from CUSTOMER3 where CUSTOMER_ID=3
            System.out.println(customerIterator.next());
        }
    }
}
