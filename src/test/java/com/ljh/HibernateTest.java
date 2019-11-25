package com.ljh;

import com.ljh.entity.News;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * HibernateTest
 *
 * @author ljh
 * created on 2019/11/21 16:19
 */
public class HibernateTest {

    /**
     * Hibernate 操作基本步骤
     */
    @Test
    public void test() {
        // 1. 创建一个 SessionFactory
        SessionFactory sessionFactory;
        // 1.1 创建 Configuration 对象：对应 hibernate 的基本配置信息和对象关系映射信息
        Configuration configuration = new Configuration().configure();
        // 1.2
        sessionFactory = configuration.buildSessionFactory();

        // 2. 创建一个 Session
        Session session = sessionFactory.openSession();

        // 3. 开启事务
        Transaction transaction = session.beginTransaction();

        // 4. 执行操作
//        News news = new News("Java", "ljh", new Date());
//        session.save(news);

        News news = session.get(News.class, 1);
        System.out.println("news = " + news);

        // 5. 提交事务
        transaction.commit();

        // 6. 关闭 Session
        session.close();

        // 7. 关闭 SessionFactory
        sessionFactory.close();
    }
}
