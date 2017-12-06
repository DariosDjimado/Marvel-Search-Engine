package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.database.CharactersTable;
import fr.tse.fise2.heapoverflow.database.ComicsTable;
import fr.tse.fise2.heapoverflow.database.ConnectionDB;
import fr.tse.fise2.heapoverflow.main.DataBaseErrorHandler;

public class TasksController {
    private final ConnectionDB connectionDB;
    private final CharactersTable charactersTable;
    private final ComicsTable comicsTable;

    public TasksController() {
        DataBaseErrorHandler dataBaseErrorHandler = new DataBaseErrorHandler();
        // init connection to database
        this.connectionDB = new ConnectionDB(dataBaseErrorHandler);
        // init charactersTable
        this.charactersTable = new CharactersTable(this.connectionDB);
        // init comics table
        this.comicsTable = new ComicsTable(this.connectionDB);
    }

    public ConnectionDB getConnectionDB() {
        return connectionDB;
    }

    public CharactersTable getCharactersTable() {
        return charactersTable;
    }

    public ComicsTable getComicsTable() {
        return comicsTable;
    }
}
