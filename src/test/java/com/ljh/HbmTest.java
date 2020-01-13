package com.ljh;

import com.ljh.entity.News;
import org.junit.Test;

/**
 * hbm 文件配置
 *
 * @author Arsenal
 * created on 2020/1/13 17:33
 */
public class HbmTest extends BaseTest {
    
    @Test
    public void testDynamicUpdate() {
        News news = session.get(News.class, 6);
        news.setAuthor("ABCD");     // dynamic-update="false", update NEWS set TITLE=?, AUTHOR=?, Date=? where ID=?
        news.setAuthor("AABCD");    // dynamic-update="true", update NEWS set AUTHOR=? where ID=?
    }
}
