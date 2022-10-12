package com.ljh.service.impl;

import com.ljh.dao.BookShopDao;
import com.ljh.service.BookShopService;
import org.springframework.stereotype.Service;

/**
 * BookShopServiceImpl
 *
 * @author ljh
 * created on 2022/10/11 15:53
 */
@Service
public class BookShopServiceImpl implements BookShopService {

    private final BookShopDao bookShopDao;

    public BookShopServiceImpl(BookShopDao bookShopDao) {
        this.bookShopDao = bookShopDao;
    }

    @Override
    public void purchase(String username, String isbn) {
        int price = bookShopDao.findBookPriceByIsbn(isbn);
        bookShopDao.updateBookStockByIsbn(isbn);
        bookShopDao.updateAccountBalanceByUsername(username, price);
    }
}
