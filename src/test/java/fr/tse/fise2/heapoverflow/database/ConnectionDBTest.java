package fr.tse.fise2.heapoverflow.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Darios DJIMADO
 */
public class ConnectionDBTest {
    private ConnectionDB connectionDB;

    @Before
    public void setUp() {
        this.connectionDB = ConnectionDB.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        this.connectionDB.closeConnection();
    }

    @Test
    public void getInstance() {
        this.connectionDB = ConnectionDB.getInstance();
        assertNotNull(this.connectionDB);
    }

    @Test
    public void getConnection() {
        Connection connection = this.connectionDB.getConnection();
        assertNotNull(connection);
    }

    @Test
    public void closeConnection() {
        try {
            this.connectionDB.closeConnection();
            assertTrue(this.connectionDB.getConnection().isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}