package com.ljh;

import com.ljh.entity.many2many.Category;
import com.ljh.entity.many2many.Item;
import com.ljh.entity.many2one.ba.Customer3;
import com.ljh.entity.many2one.ba.Order3;
import com.ljh.entity.many2one.ua.Customer;
import com.ljh.entity.many2one.ua.Order;
import com.ljh.entity.one2many.ua.Customer2;
import com.ljh.entity.one2many.ua.Order2;
import com.ljh.entity.one2one.Department;
import com.ljh.entity.one2one.Manager;
import org.junit.Test;

import java.util.Date;

/**
 * 关联关系
 *
 * @author ljh
 * created on 2022/10/14 11:12
 */
public class AssociationTest extends BaseTest {

    /**
     * 单向多对一
     * 【n】添加 @ManyToOne 和 @JoinColumn {@link Order}
     */
    @Test
    public void testManyToOnePersist() {
        Customer customer = new Customer();
        customer.setLastName("FF");
        customer.setEmail("ff@163.com");
        customer.setAge(18);
        customer.setGender(Customer.GenderEnum.MALE);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Order order = new Order().setOrderName("O-FF-1");
        Order order2 = new Order().setOrderName("O-FF-2");

        order.setCustomer(customer);
        order2.setCustomer(customer);

        entityManager.persist(customer);
        entityManager.persist(order);
        entityManager.persist(order2);
    }

    @Test
    public void testManyToOneFind() {
        Order order = entityManager.find(Order.class, 1);
        System.out.println(order.getOrderName());
        // @ManyToOne(fetch = FetchType.LAZY) 时，由于 Order 持有外键，通过外键可查询出关联对象，
        // 所以此处打印是 Customer 的代理类，并没有发出 SELECT 语句查询 Customer
        System.out.println(order.getCustomer().getClass());
        System.out.println(order.getCustomer().getLastName());
    }

    @Test
    public void testManyToOneUpdate() {
        Order order = entityManager.find(Order.class, 1);
        order.getCustomer().setLastName("FFF");
    }

    @Test
    public void testManyToOneRemove() {
        Customer customer = entityManager.find(Customer.class, 5);
        // 不能直接删除【1】，因为有外键关联
        // javax.persistence.PersistenceException: org.hibernate.exception.ConstraintViolationException
        entityManager.remove(customer);
    }

    /**
     * 单向一对多
     * 【1】添加 @OneToMany 和 @JoinColumn {@link Customer2}
     */
    @Test
    public void testOneToManyPersist() {
        Customer2 customer = new Customer2().setLastName("AA").setEmail("aa@163.com");

        Order2 order = new Order2().setOrderName("O-AA-1");
        Order2 order2 = new Order2().setOrderName("O-AA-2");

        customer.getOrders().add(order);
        customer.getOrders().add(order2);

        // 单向一对多必定会多 UPDATE 语句，因为【n】INSERT 时，不会同时插入外键列
        entityManager.persist(customer);
        entityManager.persist(order);
        entityManager.persist(order2);
    }

    @Test
    public void testOneToManyFind() {
        Customer2 customer = entityManager.find(Customer2.class, 1);
        System.out.println(customer.getLastName());
        System.out.println(customer.getOrders().size());
    }

    @Test
    public void testOneToManyUpdate() {
        Customer2 customer = entityManager.find(Customer2.class, 1);
        customer.getOrders().iterator().next().setOrderName("O-AAA-1");
    }

    @Test
    public void testOneToManyRemove() {
        Customer2 customer = entityManager.find(Customer2.class, 1);
        // 删除【1】时，会先把【n】的外键置为 null，再删除【1】
        // 可以通过 @OneToMany(cascade = CascadeType.REMOVE) 实现级联删除
        entityManager.remove(customer);
    }

