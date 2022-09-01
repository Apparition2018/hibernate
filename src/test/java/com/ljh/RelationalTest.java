package com.ljh;

import com.ljh.entity.component.Pay;
import com.ljh.entity.component.Worker;
import com.ljh.entity.many2many.ba.Category2;
import com.ljh.entity.many2many.ba.Item2;
import com.ljh.entity.many2many.ua.Category;
import com.ljh.entity.many2many.ua.Item;
import com.ljh.entity.many2one.ba.Customer2;
import com.ljh.entity.many2one.ba.Order2;
import com.ljh.entity.many2one.ua.Customer;
import com.ljh.entity.many2one.ua.Order;
import com.ljh.entity.one2one.foreign.Department;
import com.ljh.entity.one2one.foreign.Manager;
import com.ljh.entity.one2one.primary.Department2;
import com.ljh.entity.one2one.primary.Manager2;
import org.junit.Test;

import java.util.Set;

/**
 * 关系
 *
 * @author ljh
 * created on 2020/3/13 17:47
 */
public class RelationalTest extends BaseTest {

    /**
     * 组成关系：<component/>
     */
    @Test
    public void testComponent() {
        Pay pay = new Pay();
        pay.setMonthlyPay(1000);
        pay.setYearPay(80000);
        pay.setVocationWithPay(5);

        Worker worker = new Worker();
        worker.setName("LJH");
        worker.setPay(pay);
        session.save(worker);
    }

    /**
     * 单向多对一
     * 【多】设置 <many-to-one/>
     */
    @Test
    public void testMany2OneSave() {
        Customer customer = new Customer();
        customer.setCustomerName("LJH");

        Order order1 = new Order().setOrderName("ORDER-1");
        order1.setCustomer(customer);

        Order order2 = new Order().setOrderName("ORDER-2");
        order2.setCustomer(customer);

        // 先插入【一】，后插入【多】，只有 INSERT 语句
        session.save(customer);
        session.save(order1);
        session.save(order2);

        // 先插入【多】，后插入【一】，最后会多出 UPDATE【多】的语句
        // 因为先插入【多】时，【一】还没有插入，外键还是空的，需要在【一】插入后再 UPDATE【多】的外键
//        session.save(order1);
//        session.save(order2);
//        session.save(customer);
    }

    @Test
    public void testMany2OneGet() {
        Order order = session.get(Order.class, 1);
        System.out.println(order.getOrderName());
        // 第一次使用到 Customer，才发送 SELECT SQL（懒加载）
        // 如果在此之前 Session 已关闭，则会抛出 LazyInitializationException
        System.out.println(order.getCustomer().getCustomerName());
        System.out.println(order.getCustomer().getClass().getName());
        // 由于是懒加载，所以是 Customer 的代理对象 com.ljh.entity.many2one.ua.Customer$HibernateProxy$qAEn6V2V
        System.out.println(order.getCustomer().getClass().getName());
    }

    @Test
    public void testMany2OneUpdate() {
        Order order = session.get(Order.class, 1);
        order.getCustomer().setCustomerName("LJH2");
    }

    @Test
    public void testMany2OneDelete() {
        // 在不设定级联关系的情况下，且【一】有数据被【多】引用，不能直接删除【一】，会抛出 PersistenceException → ConstraintViolationException
        // 解决：先删除【多】 或 设置级联关系
        Customer customer = session.get(Customer.class, 1);
        session.delete(customer);
    }

    /**
     * 双向多对一
     * 【多】设置 <many-to-one/>
     * 【一】设置 <set inverse="true"><key/><one-to-many/></set>
     */
    @Test
    public void testMany2OneSave2() {
        Customer2 customer = new Customer2();
        customer.setCustomerName("LJH");

        Order2 order1 = new Order2().setOrderName("ORDER-1");
        order1.setCustomer(customer);

        Order2 order2 = new Order2().setOrderName("ORDER-2");
        order2.setCustomer(customer);

        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        session.save(customer);
        session.save(order1);
        session.save(order2);
    }

    @Test
    public void testMany2OneGet2() {
        Customer2 customer = session.get(Customer2.class, 1);
        System.out.println(customer.getCustomerName());
        // 第一次使用到 Orders，才发送 SELECT SQL（懒加载）
        // 如果在此之前 Session 已关闭，则会抛出 LazyInitializationException
        System.out.println(customer.getOrders().iterator().next().getOrderName());
        // Hibernate 内置集合类型：org.hibernate.collection.internal.PersistentSet
        // 该类型具有延迟加载和存放代理对象的功能
        System.out.println(customer.getOrders().getClass());
    }

