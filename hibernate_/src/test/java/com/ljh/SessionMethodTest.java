package com.ljh;

import com.ljh.entity.News;
import org.junit.Test;

import javax.persistence.PersistenceException;
import java.util.Date;

/**
 * Session 核心方法
 *
 * @author ljh
 * created on 2019/11/25 15:38
 */
public class SessionMethodTest extends BaseTest {

    /**
     * save()
     * 1. 使一个临时对象变为持久化对象
     * 2. generator
     * -    assigned    需要在调用 save() 之前设置 OID 值，否则则抛出 IdentifierGenerationException；flush() 时会发动 INSERT 语句
     * -    identity    在调用 save() 之前设置 OID 值会被覆盖，save() 时会立即发送 INSERT 语句以获取 OID 值，不会等到 flush() 才发送
     * -    sequence    在调用 save() 之前设置 OID 值会被覆盖，save() 时会发送
     * -    increment   在调用 save() 之前设置 OID 值会被覆盖，save() 时会发送 SELECT max 语句以获取 OID 值；flush() 时会发动 INSERT 语句
     */
    @Test
    public void testSave() {
        News news = new News().setTitle("Python").setAuthor("Van Rossum").setDate(new Date());
        news.setId(100);
        System.out.println("news = " + news);
        // generator 为 native，数据库为 mysql，所以 generator 为 identity
        // 调用 save() 会立即发送 INSERT 语句以获取 OID 值，所以上面 news.setId(100) 设置的 OID 被覆盖了
        session.save(news);
        System.out.println("news = " + news);
    }

    /**
     * persist() 和 save() 的区别：
     * 当 generator 为 identity/sequence/increment/...（需要通过 SQL 语句以获取 OID 值）时，
     * 在调用 save() 之前设置 OID 值会被覆盖；而在调用 persist() 之前 OID 值必须为 null，否则抛出 PersistentObjectException
     */
    @Test
    public void testPersist() {
        News news = new News().setTitle("C#").setAuthor("BB").setDate(new Date());
        news.setId(200);
        session.persist(news);
        System.out.println("news = " + news);
    }

    /**
     * get()
     * 1. 立即发送 SELECT 语句
     * 2. 获取不到值返回 null
     * <p>
     * load()
     * 1. lazy="true"时，使用延迟加载，不立即发送 SELECT 语句，而是得到一个代理对象，该代理对象只保存了 OID 值，直到第一次访问非 OID 属性时，才会发送 SELECT 语句
     * 2. 获取不到值抛出异常
     * 3. 在第一次使用对象之前，如果 Session 已经关闭，则抛出 LazyInitializationException
     */
    @Test
    public void testGetAndLoad() {
        News news = session.get(News.class, 1);
        System.out.println("news = " + news);

        // 这里如果断点，会发现延迟加载失效，发送了 SELECT 语句，因为断点会被认为是第一次使用到对象
        news = session.load(News.class, 2);
        // 由于是懒加载，所以是 News 的代理对象 com.ljh.entity.News$HibernateProxy$3i8iYsPl
        System.out.println(news.getClass().getName());
        // 这里会被认为是第一次使用到对象，发送 SELECT 语句
        System.out.println("news = " + news);

        news = session.load(News.class, -1);
        // org.hibernate.ObjectNotFoundException: No row with the given identifier exists: [com.ljh.entity.News#-1]
        System.out.println(news);

        this.closeThenOpenNewSession();

        news = session.load(News.class, 1);
        session.close();
        // 第一次使用到懒加载对象，但 Session 已关闭，所以抛出 LazyInitializationException
        System.out.println("news = " + news);
    }

    /**
     * update()
     * 1. 更新一个持久化对象，不需要显式调用 update()，因为在调用 Transaction 的 commit() 时，会先执行 session 的 flush()
     * -    更新持久化对象会先发送 SELECT 语句来确定是否确实需要 UPDATE
     * -    持久化对象的 OID 是不能被修改，否则抛出 PersistenceException → HibernateException
     * 2. 更新一个游离对象，需要显式调用 update()
     * -    更新游离对象都会发送 UPDATE 语句，除非在 .hbm.xml 文件的 class 节点设置 select-before-update=true
     * -    更新游离对象时，如果 Session 缓存中已经存在相同 OID 的持久化对象，会抛出 NonUniqueObjectException
     * 3. 更新对象的 OID 在数据库中不存在，抛出 StaleStateException
     */
    @Test
    public void testUpdate() {
        /* 更新持久化对象 */
        News news = session.get(News.class, 1);
        news.setId(300);
        news.setAuthor("LJH2");
        try {
            // 持久化对象在 Session 缓存中，所以 session.commit() 隐式调用 flush() 时，会发送 UPDATE 语句
            // 上面 news.setId(300) 修改持久化对象 OID 值，UPDATE 时，会抛出 PersistenceException → HibernateException
            this.closeThenOpenNewSession();
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }

        /* 更新游离对象 */
        news.setAuthor("LJH3");
        // 对象不在 Session 缓存中，是游离对象，所以 flush() 不发送 UPDATE 语句
        transaction.commit();

        /* 更新游离对象 */
        session.beginTransaction();
        // 从数据库获取 OID 为 1 的对象
        News news2 = session.get(News.class, 1);
        // 更新 OID 为 1 的游离对象，因为 Session 中已存在相同 OID 的持久化对象，所以抛出 NonUniqueObjectException
        session.update(news);
    }

    /**
     * saveOrUpdate()
     * 1. 临时对象 → save()
     * 2. 游离对象 → update()
     * <p>
     * 如何判断是否为临时对象：
     * 1. OID 是否为 null
     * 2. 映射文件 <id> 设置了 unsaved-value 属性值，且 OID 值与 unsaved-value 属性值相等
     */
    @Test
    public void testSaveOrUpdate() {
        News news = session.get(News.class, 1);
        news.setAuthor("LJH2");
        session.saveOrUpdate(news);
    }

    /**
     * delete()
     * 1. 删除一个游离对象或持久化对象
     * 2. 删除对象的 OID 在数据库不存在，抛出 StaleStateException
     * 3. 可以通过设置 hibernate 配置文件 hibernate.use_identifier_rollback 为 true，使删除对象后，把其 OID 置为 null
     */
    @Test
    public void testDelete() {
        News news = new News().setId(-1);
        session.delete(news);
        // 到事务提交时，抛出 org.hibernate.StaleStateException: Batch update returned unexpected row count from update [0];
    }

    /**
     * doWork()：通过 JDBC 的 Connection 执行 JDBC 操作
     */
    @Test
    public void doWork() {
        session.doWork(connection -> System.out.println("connection = " + connection));
    }
}
