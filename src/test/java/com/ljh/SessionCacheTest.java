package com.ljh;

import com.ljh.entity.News;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Date;
import java.util.List;

/**
 * Session 缓存操作
 *
 * @author ljh
 * created on 2019/11/22 17:18
 */
public class SessionCacheTest {

    private SessionFactory sessionFactory;
    // 生产环境中 session 和 transaction 不能作为成员变量，会有并发的问题
    private Session session;
    private Transaction transaction;

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

    /**
     * 测试 session 缓存 (一级缓存)
     *
     * 由于第一次查询通过访问数据库获取的数据已缓存在缓存中，所以第二次查询直接获取缓存中的数据，并没有访问数据库
     */
    @Test
    public void testSessionCache() {
        News news = session.get(News.class, 1);
        System.out.println("news = " + news);

        News news2 = session.get(News.class, 1);
        System.out.println("news2 = " + news2);
    }

    /**
     * flush() 使数据表中的记录和 Session 缓存中的对象的状态保存一致，为了保存一致，则可能会发送对应的 SQL 语句进行修改
     * 1. 调用 Transaction 中的 commit()，先调用 session 的 flush()，再提交事务
     * 2. flush() 可能会发送 SQL 语句，但不会提交事务
     * 3. 注意：在未提交事务或显式的调用 session.flush() 之前，也有可能会进行 flush() 操作
     * 1) 执行 HQL 或 QBC 查询，会先进行 flush() 操作，以得到数据表的最新的记录
     */
    @Test
    public void testSessionFlush() {
        News news = session.get(News.class, 1);
        news.setAuthor("SUN");

//        session.flush();
//        System.out.println("flush");

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
        criteriaQuery.from(News.class);
        List<News> newsList = session.createQuery(criteriaQuery).getResultList();
        System.out.println("news = " + newsList.get(0));

    }

    /**
     * 接上面注释...
     * 2) 若记录的 ID 是由底层数据库使用自增的方式生成的，则在调用 save() 后，就会立即发送 INSERT 语句，因为 save() 后，必须保证对象的 ID 是存在的
     */
    @Test
    public void testSessionFlush2() {
        News news = new News("Java", "SUN", new Date());
        session.save(news);
    }

    /**
     * refresh() 会强制发送 SELECT 语句，使 Session 缓存中对象的状态和数据表中的对应的记录保持一致！
     */
    @Test
    public void testSessionRefresh() {
        News news = session.get(News.class, 1);
        System.out.println("news = " + news); // 断点，控制台打印结果后，修改数据库字段值

        session.refresh(news);
        System.out.println("news = " + news);
    }

    /**
     * clear() 清理缓存
     */
    @Test
    public void testClear() {
        News news1 = session.get(News.class, 1);
        System.out.println("news1 = " + news1);
        session.clear();
        News news2 = session.get(News.class, 1);
        System.out.println("news2 = " + news2);
    }
}
