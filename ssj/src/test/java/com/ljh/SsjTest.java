package com.ljh;

import com.ljh.entity.Department;
import com.ljh.entity.Employee;
import com.ljh.repository.DepartmentRepository;
import com.ljh.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * SsjTest
 *
 * @author ljh
 * created on 2021/10/26 11:39
 */
public class SsjTest {

    private ApplicationContext ctx;
    private DepartmentRepository departmentRepository;
    private EmployeeRepository employeeRepository;

    @Before
    public void init() {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        departmentRepository = ctx.getBean(DepartmentRepository.class);
        employeeRepository = ctx.getBean(EmployeeRepository.class);
    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void initData() {
        Department dept1 = new Department().setDeptName("总裁办");
        Department dept2 = new Department().setDeptName("开发部");
        Department dept3 = new Department().setDeptName("后勤部");
        Department dept4 = new Department().setDeptName("公关部");
        departmentRepository.save(dept1);
        departmentRepository.save(dept2);
        departmentRepository.save(dept3);
        departmentRepository.save(dept4);

        Employee emp1 = new Employee().setLastName("AA").setEmail("aa@163.com").setBirth(Date.valueOf("2021-08-30"))
                .setCreateTime(Timestamp.valueOf("2021-08-30 15:00:00")).setDept(new Department().setId(1));
        Employee emp2 = new Employee().setLastName("BB").setEmail("bb@163.com").setBirth(Date.valueOf("2021-09-13"))
                .setCreateTime(Timestamp.valueOf("2021-08-31 15:00:00")).setDept(new Department().setId(2));
        Employee emp3 = new Employee().setLastName("CC").setEmail("cc@163.com").setBirth(Date.valueOf("2021-09-27"))
                .setCreateTime(Timestamp.valueOf("2021-09-03 15:00:00")).setDept(new Department().setId(3));
        Employee emp4 = new Employee().setLastName("DD").setEmail("dd@163.com").setBirth(Date.valueOf("2021-09-15"))
                .setCreateTime(Timestamp.valueOf("2021-09-05 15:00:00")).setDept(new Department().setId(4));
        Employee emp5 = new Employee().setLastName("EE").setEmail("ee@163.com").setBirth(Date.valueOf("2021-09-10"))
                .setCreateTime(Timestamp.valueOf("2021-10-03 15:00:00")).setDept(new Department().setId(1));
        Employee emp6 = new Employee().setLastName("FF").setEmail("ff@163.com").setBirth(Date.valueOf("2021-09-04"))
                .setCreateTime(Timestamp.valueOf("2021-10-10 15:00:00")).setDept(new Department().setId(2));
        Employee emp7 = new Employee().setLastName("GG").setEmail("gg@163.com").setBirth(Date.valueOf("2021-10-10"))
                .setCreateTime(Timestamp.valueOf("2021-10-06 15:00:00")).setDept(new Department().setId(3));
        employeeRepository.save(emp1);
        employeeRepository.save(emp2);
        employeeRepository.save(emp3);
        employeeRepository.save(emp4);
        employeeRepository.save(emp5);
        employeeRepository.save(emp6);
        employeeRepository.save(emp7);
    }
}
