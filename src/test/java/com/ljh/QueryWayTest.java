package com.ljh;

import com.ljh.entity.query.strategy.Customer3;
import com.ljh.entity.query.way.Department3;
import com.ljh.entity.query.way.Employee3;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.hibernate.query.Query;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
     * HQL
     */
    @Test
    public void testHql() {
        // 1. 创建 Query 对象
        String hql = "FROM Employee3 e WHERE e.salary > :sal AND e.email LIKE ?2 AND e.dept = ?3 ORDER BY e.salary";
        Query<Employee3> query = session.createQuery(hql);

        // 2. 绑定参数
        query
                // 按照参数名字绑定
                .setParameter("sal", 6000F)
                // 按照参数位置绑定
                .setParameter(2, "%A%")
                .setParameter(3, new Department3().setId(80));

        // 3. 执行查询
        System.out.println(query.list().size());
    }

    /**
     * HQL 分页查询
     */
    @Test
    public void testHqlPage() {
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
    public void testHqlProjection() {
        String hql = "SELECT e.salary, e.email, e.dept FROM Employee3 e WHERE e.dept = :dept";
        Query<Object[]> query = session.createQuery(hql);
        List<Object[]> emps = query.setParameter("dept", new Department3().setId(80)).list();
        emps.forEach(emp -> System.out.println(Arrays.asList(emp)));
    }

    /**
     * HQL 投影查询返回 List<Entity>
     */
    @Test
    public void testHqlProjection2() {
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
        Query<Object[]> query = session.createQuery(hql).setParameter("minSal", 8000F);
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
     * HQL 普通连接：根据 hbm.xml 决定检索策略
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

    /**
     * HQL executeUpdate
     */
    @Test
    public void testHqlExecuteUpdate() {
        String hql = "DELETE From Department3 d WHERE d.id = :id";
        Query<Department3> query = session.createQuery(hql);
        query.setParameter("id", 100).executeUpdate();
    }

    /**
     * Criteria
     */
    @Test
    public void testCriteria() {
        /* 旧版 Criteria */
        // 1. 创建 Criteria 对象
        Criteria criteria = session.createCriteria(Employee3.class);
        // 2. 添加查询条件
        criteria.add(Restrictions.eq("email", "SKUMAR"))
                .add(Restrictions.gt("salary", 5000F));
        // 3. 执行查询
        Employee3 employee = (Employee3) criteria.uniqueResult();
        System.out.println(employee);


        /* 新版 Criteria */
        // 1. 创建 CriteriaBuilder 对象
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        // 2. 创建 CriteriaQuery 对象
        CriteriaQuery<Employee3> criteriaQuery = criteriaBuilder.createQuery(Employee3.class);
        // 3. 创建 Root 对象
        Root<Employee3> root = criteriaQuery.from(Employee3.class);
        // 4. 添加查询条件
        Predicate emailEqPred = criteriaBuilder.equal(root.get("email"), "SKUMAR");
        Predicate salaryGtPred = criteriaBuilder.greaterThan(root.get("salary"), 5000F);
        criteriaQuery.where(criteriaBuilder.and(emailEqPred, salaryGtPred));
        // 5. 创建 Query 对象
        Query<Employee3> query = session.createQuery(criteriaQuery);
        // 6. 执行查询
        employee = query.uniqueResult();
        System.out.println(employee);
    }

    /**
     * 旧 Criteria
     * AND  Conjunction
     * OR   Disjunction
     */
    @Test
    public void testCriteriaJunction() {
        /* 旧版 Criteria */
        Criteria criteria = session.createCriteria(Employee3.class);

        // 1. AND
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("name", "a", MatchMode.ANYWHERE));
        conjunction.add(Restrictions.eq("dept", new Department3().setId(80)));
        System.out.println(conjunction); // (name like %a% and dept=Department3{id=80, name='null'})
        // 2. OR
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.ge("salary", 6000F));
        disjunction.add(Restrictions.isNull("email"));
        System.out.println(disjunction); // (salary>=6000.0 or email is null)

        criteria.add(disjunction).add(conjunction);
        System.out.println(criteria.list());
        // select ... from EMPLOYEE3 e where (e.SALARY>=? or e.EMAIL is null) and (e.NAME like ? and e.DEPT_ID=?)


        /* 新版 Criteria */
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Employee3> criteriaQuery = criteriaBuilder.createQuery(Employee3.class);
        Root<Employee3> root = criteriaQuery.from(Employee3.class);

        // 1. AND
        Predicate nameLikePred = criteriaBuilder.like(root.get("name"), "%a%");
        Predicate deptIdEqPred = criteriaBuilder.equal(root.get("dept"), new Department3().setId(80));
        Predicate pred1 = criteriaBuilder.and(nameLikePred, deptIdEqPred);
        // 2. OR
        Predicate salaryGePred = criteriaBuilder.ge(root.get("salary"), 6000F);
        Predicate emailIsNullPred = criteriaBuilder.isNull(root.get("email"));
        Predicate pred2 = criteriaBuilder.or(salaryGePred, emailIsNullPred);

        criteriaQuery.where(pred1, pred2);
        System.out.println(session.createQuery(criteriaQuery).list());
    }

    /**
     * Criteria Projections
     */
    @Test
    public void testCriteriaProjections() {
        /* 旧版 Criteria */
        Criteria criteria = session.createCriteria(Employee3.class);
        criteria.setProjection(Projections.max("salary"));
        System.out.println(criteria.uniqueResult());
        // select max(SALARY) from EMPLOYEE3

        /* 新版 Criteria */
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Float> criteriaQuery = criteriaBuilder.createQuery(Float.class);
        Root<Employee3> root = criteriaQuery.from(Employee3.class);
        criteriaQuery.select(criteriaBuilder.max(root.get("salary")));
        System.out.println(session.createQuery(criteriaQuery).uniqueResult());
    }

    /**
     * Criteria 排序、分页
     */
    @Test
    public void testCriteriaOrderAndPage() {
        int pageNo = 3;
        int pageSize = 5;

        /* 旧版 Criteria */
        Criteria criteria = session.createCriteria(Employee3.class);
        // 添加排序
        criteria.addOrder(Order.asc("salary"));
        criteria.addOrder(Order.asc("email"));
        // 分页
        criteria.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
        // select ... from EMPLOYEE3 order by SALARY asc, EMAIL asc limit ?, ?

        /* 新版 Criteria */
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Employee3> criteriaQuery = criteriaBuilder.createQuery(Employee3.class);
        Root<Employee3> root = criteriaQuery.from(Employee3.class);
        // 添加排序
        javax.persistence.criteria.Order salaryOrder = criteriaBuilder.asc(root.get("salary"));
        javax.persistence.criteria.Order emailOrder = criteriaBuilder.asc(root.get("email"));
        criteriaQuery.orderBy(salaryOrder, emailOrder);
        // 分页
        session.createQuery(criteriaQuery).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
    }

    /**
     * Native SQL
     */
    @Test
    public void testNativeSQL() {
        String sql = "select * from EMPLOYEE3 order by SALARY, EMAIL limit ?1, ?2";
        session.createNativeQuery(sql)
                .setParameter(1, 0)
                .setParameter(2, 10)
                .list();
    }

    /**
     * Named SQL
     */
    @Test
    public void testNamedQuery() {
        /* 旧版 Named SQL */
        // queryName 对应 hbm.xml 文件中 <query/>|<sql-query/> 的 name 属性值
        // <query>      →   HQL
        // <sql-query>  →   SQL
        Query<Employee3> query = session.getNamedQuery("rangeSalEmps");
        List<Employee3> emps = query.setParameter("sal1", 5000F).setParameter("sal2", 10000F).list();
        System.out.println(emps.size());
    }
}
