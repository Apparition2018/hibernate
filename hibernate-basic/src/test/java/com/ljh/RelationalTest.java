package com.ljh;

import com.ljh.entity.component.Pay;
import com.ljh.entity.component.Worker;
import com.ljh.entity.inheritance.joined.subclass.Person2;
import com.ljh.entity.inheritance.joined.subclass.Staff2;
import com.ljh.entity.inheritance.sublcass.Person;
import com.ljh.entity.inheritance.sublcass.Staff;
import com.ljh.entity.inheritance.union.subclass.Person3;
import com.ljh.entity.inheritance.union.subclass.Staff3;
import com.ljh.entity.many2many.ba.Student2;
import com.ljh.entity.many2many.ba.Teacher2;
import com.ljh.entity.many2many.ua.Student;
import com.ljh.entity.many2many.ua.Teacher;
import com.ljh.entity.many2one.ba.Customer2;
import com.ljh.entity.many2one.ba.Order2;
import com.ljh.entity.many2one.ua.Customer;
import com.ljh.entity.many2one.ua.Order;
import com.ljh.entity.one2one.foreign.Department;
import com.ljh.entity.one2one.foreign.Manager;
import com.ljh.entity.one2one.primary.Department2;
import com.ljh.entity.one2one.primary.Manager2;
import org.junit.Test;

import java.util.List;

/**
 * 关系
 * 1.组成关系
 * 2.关联关系
 * 3.继承关系
 *
 * @author ljh
 * created on 2020/3/13 17:47
 */
public class RelationalTest extends BaseTest {

    /**
     * 组成关系：<component/>
     */
    public static class ComponentTest extends BaseTest {
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
    }

    /**
     * 关联关系
     */
    public static class AssociationTest extends BaseTest {
        /**
         * 单向多对一
         * 【n】设置 <many-to-one/>
         */
        @Test
        public void testManyToOneSave() {
            Customer customer = new Customer();
            customer.setCustomerName("LJH");

            Order order1 = new Order().setOrderName("ORDER-1");
            order1.setCustomer(customer);

            Order order2 = new Order().setOrderName("ORDER-2");
            order2.setCustomer(customer);

            // 先插入【1】，后插入【n】，只有 INSERT 语句
            session.save(customer);
            session.save(order1);
            session.save(order2);

            // 先插入【n】，后插入【1】，最后会多出 UPDATE【n】的语句
            // 因为先插入【n】时，【1】还没有插入，外键还是空的，需要在【1】插入后再 UPDATE【n】的外键
//        session.save(order1);
//        session.save(order2);
//        session.save(customer);
        }

        @Test
        public void testManyToOneGet() {
            Order order = session.get(Order.class, 1);
            System.out.println(order.getOrderName());
            // 第一次使用到关联对象 customer，才发送 SELECT 语句（懒加载）
            // 如果在此之前 Session 已关闭，则会抛出 LazyInitializationException
            System.out.println(order.getCustomer().getCustomerName());
            System.out.println(order.getCustomer().getClass().getName());
            // 由于是懒加载，所以是 Customer 的代理对象 com.ljh.entity.many2one.ua.Customer$HibernateProxy$qAEn6V2V
            System.out.println(order.getCustomer().getClass().getName());
        }

        @Test
        public void testManyToOneUpdate() {
            Order order = session.get(Order.class, 1);
            order.getCustomer().setCustomerName("LJH2");
        }

        @Test
        public void testManyToOneDelete() {
            // 在不设定级联关系的情况下，且【1】有数据被【n】引用，不能直接删除【1】，会抛出 PersistenceException → ConstraintViolationException
            // 解决：先删除【n】 或 设置级联关系
            Customer customer = session.get(Customer.class, 1);
            session.delete(customer);
        }

