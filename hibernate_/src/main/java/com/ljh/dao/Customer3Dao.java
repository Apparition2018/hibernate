package com.ljh.dao;

import com.ljh.entity.query.strategy.Customer3;
import com.ljh.util.HibernateUtils;
import org.hibernate.Session;

/**
 * Customer3Dao
 *
 * @author ljh
 * created on 2022/9/8 21:52
 */
public class Customer3Dao {

    /**
     * 传入一个 Session 对象，意味着上一层(Service)需要获取到 Session 对象。
     * 这说明上一层需要和 Hibernate 的 API 紧密耦合，不推荐此种方式。
     */
    public void save(Session session, Customer3 customer) {
        session.save(customer);
    }

    public void save(Customer3 customer) {
        Session session = HibernateUtils.getSession();
        System.out.println(session.hashCode());
        session.save(customer);
    }
}
