package org.easyspring.service.v4;

import org.easyspring.beans.factory.annotation.Autowired;
import org.easyspring.dao.v2.AccountDao;
import org.easyspring.dao.v2.ItemDao;
import org.easyspring.stereotype.Component;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:14
 */
@Component(value = "petStore")
public class PetStoreService {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

}
