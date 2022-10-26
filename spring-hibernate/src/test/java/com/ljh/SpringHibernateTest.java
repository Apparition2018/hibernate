package com.ljh;

import com.ljh.service.BookShopService;
import com.ljh.service.Cashier;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * SpringHibernateTest
 *
 * @author ljh
 * created on 2022/10/11 11:37
 */
public class SpringHibernateTest {

    private ApplicationContext ctx;
    private BookShopService bookShopService;
    private Cashier cashier;

    @Before
    public void init() {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        bookShopService = ctx.getBean(BookShopService.class);
        cashier = ctx.getBean(Cashier.class);
    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void testBookShopService() {
        // 先在数据库添加记录：
        // S_BOOK       1   java    1001    100 10
        //              2   oracle  1002    70  10
        // S_ACCOUNT    1   Mary    150
        bookShopService.purchase("Mary", "1001");
    }

    @Test
    public void testCashier() {
        // 先在数据库修改记录：
        // S_ACCOUNT    1   Mary    130
        // 由于 bookService.purchase() 的事务传播行为设置为 REQUIRES_NEW
        // 所以 Mary 买第二本书（1002）出现余额不足异常时，不会回滚之前买第一本书（1001）的代码
        cashier.checkout("Mary", Arrays.asList("1001", "1002"));
    }
}