    /**
     * 双向多对一
     * 【1】添加 @OneToMany(mappedBy = "customer") {@link Customer3}
     * 【n】添加 @ManyToOne {@link Order3}
     */
    @Test
    public void testManyToOnePersist2() {
        Customer3 customer = new Customer3().setLastName("AA").setEmail("aa@163.com");

        Order3 order = new Order3().setOrderName("O-AA-1");
        Order3 order2 = new Order3().setOrderName("O-AA-2");

        customer.getOrders().add(order);
        customer.getOrders().add(order2);
        order.setCustomer(customer);
        order2.setCustomer(customer);

        entityManager.persist(customer);
        entityManager.persist(order);
        entityManager.persist(order2);
    }

    @Test
    public void testManyToOneFind2() {
        Order3 order = entityManager.find(Order3.class, 1);
        System.out.println(order.getOrderName());
        System.out.println(order.getCustomer().getClass());
        System.out.println(order.getCustomer().getLastName());
    }

    @Test
    public void testManyToOneUpdate2() {
        Order order = entityManager.find(Order.class, 1);
        order.getCustomer().setLastName("FFF");
    }

    @Test
    public void testManyToOneRemove2() {
        Customer customer = entityManager.find(Customer.class, 5);
        // 不能直接删除【1】，因为有外键关联
        // javax.persistence.PersistenceException: org.hibernate.exception.ConstraintViolationException
        entityManager.remove(customer);
    }

    /**
     * 双向一对一
     * 持有外键的一方添加 @OneToOne 和 @JoinColumn(name = "mgr_id", unique = true)
     * 没有外键的一方添加 @OneToOne(mappedBy = "mgr")
     */
    @Test
    public void testOneToOnePersist() {
        Manager manager = new Manager().setMgrName("M-AA");
        Department department = new Department().setDeptName("D-AA");

        manager.setDept(department);
        department.setMgr(manager);

        entityManager.persist(manager);
        entityManager.persist(department);
    }

    @Test
    public void testOneToOneFind() {
        Department department = entityManager.find(Department.class, 1);
        System.out.println(department.getDeptName());
        // @OneToOne(fetch = FetchType.LAZY) 时，由于 Department 持有外键，通过外键可查询出关联对象，
        // 所以此处打印是 Manager 的代理类
        System.out.println(department.getMgr().getClass().getName());
    }

    @Test
    public void testOneToOneFind2() {
        Manager manager = entityManager.find(Manager.class, 1);
        System.out.println(manager.getMgrName());
        // @OneToOne(fetch = FetchType.LAZY) 时，由于 Manager 没有持有外键，
        // 所以此处发送了 SELECT 语句查询关联对象
        System.out.println(manager.getDept().getClass().getName());
    }

    /**
     * 双向多对多
     * 一方添加 @ManyToMany 和 @JoinTable(…)
     * 另一方添加 @ManyToMany(mappedBy = "(…")
     */
    @Test
    public void testManyToManyPersist() {
        Item i1 = new Item().setItemName("i-1");
        Item i2 = new Item().setItemName("i-2");

        Category c1 = new Category().setCategoryName("c-1");
        Category c2 = new Category().setCategoryName("c-2");

        i1.getCategories().add(c1);
        i1.getCategories().add(c2);
        i2.getCategories().add(c1);
        i2.getCategories().add(c2);

        c1.getItems().add(i1);
        c1.getItems().add(i2);
        c2.getItems().add(i1);
        c2.getItems().add(i2);

        entityManager.persist(i1);
        entityManager.persist(i2);
        entityManager.persist(c1);
        entityManager.persist(c2);
    }

    @Test
    public void testManyToManyFind() {
        Item item = entityManager.find(Item.class, 1);
        System.out.println(item.getItemName());
        System.out.println(item.getCategories().size());
    }

    @Test
    public void testManyToManyFind2() {
        Category category = entityManager.find(Category.class, 1);
        System.out.println(category.getCategoryName());
        System.out.println(category.getItems().size());
    }
}
