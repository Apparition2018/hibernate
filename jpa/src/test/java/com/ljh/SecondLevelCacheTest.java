package com.ljh;

import com.ljh.entity.many2one.ba.Customer3;
import org.junit.Test;

/**
 * 二级缓存
 *
 * @author ljh
 */
public class SecondLevelCacheTest extends BaseTest {

    @Test
    public void test() {
        entityManager.find(Customer3.class, 1);
        this.closeThenOpenNewEntityManager();
        entityManager.find(Customer3.class, 1);
    }
}
