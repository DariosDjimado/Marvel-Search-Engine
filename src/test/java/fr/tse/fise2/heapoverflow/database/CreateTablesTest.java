package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Darios DJIMADO
 */
public class CreateTablesTest {

    private static ConnectionDB CONNECTION_DB;

    @BeforeClass
    public static void setupConfig() {
        AppErrorHandler.configureLogging();
        CONNECTION_DB = ConnectionDB.getInstance();
        DataBase.cleanUpDB(CONNECTION_DB);
    }

    @AfterClass
    public static void finishedConfig() throws Exception {
        DataBase.cleanUpDB(CONNECTION_DB);
        CONNECTION_DB.closeConnection();
    }

    @Before
    public void setUp() {
        DataBase.cleanUpDB(CONNECTION_DB);
    }

    @Test
    public void createAllTables() {
        CreateTables.createAllTables();
        assertTrue(CreateTables.tableIsCreated("cache_urls"));
        assertTrue(CreateTables.tableIsCreated("users"));
        assertTrue(CreateTables.tableIsCreated("elements"));
        assertTrue(CreateTables.tableIsCreated("collections"));
        assertTrue(CreateTables.tableIsCreated("elements_association"));
    }

    @Test
    public void createCacheUrlsTable() {
        CreateTables.createCacheUrlsTable();
        assertTrue(CreateTables.tableIsCreated("cache_urls"));
    }

    @Test
    public void createUsersTable() {
        CreateTables.createUsersTable();
        assertTrue(CreateTables.tableIsCreated("users"));
    }

    @Test
    public void createElementsTable() {
        CreateTables.createElementsTable();
        assertTrue(CreateTables.tableIsCreated("elements"));
    }

    @Test
    public void createCollectionsListTable() {
        CreateTables.createCollectionsListTable();
        assertTrue(CreateTables.tableIsCreated("collections"));
    }

    @Test
    public void createElementsAssociationTable() {
        CreateTables.createUsersTable();
        CreateTables.createElementsTable();
        CreateTables.createCollectionsListTable();
        CreateTables.createElementsAssociationTable();
        assertTrue(CreateTables.tableIsCreated("elements_association"));
    }

    @Test
    public void tableIsCreated() {
        ConnectionDB connectionDB = ConnectionDB.getInstance();
        try {
            connectionDB.getConnection().createStatement().execute("CREATE TABLE TEST_TABLE(id INT PRIMARY KEY )");
            assertTrue(CreateTables.tableIsCreated("test_table"));
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }
}