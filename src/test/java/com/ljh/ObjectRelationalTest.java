package com.ljh;

import com.ljh.entity.many2many.ba.Category2;
import com.ljh.entity.many2many.ba.Item2;
import com.ljh.entity.many2many.ua.Category;
import com.ljh.entity.many2many.ua.Item;
import com.ljh.entity.one2many.ba.Customer2;
import com.ljh.entity.one2many.ba.Order2;
import com.ljh.entity.one2many.ua.Customer;
import com.ljh.entity.one2many.ua.Order;
import com.ljh.entity.one2one.foreign.Department;
import com.ljh.entity.one2one.foreign.Manager;
import com.ljh.entity.one2one.primary.Department2;
import com.ljh.entity.one2one.primary.Manager2;
import org.junit.Test;

import java.util.Set;

/**
 * 对象关联
 *
 * @author ljh
 * created on 2020/3/13 17:47
 */
public class ObjectRelationalTest extends BaseTest {

    /**
     * 测试 单向一对多
     * 只设置 <many-to-one/>
     */
    @Test
    public void testMany2OneSave() {
        Customer customer = new Customer();
        customer.setCustomerName("AA");

        Order order1 = new Order();
        order1.setOrderName("ORDER-1");

        Order order2 = new Order();
        order2.setOrderName("ORDER-2");

        // 设定关联关系
        order1.setCustomer(customer);
        order2.setCustomer(customer);

        // 执行 save 操作：先插入 Customer，再插入 Order。3 条 INSERT (推荐)
        // 先插入 1 的一端，再插入 n 的一端，只有 INSERT 语句
        session.save(customer);

        session.save(order1);
        session.save(order2);

        // 先插入 Order， 再插入 Customer。3 条 INSERT，2 条 UPDATE
        // 先插入 n 的一端，再插入 1 的一端，会多出 UPDATE 语句！
        // 因为在插入多的一端时，无法确定 1 的一端的外键值，所以只能等 1 的一端插入后，再额外发送 UPDATE 语句
//        session.save(order1);
//        session.save(order2);
//        
//        session.save(customer);
    }
    @Test
    public void testMany2OneGet() {
        // 1. 若查询 n 的一端的一个对象，则默认情况下，只查询了 n 的一端的对象，而没有查询关联 1 的那一端对象
        Order order = session.get(Order.class, 1);
        System.out.println(order.getOrderName());

        // 2. 在需要使用到关联的对象时，才发送对应的 SQL 语句 (懒加载)
        Customer customer = order.getCustomer();
        System.out.println(customer.getCustomerName());

        // 3. 在查询 Customer 对象时，由 n 的一段导航到 1 的一端时，若此时 session 已被关闭，则会发生 LazyInitializationException 异常

        // 4. 获取 Order 对象时，默认情况下，其关联的 Customer 对象是一个代理对象
        System.out.println(order.getCustomer().getClass().getName());
    }
    @Test
    public void testMany2OneDelete() {
        // 在不设定级联关系的情况下，且 1 这一端的对象有 n 的对象在引用，不能直接删除 1 这一端的对象
        Customer customer = session.get(Customer.class, 1);
        session.delete(customer);
    }

    /**
     * 测试 双向一对多
     * 既设置 <many-to-one/>
     * 又设置 <set><key/><one-to-many/></set>
     */
    @Test
    public void testOne2ManySave() {
        Customer2 customer = new Customer2();
        customer.setCustomerName("CC");

        Order2 order1 = new Order2();
        order1.setOrderName("ORDER-5");

        Order2 order2 = new Order2();
        order2.setOrderName("ORDER-6");

        // 设定关联关系
        order1.setCustomer(customer);
        order2.setCustomer(customer);

        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        // 执行 save 操作：先插入 Customer，再插入 Order。3 条 INSERT，2 条 UPDATE
        // 因为 1 的一端和 n 的一端都维护关联关系，所以会多出 UPDATE 语句
        // 可以在 1 的一端的 <set> 节点指定 inverse="true"，来使 1 的一端放弃关联关系，这样就不会多出 UPDATE 语句
        session.save(customer);

        session.save(order1);
        session.save(order2);

        // 执行 save 操作：先插入 Customer，再插入 Order。3 条 INSERT，4 条 UPDATE
//        session.save(order1);
//        session.save(order2);
//
//        session.save(customer);
    }
    @Test
    public void testOne2ManyGet() {
        // 1. 对 n 的一端的集合使用延迟加载
        Customer2 customer = session.get(Customer2.class, 2);
        System.out.println(customer.getCustomerName());

        // 2. 返回的 n 的一端的集合时 Hibernate 内置的集合类型 (or.hibernate.collection.internal.PersistentSet)
        // 该类型具有延迟加载和存放代理对象的功能
        System.out.println(customer.getOrders().getClass());

        // 3. 若 session 已被关闭，则会发生 LazyInitializationException 异常

        // 4. 在需要使用集合中元素的时候进行初始化
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
