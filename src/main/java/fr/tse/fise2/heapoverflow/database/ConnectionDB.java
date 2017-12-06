package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.main.DataBaseErrorHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @author Darios DJIMADO
 */
public class ConnectionDB {
    private static final String JDBC_URl = "jdbc:derby:lc_database;create=true";
    private final DataBaseErrorHandler errorHandler;
    private Connection connection;

    public ConnectionDB(final DataBaseErrorHandler errorHandler) {
        this.errorHandler = errorHandler;

        try {
            this.connection = DriverManager.getConnection(JDBC_URl);
        } catch (SQLException e) {
            this.errorHandler.emitConnectionFailed(e);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public DataBaseErrorHandler getErrorHandler() {
        return this.errorHandler;
    }
}
