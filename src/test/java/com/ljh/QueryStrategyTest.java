package com.ljh;

import com.ljh.entity.many2one.ua.Customer;
import com.ljh.entity.query.strategy.Customer3;
import com.ljh.entity.query.strategy.Order3;
import org.junit.Test;

import java.util.List;

/**
 * 检索策略
 *
 * @author ljh
 * created on 2022/9/2 17:17
 */
public class QueryStrategyTest extends BaseTest {

    /**
     * 类级别检索策略，<class lazy/>
     */
    @Test
    public void testClassLazy() {
        // load() 延迟检索，没有发送 SELECT 语句
        Customer customer = session.load(Customer.class, 1);
        // 代理对象 class com.ljh.entity.many2one.ua.Customer$HibernateProxy$tA0xWhHC
        System.out.println(customer.getClass());
        // 打印 OID，不会发送 SELECT 语句
        System.out.println(customer.getCustomerId());
        // 第一次访问非 OID 属性，发送 SELECT 语句
        System.out.println(customer.getCustomerName());
    }

    @Test
    public void testSetInitData() {
        Customer3 customer1 = new Customer3().setCustomerName("AA");
        Customer3 customer2 = new Customer3().setCustomerName("BB");
        Customer3 customer3 = new Customer3().setCustomerName("CC");
        Customer3 customer4 = new Customer3().setCustomerName("DD");
        session.save(customer1);
        session.save(customer2);
        session.save(customer3);
        session.save(customer4);
        session.save(new Order3().setOrderName("aa").setCustomer(customer1));
        session.save(new Order3().setOrderName("bb").setCustomer(customer1));
        session.save(new Order3().setOrderName("cc").setCustomer(customer1));
        session.save(new Order3().setOrderName("dd").setCustomer(customer2));
        session.save(new Order3().setOrderName("ee").setCustomer(customer2));
        session.save(new Order3().setOrderName("ff").setCustomer(customer2));
        session.save(new Order3().setOrderName("gg").setCustomer(customer3));
        session.save(new Order3().setOrderName("hh").setCustomer(customer3));
        session.save(new Order3().setOrderName("ii").setCustomer(customer3));
        session.save(new Order3().setOrderName("jj"));
    }

    /**
     * 一对多和多对多的检索策略，<set lazy/>
     */
    @Test
    public void testSetLazy() {
        Customer3 customer = session.load(Customer3.class, 1);
        // lazy="true" 时，会发送 SELECT ORDER_ID, ORDER_NAME, CUSTOMER_ID
        // lazy="extra" 会尽可能延迟加载 orders，所以这里只会发送 SELECT count(ORDER_ID)
        System.out.println(customer.getOrders().size());
        Order3 order = new Order3().setOrderId(1);
        // lazy="extra" 会尽可能延迟加载 orders，且重写了 Order3 的 equal()，ORDER_ID 相等即表示对象相等
        // 所以这里只会发送 SELECT 1 from ORDER3 where CUSTOMER_ID=? and ORDER_ID=?
        System.out.println(customer.getOrders().contains(order));
    }

    /**
     * 一对多和多对多的检索策略，<set batch-size/>
     */
    @Test
    public void testSetBatchSize() {
        List<Customer3> customers = session.createQuery("FROM Customer3").list();
        for (Customer3 customer : customers) {
            // lazy="true" 且没有设置 batch-size 时，会发送 customers.size() 条 select ... from ORDER3 where CUSTOMER_ID=?
            // lazy="true" 且设置 batch-size="5" 时，只会发送 (customer.size() / 5 + 1) 条 select ... from ORDER3 where CUSTOMER_ID in (?,?,?,?,?)
            System.out.println(customer.getOrders().size());
        }
    }

    /**
     * 一对多和多对多的检索策略，<set fetch/>
     */
    @Test
    public void testSetFetch() {
        List<Customer3> customers = session.createQuery("FROM Customer3").list();
        for (Customer3 customer : customers) {
            // lazy="true" 且 fetch="select" 时，发送 (customer.size() / 5 + 1) 条 select ... from ORDER3 where CUSTOMER_ID in (?,?...)
            // lazy="true" 且 fetch="subselect" 时，忽略 batch-size 属性，发送1条 select ... from ORDER3 where CUSTOMER_ID in (select CUSTOMER_ID from CUSTOMER3)
            System.out.println(customer.getOrders().size());
        }
    }

    /**
     * 一对多和多对多的检索策略，<set fetch="join"/>
     */
    @Test
    public void testSetFetchJoin() {
        Customer3 customer = session.load(Customer3.class, 1);
        // 没有设置 batch-size 属性，且 fetch="join"，忽略 lazy 属性
        // 发送左外连接查询语句 select ... from CUSTOMER3 c left outer join ORDER3 on c.CUSTOMER_ID=o.CUSTOMER_ID where c.CUSTOMER_ID=?
        System.out.println(customer.getOrders().size());
    }

    /**
     * 多对一和一对一的检索策略，<many-to-one fetch/>
     */
    @Test
    public void testManyToOneFetch() {
        Order3 order = session.load(Order3.class, 1);
        // fetch="join" 时，忽略 lazy 属性
        // 发送左外连接查询语句 select ... from ORDER3 o left outer join CUSTOMER3 c on o.CUSTOMER_ID=c.CUSTOMER_ID where o.ORDER_ID=?
        System.out.println(order.getCustomer().getCustomerName());
    }

    /**
     * 多对一和一对一的检索策略，<class batch-size/>
     */
    @Test
    public void testClassBatchSize() {
        List<Order3> orders = session.createQuery("FROM Order3").list();
        for (Order3 order : orders) {
            // 没有设置 batch-size 属性时，发送 select ... from CUSTOMER3 where CUSTOMER_ID=?
            // 设置 batch-size="5" 时，发送 select ... from CUSTOMER3 where CUSTOMER_ID in (?,?...)
            System.out.println(order.getCustomer().getCustomerName());
        }
    }
}
