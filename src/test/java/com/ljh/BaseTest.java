package com.ljh;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;

/**
 * BaseTest
 *
 * @author ljh
 * created on 2020/1/13 17:34
 */
public class BaseTest {

    protected SessionFactory sessionFactory;
    // 生产环境中 session 和 transaction 不能作为成员变量，会有并发的问题
    protected Session session;
    protected Transaction transaction;

    @Before
    public void init() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void destroy() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }
}
