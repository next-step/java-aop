package com.interface21.transaction.support;

import com.interface21.dao.DataAccessException;
import com.interface21.jdbc.datasource.DataSourceUtils;
import com.interface21.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;


public class DataSourceTransactionManager implements PlatformTransactionManager {

    private final DataSource dataSource;

    public DataSourceTransactionManager(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void begin() {
        final var connection = DataSourceUtils.getConnection(dataSource);
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void commit() {
        final var connection = DataSourceUtils.getConnection(dataSource);
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
            TransactionSynchronizationManager.unbindResource(dataSource);
        }
    }

    public void rollback() {
        final var connection = DataSourceUtils.getConnection(dataSource);
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
            TransactionSynchronizationManager.unbindResource(dataSource);
        }
    }
}
