package com.ljh;

import com.ljh.entity.News;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
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
        // 1 创建 Configuration，默认读取 hibernate.cfg.xml
        Configuration configuration = new Configuration().configure();

        // 2. 创建 SessionFactory
        //  针对单个数据库映射关系经过编译后的内存镜像，是线程安全的
        //  一旦构建完毕，即被赋予特定的配置信息
        //  非常消耗资源，一般情况下一个应用中只初始化一个
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // 3. 创建 Session
        Session session = sessionFactory.openSession();

        // 4. 开启事务
        Transaction transaction = session.beginTransaction();

        // 5. 执行操作
//        News news = new News("Java", "LJH", new Date());
//        session.saveOrUpdate(news);
        News news = session.get(News.class, 1);
        System.out.println("news = " + news);

        // 6. 提交事务
        transaction.commit();

        // 7. 关闭 Session
        session.close();

        // 8. 关闭 SessionFactory
        sessionFactory.close();
    }
}
