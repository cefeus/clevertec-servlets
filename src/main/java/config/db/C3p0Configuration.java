package config.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import util.ReadProperties;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import static util.constants.DatabaseConstants.*;

/**
 * Config class for connection pool
 */
public class C3p0Configuration {
    private final ComboPooledDataSource connPool;

    public C3p0Configuration() {
        connPool = new ComboPooledDataSource();
        loadProperties();
    }

    /**
     * Loads all needed for connection pool parameters
     */
    private void loadProperties() {
        connPool.setJdbcUrl(ReadProperties.getPropertyByKey(URL));
        connPool.setUser(ReadProperties.getPropertyByKey(USERNAME));
        connPool.setPassword(ReadProperties.getPropertyByKey(PASSWORD));
        try {
            connPool.setDriverClass(ReadProperties.getPropertyByKey(DRIVER));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to open new connection
     *
     * @return Connection
     * @throws SQLException
     */
    public Connection open() throws SQLException {
        return connPool.getConnection();
    }

    /**
     * Method to close existing connection
     */
    public void close() {
        connPool.close();
    }

}
