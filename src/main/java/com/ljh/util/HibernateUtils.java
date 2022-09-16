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
