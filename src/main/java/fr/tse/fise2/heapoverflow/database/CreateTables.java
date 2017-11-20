package fr.tse.fise2.heapoverflow.database;

import java.sql.SQLException;

/**
 * @author Darios DJIMADO
 */
public class CreateTables {
    private final ConnectionDB connectionDB;

    public CreateTables(final ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    /**
     * This method creates all tables.
     *
     * @return boolean. True if all tables are successfully created otherwise false will be returned.
     */
    public boolean createAllTables() {
        return createCharactersTable() && createComicsTable();
    }

    /**
     * @return boolean. True if characters table is successfully created otherwise false will be returned.
     */
    private boolean createCharactersTable() {
        boolean execute;
        try {
            execute = connectionDB.getConnection()
                    .createStatement()
                    .execute("CREATE TABLE characters(" +
                            "id INTEGER PRIMARY KEY NOT NULL," +
                            "name VARCHAR(255) NOT NULL," +
                            "wikipedia_en_url VARCHAR(255)," +
                            "wikipedia_fr_url VARCHAR(255))");
        } catch (SQLException e) {
            connectionDB.getErrorHandler().emitCreateCharactersTableFailed(e);
            execute = false;
        }
        return execute;
    }

    /**
     * @return boolean. True if comics table is successfully creates otherwise false will be returned.
     */
    private boolean createComicsTable() {
        boolean execute;
        try {
            execute = connectionDB.getConnection()
                    .createStatement()
                    .execute("CREATE TABLE comics(" +
                            "id INTEGER PRIMARY KEY NOT NULL," +
                            "title VARCHAR(255) NOT NULL)");
        } catch (SQLException e) {
            connectionDB.getErrorHandler().emitCreateComicsTableFailed(e);
            execute = false;
        }
        return execute;
    }


}
