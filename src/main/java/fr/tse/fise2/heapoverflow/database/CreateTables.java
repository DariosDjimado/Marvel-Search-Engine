package fr.tse.fise2.heapoverflow.database;

import java.sql.SQLException;

/**
 * @author Darios DJIMADO
 */
public final class CreateTables {
    private final ConnectionDB connectionDB;

    public CreateTables(final ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    /**
     * Creates all tables.
     *
     * @return boolean. True if all tables are successfully created otherwise false will be returned.
     */
    public boolean createAllTables() {
        return createCharactersTable() && createComicsTable() && createCacheUrlsTable() && createUsersTable();
    }

    /**
     * Creates characters table.
     *
     * @return boolean. True if characters table is successfully created otherwise false will be returned.
     */
    private boolean createCharactersTable() {
        boolean execute;
        try {
            connectionDB.getConnection()
                    .createStatement()
                    .execute("CREATE TABLE characters(" +
                            "id INTEGER PRIMARY KEY NOT NULL," +
                            "name VARCHAR(255) NOT NULL," +
                            "wikipedia_en_url VARCHAR(255)," +
                            "wikipedia_fr_url VARCHAR(255))");
            execute = true;
        } catch (SQLException e) {
            connectionDB.getErrorHandler().emitCreateCharactersTableFailed(e);
            execute = false;
        }
        return execute;
    }

    /**
     * @return boolean. True if comics table is successfully creates otherwise false will be returned.
     */
    public boolean createComicsTable() {
        boolean execute;
        try {
            connectionDB.getConnection()
                    .createStatement()
                    .execute("CREATE TABLE comics(" +
                            "id INTEGER PRIMARY KEY NOT NULL," +
                            "title VARCHAR(255) NOT NULL)");
            execute = true;
        } catch (SQLException e) {
            e.printStackTrace();
            connectionDB.getErrorHandler().emitCreateComicsTableFailed(e);
            execute = false;
        }
        return execute;
    }

    /**
     * Creates cache urls table
     *
     * @return boolean. True if comics table is successfully creates otherwise false will be returned.
     */
    public boolean createCacheUrlsTable() {
        boolean execute;
        try {
            this.connectionDB.getConnection()
                    .createStatement()
                    .execute("CREATE TABLE cache_urls(" +
                            "shorten_url VARCHAR(255) PRIMARY KEY NOT NULL," +
                            " complete_url VARCHAR (255) NOT NULL )");
            execute = true;
        } catch (SQLException e) {
            e.printStackTrace();
            execute = false;
        }
        return execute;
    }

    /**
     * Creates users table.
     *
     * @return boolean. True if comics table is successfully creates otherwise false will be returned.
     */
    public boolean createUsersTable() {
        boolean execute;
        try {
            this.connectionDB.getConnection()
                    .createStatement()
                    .execute("CREATE TABLE users(id INT NOT NULL PRIMARY KEY" +
                            " GENERATED ALWAYS AS IDENTITY" +
                            "(START WITH 1, INCREMENT BY 1) , " +
                            "username VARCHAR(50) NOT NULL , " +
                            "email VARCHAR(100) UNIQUE NOT NULL , " +
                            "last_name VARCHAR(50) NOT NULL , " +
                            "first_name VARCHAR(50)  NOT NULL , " +
                            "password VARCHAR(100) NOT NULL )");
            execute = true;
        } catch (SQLException e) {
            e.printStackTrace();
            execute = false;
        }
        return execute;
    }
}
