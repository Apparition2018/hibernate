package com.ljh;

import com.ljh.entity.News;
import org.junit.Test;

import java.util.Date;

/**
 * Session 核心方法
 *
 * @author ljh
 * created on 2019/11/25 15:38
 */
public class SessionTest extends BaseTest {

    /**
     * save()
     * 1. 使一个临时对象变为持久化对象
     * 2. 为对象分配ID
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
     * 3. 若数据表中没有对应的记录，get() 返回 null，load() 初始化抛出异常
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
     * 2. 更新一个游离对象，需要显式调用 update()，可以把一个游离对象变为持久化对象
     * 
     * 注意：
     * 1. 无论要更新的游离对象和数据表的记录是否一致，都会发送 UPDATE 语句。除非，在 .hbm.xml 文件的 class 节点设置 select-before-update=true，多用在跟触发器协同工作，除非项目中有 UPDATE 语句的触发器
     * 2. 若数据表中没有对应的记录，但还调用了 update()，会抛出异常
     * 3. 当 update() 方法关联一个游离对象时，如果在 session 的缓存中已经存在相同 OID 的持久化对象，会抛出异常，因为在 session 缓存中不能有两个 OID 相同的对象
     */
    @Test
    public void testUpdate() {
        News news = session.get(News.class, 1);
        
        transaction.commit();
        session.close();
        
//        news.setId(100);
        
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
                
//        news.setAuthor("Oracle");
        
        News news2 = session.get(News.class, 1);
        
        session.update(news);
    }

    /**
     * saveOrUpdate()
     * 1. 临时对象执行 save()
     * 2. 游离对象执行 update()
     * 
     * 注意：
     * 1. 若 OID 不为 null，但数据表中还没有与其对应的记录，会抛出异常
     * 2. OID 等于 id 的 unsaved-value 的值，也被认为时一个游离对象
     */
    @Test
    public void testSaveOrUpdate() {
        News news = new News("FFF", "fff", new Date());
        news.setId(11);
        session.saveOrUpdate(news);
    }

    /**
     * delete()
     * 1. 可以删除一个游离对象或持久化对象
     * 2. OID 和数据表种一条记录对应，就会准备执行 DELETE 操作
     *    若 OID 在数据表中没有对应记录，则抛出异常
     * 3. 可以通过设置 hibernate 配置文件 hibernate.use_identifier_rollback 为 true，
     *    使删除对象后，把其 OID 置为 null。（开发中使用不多）
     */
    @Test
    public void testDelete() {
//        News news = new News();
//        news.setId(11);
        
        News news = session.get(News.class, 5);
        
        session.delete(news);
        System.out.println("news = " + news);
    }

    /**
     * evict()
     * 从 session 缓存中把指定的持久化对象移除
     */
    @Test
    public void testEvict() {
        News news1 = session.get(News.class, 6);
        News news2 = session.get(News.class, 7);
        
        news1.setTitle("AA");
        news2.setTitle("BB");
        
        session.evict(news1);
    }

    /**
     * Hibernate 调用存储过程
     * 通过 doWork() 获取 Connection
     */
    @Test
    public void doWork() {
        session.doWork(connection -> {
            System.out.println("connection = " + connection);
            // 使用 jdbc 调用存储过程
        });
    }


}
