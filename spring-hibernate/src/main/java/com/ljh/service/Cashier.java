package com.ljh.service;

import java.util.List;

/**
 * Cashier
 *
 * @author ljh
 * created on 2022/10/11 15:56
 */
public interface Cashier {

    void checkout(String username, List<String> isbns);
}