        /**
         * 双向多对一
         * 【n】设置 <many-to-one/>
         * 【1】设置 <set inverse="true"><key/><one-to-many/></set>
         */
        @Test
        public void testManyToOneSave2() {
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
        public void testManyToOneGetAndOrderBy() {
            Customer2 customer = session.get(Customer2.class, 1);
            System.out.println(customer.getCustomerName());
            // 第一次使用到关联对象 orders，才发送 SELECT 语句（懒加载）
            // 如果在此之前 Session 已关闭，则会抛出 LazyInitializationException
            System.out.println(customer.getOrders().iterator().next().getOrderName());
            // Hibernate 内置集合类型：org.hibernate.collection.internal.PersistentSet
            // 该类型具有延迟加载和存放代理对象的功能
            System.out.println(customer.getOrders().getClass());
        }

        /**
         * 级联操作-孤立删除
         * cascade="delete-orphan"
         */
        @Test
        public void testCascadeDeleteOrphan() {
            Customer2 customer = session.get(Customer2.class, 1);
            customer.getOrders().clear();
            // clear() 清空了 orders 集合，保存 customer 时删除了关联的 Order
            session.saveOrUpdate(customer);
        }

        /**
         * 级联操作-级联保存/更新
         * cascade="save-update"
         */
        @Test
        public void testCascadeSaveUpdate() {
            Customer2 customer = new Customer2();
            customer.setCustomerName("Van Rossum");

            Order2 order1 = new Order2().setOrderName("ORDER-3");
            order1.setCustomer(customer);

            Order2 order2 = new Order2().setOrderName("ORDER-4");
            order2.setCustomer(customer);

            customer.getOrders().add(order1);
            customer.getOrders().add(order2);

            // 保存 customer 时，级联保存关联的 Order
            session.save(customer);
        }

        /**
         * 基于外键的一对一
         * 持有外键的一方 <many-to-one unique="true"/>
         * 没有外键的一方 <one-to-one property-ref="mgr"/>
         */
        @Test
        public void testOneToOneForeignSave() {
            Department department = new Department().setDeptName("DEPT-A");
            Manager manager = new Manager().setMgrName("MGR-A");
            department.setMgr(manager);
            manager.setDept(department);

            // 先保存没有外键列的对象，减少 UPDATE 语句
            session.save(manager);
            session.save(department);
        }

        @Test
        public void testOneToOneForeignGet() {
            Department dept = session.get(Department.class, 1);
            System.out.println(dept.getDeptName());
            // 第一次使用到关联对象 mgr，才发送 SELECT 语句（懒加载）
            // 如果在此之前 Session 已关闭，则会抛出 LazyInitializationException
            System.out.println(dept.getMgr().getMgrName());
        }

        @Test
        public void testOneToOneForeignGet2() {
            Manager mgr = session.get(Manager.class, 1);
            // mgr 没有持有外键，所以使用到关联对象的时候，无法使用懒加载
            // 而是在查询 mgr 时，就使用左关联一并查询出关联对象了
            System.out.println(mgr.getDept().getDeptName());
        }

        /**
         * 基于主键的一对一
         * 一端   <id><generator class="foreign"><param></param></generator></id> + <one-to-one/>
         * 另一端  <one-to-one/>
         */
        @Test
        public void testOneToOnePrimarySave() {
            Department2 department = new Department2().setDeptName("DEPT-B");
            Manager2 manager = new Manager2().setMgrName("MGR-B");
            department.setMgr(manager);
            manager.setDept(department);

            // 无论先保存哪个，Hibernate 总是后保存 generator 为 foreign 的一端
            session.save(manager);
            session.save(department);
        }

        /**
         * 单向多对多
         * 一端 <set><many-to-many></many-to-many></set>
         */
        @Test
        public void testManyToManyBaSave() {
            Teacher teacher1 = new Teacher().setName("Jack");
            Teacher teacher2 = new Teacher().setName("Rose");
            Student student1 = new Student().setName("Tom");
            Student student2 = new Student().setName("Mary");

            teacher1.getStudents().add(student1);
            teacher1.getStudents().add(student2);
            teacher2.getStudents().add(student1);
            teacher2.getStudents().add(student2);

            session.save(teacher1);
            session.save(teacher2);
            session.save(student1);
            session.save(student2);
        }

        @Test
        public void testManyToManyBaGet() {
            Teacher teacher = session.get(Teacher.class, 1);
            // 第一次使用到关联对象 students，才发送 SELECT 语句（懒加载），SELECT ... from TEACHER_STUDENT ts inner join STUDENT s
            System.out.println(teacher.getStudents().size());
        }

        /**
         * 双向多对多
         * 一端 <set><many-to-many></many-to-many></set>
         * 一端 <set><many-to-many></many-to-many inverse="true"></set>
         */
        @Test
        public void testManyToManyUaSave() {
            Teacher2 teacher1 = new Teacher2().setName("Jack");
            Teacher2 teacher2 = new Teacher2().setName("Rose");
            Student2 student1 = new Student2().setName("Tom");
            Student2 student2 = new Student2().setName("Mary");

            teacher1.getStudents().add(student1);
            teacher1.getStudents().add(student2);
            teacher2.getStudents().add(student1);
            teacher2.getStudents().add(student2);

            student1.getTeachers().add(teacher1);
            student1.getTeachers().add(teacher2);
            student2.getTeachers().add(teacher1);
            student2.getTeachers().add(teacher2);

            session.save(teacher1);
            session.save(teacher2);
            session.save(student1);
            session.save(student2);
        }
    }

