package org.easyspring.service.v2;

import org.easyspring.dao.v2.AccountDao;
import org.easyspring.dao.v2.ItemDao;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:14
 */
public class PetStoreService {

    private AccountDao accountDao;
    private ItemDao itemDao;
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
}
