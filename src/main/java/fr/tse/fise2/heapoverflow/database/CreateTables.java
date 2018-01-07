package fr.tse.fise2.heapoverflow.database;

import org.intellij.lang.annotations.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Darios DJIMADO
 */
public final class CreateTables {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateTables.class);

    /**
     * Creates all tables.
     *
     * @return boolean. True if all tables are successfully created otherwise false will be returned.
     */
    public static boolean createAllTables() {
        return createUsersTable()
                && createElementsTable()
                && createCacheUrlsTable()
                && createCollectionsTable()
                && createElementsAssociationTable()
                && createWikipediaUrlsTable()
                && createFirstAppearanceTable();
    }

    /**
     * Creates cache urls table
     *
     * @return boolean. True if comics table is successfully creates otherwise false will be returned.
     */
    public static boolean createCacheUrlsTable() {
        return createTable("CREATE TABLE cache_urls(" +
                "shorten_url VARCHAR(255) PRIMARY KEY NOT NULL," +
                " complete_url VARCHAR (255) NOT NULL )");
    }

    /**
     * Creates users table.
     *
     * @return boolean. True if comics table is successfully creates otherwise false will be returned.
     */
    public static boolean createUsersTable() {
        return createTable("CREATE TABLE users(id INT NOT NULL PRIMARY KEY" +
                " GENERATED ALWAYS AS IDENTITY" +
                "(START WITH 1, INCREMENT BY 1) , " +
                "username VARCHAR(50) UNIQUE NOT NULL , " +
                "email VARCHAR(100) UNIQUE NOT NULL , " +
                "last_name VARCHAR(50) NOT NULL , " +
                "first_name VARCHAR(50)  NOT NULL , " +
                "password VARCHAR(100) NOT NULL )");
    }


    /**
     * Creates elements table.
     *
     * @return boolean. True if comics table is successfully creates otherwise false will be returned.
     */
    public static boolean createElementsTable() {
        return createTable("CREATE TABLE elements(uid INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS " +
                "IDENTITY (START WITH 1, INCREMENT BY 1)," +
                "id INT NOT NULL UNIQUE , " +
                "type INT NOT NULL, " +
                "name VARCHAR(255) NOT NULL ) ");
    }


    /**
     * Creates collections table.
     *
     * @return boolean. True if comics table is successfully creates otherwise false will be returned.
     */
    public static boolean createCollectionsTable() {
        return createTable("CREATE TABLE COLLECTIONS(" +
                "collection_id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1,INCREMENT BY 1), " +
                "title VARCHAR(255) NOT NULL," +
                "description VARCHAR(255) NOT NULL," +
                "user_id INT NOT NULL REFERENCES USERS(ID))");
    }


    /**
     * Creates elements association table.
     *
     * @return boolean. True if comics table is successfully creates otherwise false will be returned.
     */
    public static boolean createElementsAssociationTable() {
        return createTable("CREATE TABLE ELEMENTS_ASSOCIATION ( uid INT NOT NULL REFERENCES ELEMENTS (UID), " +
                "user_id INT NOT NULL REFERENCES USERS(ID)," +
                "favorite BOOLEAN NOT NULL DEFAULT FALSE," +
                "collection_id INT REFERENCES COLLECTIONS(COLLECTION_ID)," +
                "is_read BOOLEAN NOT NULL DEFAULT FALSE," +
                "grade INT NOT NULL DEFAULT 0, " +
                "comment VARCHAR(255), " +
                "owned BOOLEAN NOT NULL DEFAULT FALSE," +
                "PRIMARY KEY(uid, user_id))");
    }

    public static boolean createWikipediaUrlsTable() {
        return createTable("CREATE TABLE WIKIPEDIA_URLS(" +
                "CHARACTER_NAME VARCHAR(255) NOT NULL ," +
                " CHARACTER_LABEL VARCHAR(255)," +
                " CHARACTER_URL VARCHAR(255)," +
                " CHARACTER_ALIAS VARCHAR(255)," +
                " CHARACTER_DESCRIPTION VARCHAR(255)," +
                "PRIMARY KEY (CHARACTER_NAME,CHARACTER_URL))");
    }

    public static boolean createFirstAppearanceTable(){
        return createTable("CREATE TABLE FIRST_APPEARANCE(" +
                " CHARACTERR VARCHAR(255) NOT NULL," +
                "DATE VARCHAR(255)," +
                "COMIC VARCHAR(255)," +
                "PRIMARY KEY (CHARACTERR,COMIC))");
    }


    /**
     * Creates table from sql statement
     *
     * @param sql The statement
     * @return True if comics table is successfully creates otherwise false will be returned.
     */
    private static boolean createTable(@Language("Derby") String sql) {
        boolean execute;
        try (Statement statement = ConnectionDB.getInstance().getConnection().createStatement()) {
            statement.execute(sql);
            execute = true;
        } catch (SQLException e) {
            e.printStackTrace();
            execute = false;
        }
        return execute;
    }

    /**
     * Checks if a table is created
     *
     * @param tableName the name of the table
     * @return true if table exists
     */
    public static boolean tableIsCreated(String tableName) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("check if table " + tableName.toUpperCase() + " exists in database");
        }
        boolean found;
        try {
            final DatabaseMetaData databaseMetaData = ConnectionDB.getInstance().getConnection().getMetaData();
            final ResultSet resultSet = databaseMetaData.getTables(null, null, tableName.toUpperCase(), null);
            found = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            found = false;
        }
        if (LOGGER.isDebugEnabled()) {
            if (found) {
                LOGGER.debug(tableName + " exists");
            } else {
                LOGGER.debug(tableName + " doesn't exists");
            }
        }
        return found;
    }
}
