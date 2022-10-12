package com.ljh.dao;

/**
 * BookShopDao
 *
 * @author ljh
 * created on 2022/10/11 15:25
 */
public interface BookShopDao {

    /**
     * 根据 isbn 获取单价
     *
     * @param isbn isbn
     * @return price
     */
    int findBookPriceByIsbn(String isbn);

    /**
     * 根据 isbn 更新库存：stock = stock - 1
     *
     * @param isbn isbn
     */
    void updateBookStockByIsbn(String isbn);

    /**
     * 根据用户名更余额：balance = balance - price
     *
     * @param username username
     * @param price    price
     */
    void updateAccountBalanceByUsername(String username, int price);
}
