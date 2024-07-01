package com.interface21.transaction;

public interface PlatformTransactionManager {

    void begin();

    void commit();

    void rollback();
}
