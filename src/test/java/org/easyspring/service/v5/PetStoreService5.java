package org.easyspring.service.v5;

import org.easyspring.beans.factory.annotation.Autowired;
import org.easyspring.dao.v4.AccountDao;
import org.easyspring.dao.v4.ItemDao;
import org.easyspring.stereotype.Component;
import org.easyspring.util.MessageTracker;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:14
 */
@Component(value = "petStore")
public class PetStoreService5 {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ItemDao itemDao;

    public PetStoreService5() {
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void placeOrder() {
        System.out.println("place order");
        MessageTracker.addMsg("place order");
    }

    public void placeOrderException(){
        throw new IllegalArgumentException();
    }
}
