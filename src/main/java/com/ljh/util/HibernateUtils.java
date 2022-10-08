package com.ljh.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * HibernateUtils
 *
 * @author ljh
 * created on 2022/9/8 21:58
 */
public class HibernateUtils {

    private HibernateUtils() {
    }

    // 和 Spring 整合时，可以通过依赖注入来控制 SessionFactory 为单例：https://www.baeldung.com/persistence-layer-with-spring-and-hibernate
    private volatile static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory != null) return sessionFactory;
        synchronized ((HibernateUtils.class)) {
            if (sessionFactory == null) sessionFactory = new Configuration().configure().buildSessionFactory();
            return sessionFactory;
        }
    }

    public static Session getSession() {
        return getSessionFactory().getCurrentSession();
    }
}
