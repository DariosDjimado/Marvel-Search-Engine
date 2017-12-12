package fr.tse.fise2.heapoverflow.database;

import org.intellij.lang.annotations.Language;

import java.sql.SQLException;

/**
 * @author Darios DJIMADO
 */
public final class CreateTables {
    /**
     * Creates all tables.
     *
     * @return boolean. True if all tables are successfully created otherwise false will be returned.
     */
    public static boolean createAllTables() {
        return createUsersTable() && createElementsTable() &&
                createCharactersTable() && createComicsTable()
                && createFavorites() &&
                createCacheUrlsTable();
    }

    /**
     * Creates characters table.
     *
     * @return boolean. True if characters table is successfully created otherwise false will be returned.
     */
    private static boolean createCharactersTable() {
        return createTable("CREATE TABLE characters(" +
                "id INTEGER NOT NULL REFERENCES elements(uid)," +
                "wikipedia_en_url VARCHAR(255)," +
                "wikipedia_fr_url VARCHAR(255))");
    }

    /**
     * @return boolean. True if comics table is successfully creates otherwise false will be returned.
     */
    public static boolean createComicsTable() {
        return createTable("CREATE TABLE comics(" +
                "id INTEGER NOT NULL REFERENCES ELEMENTS(UID)," +
                "title VARCHAR(255) NOT NULL)");
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
                "username VARCHAR(50) NOT NULL , " +
                "email VARCHAR(100) UNIQUE NOT NULL , " +
                "last_name VARCHAR(50) NOT NULL , " +
                "first_name VARCHAR(50)  NOT NULL , " +
                "password VARCHAR(100) NOT NULL )");
    }

    public static boolean createFavorites() {
        return createTable("CREATE TABLE favorites(id INT NOT NULL, " +
                "type INT NOT NULL, " +
                "user_id INT NOT NULL REFERENCES USERS(ID), PRIMARY KEY (id,user_id))");
    }

    public static boolean createElementsTable() {
        return createTable("CREATE TABLE elements(uid INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS " +
                "IDENTITY (START WITH 1, INCREMENT BY 1)," +
                "id INT NOT NULL, type INT NOT NULL, name VARCHAR(255) NOT NULL ) ");
    }

    private static boolean createTable(@Language("Derby") String s) {
        boolean execute;
        try {
            ConnectionDB.getConnectionDB().getConnection()
                    .createStatement()
                    .execute(s);
            execute = true;
        } catch (SQLException e) {
            e.printStackTrace();
            execute = false;
        }
        return execute;
    }
}
