package com.ljh;

import com.ljh.entity.News;
import org.junit.Test;

import java.util.Date;

/**
 * hbm 文件配置
 *
 * @author Arsenal
 * created on 2020/1/13 17:33
 */
public class HbmTest extends BaseTest {

    /**
     * 测试 class dynamic-update="true"
     */
    @Test
    public void testDynamicUpdate() {
        News news = session.get(News.class, 6);
        news.setAuthor("ABCD");     // dynamic-update="false", update NEWS set TITLE=?, AUTHOR=?, Date=? where ID=?
        news.setAuthor("AABCD");    // dynamic-update="true", update NEWS set AUTHOR=? where ID=?
    }

    /**
     * 测试 generator
     */
    @Test
    public void testGenerator() throws InterruptedException {
        News news = new News("AA", "aa", new Date());
        session.save(news);

//        Thread.sleep(5000);
    }

    /**
     * 测试 property update="false
     */
    @Test
    public void testPropertyUpdate() {
        News news = session.get(News.class, 1);
        news.setTitle("aaaa");

        System.out.println(news.getDesc());
    }

    /**
     * 测试 property formula="..."
     */
    @Test
    public void testPropertyFormula() {
        News news = session.get(News.class, 1);
        System.out.println(news.getDesc());
        // Hibernate:
        //    select
        //        news0_.ID as ID1_0_0_,
        //        news0_.TITLE as TITLE2_0_0_,
        //        news0_.AUTHOR as AUTHOR3_0_0_,
        //        news0_.Date as Date4_0_0_,
        //        (SELECT
        //            concat(news0_.author,
        //            ': ',
        //            news0_.title) 
        //        FROM
        //            NEWS n 
        //        WHERE
        //            n.id = news0_.id) as formula1_0_ 
        //    from
        //        NEWS news0_ 
        //    where
        //        news0_.ID=?
        // aa: AA
    }
}
