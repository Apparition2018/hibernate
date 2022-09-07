package com.ljh;

import com.ljh.entity.query.strategy.Customer3;
import com.ljh.entity.query.way.Department3;
import com.ljh.entity.query.way.Employee3;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 查询方式
 *
 * @author ljh
 * created on 2022/9/7 15:14
 */
public class QueryWayTest extends BaseTest {

    /**
     * HQL createQuery()
     */
    @Test
    public void testHqlCreateQuery() {
        // 1. 创建 Query 对象
        String hql = "FROM Employee3 e WHERE e.salary > :sal AND e.email LIKE ?2 AND e.dept = ?3 ORDER BY e.salary";
        Query<Employee3> query = session.createQuery(hql);

        // 2. 绑定参数
        query
                // 按照参数名字绑定
                .setParameter("sal", 6000.0F)
                // 按照参数位置绑定
                .setParameter(2, "%A%")
                .setParameter(3, new Department3().setId(80));

        // 3. 执行查询
        System.out.println(query.list().size());
    }

    /**
     * HQL getNamedQuery(queryName)
     * queryName 对应 .hbm.xml 文件中 <query/> 的 name 属性值
     */
    @Test
    public void testHqlGetNamedQuery() {
        Query<Employee3> query = session.getNamedQuery("midSalEmps");
        List<Employee3> emps = query.setParameter("minSal", 5000.0F).setParameter("maxSal", 10000.0F).list();
        System.out.println(emps.size());
    }

    /**
     * HQL 分页查询
     */
    @Test
    public void testHqlPageQuery() {
        Query<Employee3> query = session.createQuery("FROM Employee3");
        int pageNo = 3;
        int pageSize = 5;
        List<Employee3> emps = query
                .setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize).list();
        System.out.println(emps.size());
    }

    /**
     * HQL 投影查询返回 List<Object[]>
     */
    @Test
    public void testHqlProjectionQuery() {
        String hql = "SELECT e.salary, e.email, e.dept FROM Employee3 e WHERE e.dept = :dept";
        Query<Object[]> query = session.createQuery(hql);
        List<Object[]> emps = query.setParameter("dept", new Department3().setId(80)).list();
        emps.forEach(emp -> System.out.println(Arrays.asList(emp)));
    }

    /**
     * HQL 投影查询 List<Entity>
     */
    @Test
    public void testHqlProjectionQuery2() {
        String hql = "SELECT new Employee3(e.salary, e.email, e.dept) FROM Employee3 e WHERE e.dept = :dept";
        Query<Employee3> query = session.createQuery(hql);
        List<Employee3> emps = query.setParameter("dept", new Department3().setId(80)).list();
        for (Employee3 emp : emps) {
            System.out.printf("%s，%s，%s，%s%n", emp.getId(), emp.getEmail(), emp.getSalary(), emp.getDept());
        }
    }

    /**
     * HQL 聚集函数
     */
    @Test
    public void testHqlAggregateFunc() {
        String hql = "SELECT min(e.salary), max(e.salary) FROM Employee3 e GROUP BY e.dept HAVING min(e.salary) > :minSal";
        Query<Object[]> query = session.createQuery(hql).setParameter("minSal", 8000.0F);
        List<Object[]> emps = query.list();
        emps.forEach(emp -> System.out.println(Collections.singletonList(emp)));
    }

    /**
     * HQL 迫切连接：立即初始化关联对象
     * 迫切左外连接(LEFT JOIN FETCH)
     * 迫切内连接(INNER JOIN FETCH)
     */
    @Test
    public void testHqlJoinFetch() {
        String hql = "SELECT DISTINCT c FROM Customer3 c LEFT JOIN FETCH c.orders";
        Query<Customer3> query = session.createQuery(hql);
        // select distinct ... from CUSTOMER3 c left outer join ORDER3 o on c.CUSTOMER_ID=o.CUSTOMER_ID
        List<Customer3> customers = query.list();
        for (Customer3 customer : customers) {
            System.out.println(customer.getOrders().size());
        }
    }

    /**
     * HQL 普通连接：根据 .hbm.xml 决定检索策略
     * 左外连接(LEFT JOIN)
     * 内连接（INNER JOIN)
     */
    @Test
    public void testHqlJoin() {
        String hql = "SELECT DISTINCT c FROM Customer3 c LEFT JOIN c.orders";
        Query<Customer3> query = session.createQuery(hql);
        // select distinct c.* from CUSTOMER3 left outer join ORDER3 o on c.CUSTOMER_ID=o.CUSTOMER_ID
        List<Customer3> customers = query.list();
        // select * from ORDER where CUSTOMER_ID in (?,?...)
        customers.forEach(customer -> System.out.println(customer.getOrders().size()));
    }
}
