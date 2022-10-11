package com.ljh.dao.impl;

import com.ljh.dao.BookShopDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * BookShopDaoImpl
 * <p>
 * 不推荐使用 HibernateTemplate 和 HibernateDaoSupport
 * 会导致 Dao 和 Spring 耦合，移植性变差
 *
 * @author ljh
 * created on 2022/10/11 15:33
 */
@Repository
public class BookShopDaoImpl implements BookShopDao {

    private final SessionFactory sessionFactory;

    public BookShopDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public int findBookPriceByIsbn(String isbn) {
        String hql = "SELECT b.price FROM Book b WHERE b.isbn = ?1";
        Query<Integer> query = this.getSession().createQuery(hql).setParameter(1, isbn);
        return query.uniqueResult();
    }

    @Override
    public void updateBookStockByIsbn(String isbn) {
        String hql = "UPDATE Book b SET b.stock = b.stock - 1 WHERE b.stock > 0 AND b.isbn = ?1";
        int affectedRows = this.getSession().createQuery(hql).setParameter(1, isbn).executeUpdate();
        if (affectedRows == 0) {
            throw new RuntimeException("库存不足！");
        }
    }

    @Override
    public void updateAccountBalanceByUsername(String username, int price) {
        String hql = "UPDATE Account a SET a.balance = a.balance - ?1 WHERE a.balance >= ?2 AND a.username = ?3";
        int affectedRows = this.getSession().createQuery(hql).setParameter(1, price).setParameter(2, price).setParameter(3, username).executeUpdate();
        if (affectedRows == 0) {
            throw new RuntimeException("余额不足！");
        }
    }
}
