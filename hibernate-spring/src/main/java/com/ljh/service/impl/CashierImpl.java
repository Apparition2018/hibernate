package com.ljh.service.impl;

import com.ljh.service.BookShopService;
import com.ljh.service.Cashier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CashierImpl
 *
 * @author ljh
 * created on 2022/10/11 15:56
 */
@Service
public class CashierImpl implements Cashier {

    private final BookShopService bookShopService;

    public CashierImpl(BookShopService bookShopService) {
        this.bookShopService = bookShopService;
    }

    @Override
    public void checkout(String username, List<String> isbns) {
        for (String isbn : isbns) {
            bookShopService.purchase(username, isbn);
        }
    }
}
