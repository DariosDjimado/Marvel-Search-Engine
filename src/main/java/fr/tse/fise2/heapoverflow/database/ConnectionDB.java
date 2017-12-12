package fr.tse.fise2.heapoverflow.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @author Darios DJIMADO
 */
public class ConnectionDB {
    private static final String JDBC_URl = "jdbc:derby:lc_database;create=true";
    private static ConnectionDB connectionDB;
    private Connection connection;

    private ConnectionDB() {
        try {
            this.connection = DriverManager.getConnection(JDBC_URl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionDB getConnectionDB() {
        if (connectionDB == null) {
            connectionDB = new ConnectionDB();
        }
        return connectionDB;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
