package com.ljh;

import com.ljh.entity.News;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Session 缓存操作
 *
 * @author ljh
 * created on 2019/11/22 17:18
 */
public class SessionCacheTest extends BaseTest {

    @Test
    public void testCache() {
        // 访问数据库获取 news，并缓存到 Session 缓存中
        News news = session.get(News.class, 1);
        System.out.println("news = " + news);
        // 从 Session 缓存获取 news2，并没有访问数据库
        News news2 = session.get(News.class, 1);
        System.out.println("news2 = " + news2);
    }

    /**
     * flush()：按照 Session 缓存对象来同步更新数据库
     * 1. 调用 Transaction 的 commit() 时，会先隐式调用 flush()
     * 2. 查询时 (HQL 或 Query by Criteria)，如果缓存中的持久化对象发生改变，会先隐式调用 flush()，以保证查询结果为持久化对象的最新状态
     */
    @Test
    public void testFlush() {
        News news = session.get(News.class, 1);
        news.setAuthor("LJH2");

//        session.flush();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
        criteriaQuery.from(News.class);
        // 由于 news.setAuthor("LJH2") 改变了 news，所以执行 Criteria 查询前会隐式调用 flush()，以保证查询结果为持久化对象的最新状态
        List<News> newsList = session.createQuery(criteriaQuery).getResultList();
        System.out.println("news = " + newsList.get(0));
    }

    /**
     * refresh()：会发送 SELECT 语句，更新 Session 缓存
     * 但是否能够查询到对象最新的状态，还要看当前数据库的隔离级别
     */
    @Test
    public void testRefresh() {
        News news = session.get(News.class, 1);
        System.out.println("news = " + news);
        // 断点，修改数据库字段值 Author=LJH
        session.refresh(news);
        System.out.println("news = " + news);
    }

    /**
     * evict()：移除 Session 缓存中指定对象
     * clear()：清除 Session 所有缓存
     */
    @Test
    public void testEvictAndClear() {
        News news = session.get(News.class, 1);
        System.out.println("news = " + news);
        session.evict(news);
        // 因为上面语句移除了缓存，所以会重新发送 SELECT 语句
        News news2 = session.get(News.class, 1);
        System.out.println("news2 = " + news2);
        session.clear();
        // 因为上面语句清除了所有缓存，所以会重新发送 SELECT 语句
        News news3 = session.get(News.class, 1);
        System.out.println("news3 = " + news3);
    }
}