    /**
     * 继承关系
     */
    public static class InheritanceTest extends BaseTest {
        /**
         * <subclass/> 继承
         * 缺点：
         * 1. 使用了辨别者列
         * 2. 子类独有的字段不能添加非空约束
         * 3. 若继承层次较深，则数据表的字段也会较多
         */
        @Test
        public void testSubClassSave() {
            Person person = new Person().setName("LJH").setAge(40);
            session.save(person);
            Person staff = new Staff().setCompany("Microsoft").setName("Van Rossum").setAge(66);
            session.save(staff);
        }

        @Test
        public void testSubClassGet() {
            // select ... from PERSON
            List<Person> persons = session.createQuery("FROM Person").list();
            System.out.println(persons.size());

            // select ... from PERSON where type='STAFF'
            List<Staff> staff = session.createQuery("FROM Staff").list();
            System.out.println(staff.size());
        }

        /**
         * <joined-subclass/> 继承
         * 优点：
         * 1. 无需使用辨别者列
         * 2. 子类独有的字段能添加非空约束
         * 3. 没有冗余的字段
         */
        @Test
        public void testJoinedSubClassSave() {
            Person2 person = new Person2().setName("LJH").setAge(40);
            session.save(person);
            Person2 student = new Staff2().setCompany("Microsoft").setName("Van Rossum").setAge(66);
            session.save(student);
        }

        @Test
        public void testJoinedSubClassGet() {
            // select ... from PERSON2 p eft outer join STAFF2 s on p.ID=s.STAFF_ID
            List<Person2> persons = session.createQuery("FROM Person2").list();
            System.out.println(persons.size());

            // select ... from STAFF2 s inner join PERSON2 p on s.STAFF_ID=p.ID
            List<Staff2> staffs = session.createQuery("FROM Staff2").list();
            System.out.println(staffs.size());
        }

        /**
         * 测试 <union-subclass/> 继承
         * 优点：
         * 1. 无需使用辨别者列
         * 2. 子类独有的字段能添加非空约束
         * 缺点：
         * 1. 存在冗余字段
         * 2. 若更新父表的字段，则更新的效率较低
         */
        @Test
        public void testUnionSubClassSave() {
            Person3 person = new Person3().setName("LJH").setAge(40);
            session.save(person);
            Person3 student = new Staff3().setCompany("Microsoft").setName("Van Rossum").setAge(66);
            session.save(student);
        }

        @Test
        public void testUnionSubClassGet() {
            // select ... from (select ... from PERSON3 union all select ... from STAFF3) t
            // 性能较差
            List<Person3> persons = session.createQuery("FROM Person3").list();
            System.out.println(persons.size());

            // select ... from STAFF3
            List<Staff3> students = session.createQuery("FROM Staff3").list();
            System.out.println(students.size());
        }

        @Test
        public void testUnionSubClassUpdate() {
            // 查看 SQL 日志，语句非常复制，效率较低
            String hql = "UPDATE Person3 p SET p.age = 67";
            session.createQuery(hql).executeUpdate();
        }
    }
}
