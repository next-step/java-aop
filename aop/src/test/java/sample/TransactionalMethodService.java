package sample;

import com.interface21.transaction.annotation.Transactional;


public class TransactionalMethodService {

    @Transactional
    public void transactionalMethod() {
    }

    public void nonTransactionalMethod() {
    }
}
