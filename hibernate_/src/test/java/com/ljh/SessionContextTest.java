package com.ljh;

import com.ljh.dao.Customer3Dao;
import com.ljh.entity.query.strategy.Customer3;
import com.ljh.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * Current Session Context
 *
 * @author ljh
 * created on 2022/9/8 22:24
 */
public class SessionContextTest {

    @Test
    public void testThread() {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();
        Customer3Dao customerDao = new Customer3Dao();
        customerDao.save(new Customer3().setCustomerName("EE"));
        customerDao.save(new Customer3().setCustomerName("FF"));
        // customerDao.save() 里打印的 session.hashCode() 是相同的，证明是同一个 Session
        transaction.commit();
        // 若 Current Session Context 是 thread，则事务提交或回滚时，关闭 Session
        System.out.println(session.isOpen());
    }
}
