package com.ljh;

import com.ljh.entity.News;
import org.hibernate.Hibernate;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.Date;

/**
 * hbm.xml 文件配置
 *
 * @author ljh
 * created on 2020/1/13 17:33
 */
public class HbmTest extends BaseTest {

    /**
     * <class dynamic-update="true"/>
     */
    @Test
    public void testClassDynamicUpdate() {
        News news = session.get(News.class, 1);
        news.setAuthor("LJH");
        // dynamic-update="false", update NEWS set TITLE=?, AUTHOR=?, Date=? where ID=?
        // dynamic-update="true", update NEWS set AUTHOR=? where ID=?
    }

    /**
     * <property update="false/>
     */
    @Test
    public void testPropertyUpdate() {
        News news = session.get(News.class, 1);
        // 没有发送 UPDATE 语句
        news.setTitle("JAVA");
    }

    /**
     * <property formula="..."/>
     */
    @Test
    public void testPropertyFormula() {
        News news = session.get(News.class, 1);
        System.out.println(news.getDesc());
        // Hibernate:
        //     select
        //         news0_.ID as id1_0_0_,
        //         news0_.TITLE as title2_0_0_,
        //         news0_.AUTHOR as author3_0_0_,
        //         news0_.DATE as date4_0_0_,
        //         (SELECT
        //             concat(news0_.author,
        //             ': ',
        //             news0_.title)
        //         FROM
        //             NEWS n
        //         WHERE
        //             n.id = news0_.id) as formula1_0_
        //     from
        //         NEWS news0_
        //     where
        //         news0_.ID=?
        // LJH2: Java
    }

    /**
     * <column sql="mediumblob"/>
     */
    @Test
    public void testColumnBlob() throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get("hibernate-logo.svg"));
        System.out.println(inputStream.available());
        Blob image = Hibernate.getLobCreator(session).createBlob(inputStream, inputStream.available());

        News news = new News();
        news.setTitle("Go");
        news.setAuthor("Rob Pike");
        news.setDate(new Date());
        news.setContent("Go Go Go");
        news.setImage(image);
        session.save(news);
    }
}
