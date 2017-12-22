package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @author Darios DJIMADO
 */
public final class ConnectionDB {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionDB.class);
    private static final String JDBC_URl = "jdbc:derby:lc_database;create=true";
    private static ConnectionDB instance;
    private static Connection CONNECTION;

    private ConnectionDB() {
        try {
            CONNECTION = DriverManager.getConnection(JDBC_URl);
        } catch (SQLException e) {
            AppErrorHandler.onError(e);
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage(), e);
            }
            System.exit(1);
        }
    }

    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    public static void closeConnection() throws SQLException {
        if (CONNECTION != null) {
            CONNECTION.close();
        }
    }
    public Connection getConnection() {
        return CONNECTION;
    }
}
