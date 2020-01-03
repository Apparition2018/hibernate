package com.ljh;

import com.ljh.entity.News;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * 对象状态转换
 *
 * @author ljh
 * created on 2019/11/25 15:38
 */
public class SessionChangObjectStateTest {

    private SessionFactory sessionFactory;
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
     * save()
     * 1. 使一个临时对象变为持久化对象
     * 2. 为对象分配ID.
     * 3. 在 flush() 时会发送一条 INSERT 语句
     * 4. 在 save() 之前设置的 ID 是无效的
     * 5. 持久化对象的 ID 是不能被修改的
     */
    @Test
    public void testSave() {
        News news = new News();
//        news.setTitle("AA");
//        news.setTitle("BB");
        news.setTitle("CC");
//        news.setAuthor("aa");
//        news.setAuthor("bb");
        news.setAuthor("cc");
        news.setDate(new Date());
        news.setId(100);

        System.out.println("news = " + news);

        session.save(news);

        System.out.println("news = " + news);
        news.setId(101);
    }

    /**
     * persist() 和 save() 的区别：在调用 persist() 之前，对象已设置 ID，则不会执行 INSERT 语句且抛出异常
     */
    @Test
    public void testPersist() {
        News news = new News();
//        news.setTitle("DD");
        news.setTitle("EE");
//        news.setAuthor("dd");
        news.setAuthor("ee");
        news.setDate(new Date());
        news.setId(200);

        session.persist(news);

        System.out.println("news = " + news);
    }
    
    @Test
    public void testGet() {
        News news = session.get(News.class, 1);
        System.out.println("news = " + news);
    }

    /**
     * get() vs load():
     * 
     * 1. 执行 get() 会立即加载对象；而执行 load()，若不使用该对象，则不会立即执行查询操作，而返回一个代理对象
     *    get() 是立即检索，load() 是延迟检索
     * 2. load() 在需要初始化代理对象之前已经关闭了 Session 会抛出  LazyInitializationException
     * 3. 若数据表中没有对应的记录
     *    get() 返回 null，load() 初始化抛出异常
     */
    @Test
    public void testLoad() {
        News news = session.load(News.class, 1);
        System.out.println(news.getClass().getName()); // com.ljh.entity.News$HibernateProxy$3i8iYsPl
        System.out.println("news = " + news);
    }

    /**
     * update()
     * 1. 若更新一个持久化对象，不需要显式调用 update()，因为在调用 Transaction 的 commit() 时，会先执行 session 的 flush() 
     * 2. 更新一个游离对象，需要显式调用 update()
     */
    @Test
    public void testUpdate() {
        News news = session.get(News.class, 1);
        
        transaction.commit();
        session.close();
        
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
                
        news.setAuthor("Oracle");
        
        session.update(news);
    }
}
