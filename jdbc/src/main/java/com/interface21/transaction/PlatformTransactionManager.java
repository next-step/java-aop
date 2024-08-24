package com.interface21.transaction;

import javax.sql.DataSource;

public interface PlatformTransactionManager {

    void begin();

    void commit();

    void rollback();

    DataSource getDataSource();
}