    /**
     * 测试 级联操作
     * <xxx cascade="delete-orphan"/>
     */
    @Test
    public void testCascadeDeleteOrphan() {
        Customer2 customer = session.get(Customer2.class, 4);
        customer.getOrders().clear();
    }

    /**
     * 测试 级联操作
     * <xxx cascade="save-update"/>
     */
    @Test
    public void testCascadeSaveUpdate() {
        Customer2 customer = new Customer2();
        customer.setCustomerName("AA");

        Order2 order1 = new Order2();
        order1.setOrderName("ORDER-1");

        Order2 order2 = new Order2();
        order2.setOrderName("ORDER-2");

        order1.setCustomer(customer);
        order2.setCustomer(customer);

        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        session.save(customer);
    }

    /**
     * 测试 基于外键的一对一
     */
    @Test
    public void testOne2OneForeignSave() {
        Department department = new Department();
        department.setDeptName("DEPT-BB");

        Manager manager = new Manager();
        manager.setMgrName("MGR-BB");

        // 设定关联关系
        department.setMgr(manager);
        manager.setDept(department);

        // 保存操作
        // 建议先保存没有外键列的那个对象，这样会减少 UPDATE 语句
        session.save(manager);
        session.save(department);
    }

    @Test
    public void testOne2OneForeignGet() {
        // 1. 默认人情况下对关联属性使用懒加载
        Department dept = session.get(Department.class, 1);
        System.out.println(dept.getDeptName());

        // 2. 若 session 已被关闭，则会发生 LazyInitializationException 异常
//        session.close();
//        System.out.println(dept.getMgr().getMgrName());

        // 3. 查询 Manager 对象的连接条件应该是 dept.mgr_id = mgr.mgr_id，而不是 dept.dept_id = mgr.mgr_id
        Manager mgr = dept.getMgr();
        System.out.println(mgr.getMgrName());
    }

    @Test
    public void testOne2OneForeignGet2() {
        // 在查询没有外键的实体对象时，使用左外连接查询，一并查询出其关联的对象并已经进行初始化
        Manager mgr = session.get(Manager.class, 1);
        System.out.println(mgr.getMgrName());
        System.out.println(mgr.getDept().getDeptName());
    }

    /**
     * 测试 基于主键的一对一
     */
    @Test
    public void testOne2OnePrimarySave() {
        Department2 department = new Department2();
        department.setDeptName("DEPT-BB");

        Manager2 manager = new Manager2();
        manager.setMgrName("MGR-BB");

        department.setMgr(manager);
        manager.setDept(department);

        // 先插入哪一个都不会有多余的 UPDATE
        session.save(manager);
        session.save(department);
    }

    /**
     * 测试 单向单向多对多
     * 只在一端使用集合属性
     */
    @Test
    public void testMany2ManyBaSave() {
        Category category1 = new Category();
        category1.setName("C-AA");

        Category category2 = new Category();
        category2.setName("C-BB");

        Item item1 = new Item();
        item1.setName("I-AA");

        Item item2 = new Item();
        item2.setName("I-BB");

        // 设定关联关系
        category1.getItems().add(item1);
        category1.getItems().add(item2);

        category2.getItems().add(item1);
        category2.getItems().add(item2);

        // 指定保存操作
        session.save(category1);
        session.save(category2);

        session.save(item1);
        session.save(item2);
    }

    @Test
    public void testMany2ManyBaGet() {
        Category category = session.get(Category.class, 1);
        System.out.println(category.getName());

        // 需要连接中间表
        Set<Item> items = category.getItems();
        System.out.println(items.size());
    }

    /**
     * 测试 双向单向多对多
     * 在两端都使用集合属性
     */
    @Test
    public void testMany2ManyUaSave() {
        Category2 category1 = new Category2();
        category1.setName("C-AA");

        Category2 category2 = new Category2();
        category2.setName("C-BB");

        Item2 item1 = new Item2();
        item1.setName("I-AA");

        Item2 item2 = new Item2();
        item2.setName("I-BB");

        // 设定关联关系
        category1.getItems().add(item1);
        category1.getItems().add(item2);

        category2.getItems().add(item1);
        category2.getItems().add(item2);

        item1.getCategories().add(category1);
        item1.getCategories().add(category2);

        item2.getCategories().add(category1);
        item2.getCategories().add(category2);

        // 指定保存操作
        session.save(category1);
        session.save(category2);

        session.save(item1);
        session.save(item2);
    }
}
