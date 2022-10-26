package com.ljh;

import com.ljh.entity.Person;
import com.ljh.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * SpringJpaTest
 *
 * @author ljh
 * created on 2022/10/24 15:19
 */
public class SpringJpaTest {

    private ApplicationContext ctx;
    private PersonService personService;

    @Before
    public void init() {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        personService = ctx.getBean(PersonService.class);
    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void testPersonService() {
        Person p1 = new Person().setAge(11).setEmail("aa@163.com").setLastName("AA");
        Person p2 = new Person().setAge(12).setEmail("bb@163.com").setLastName("BB");

        System.out.println(personService.getClass().getName());
        personService.savePersons(p1, p2);
    }
}
