package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.database.CharactersTable;
import fr.tse.fise2.heapoverflow.database.ConnectionDB;
import fr.tse.fise2.heapoverflow.database.CreateTables;
import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.gui.SearchHandler;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

public class Controller {
    private static DataShow dataShow;
    private final ConnectionDB connectionDB;
    private final CharactersTable charactersTable;


    public Controller() {

        DataBaseErrorHandler dataBaseErrorHandler = new DataBaseErrorHandler();
        // init connection to database
        this.connectionDB = new ConnectionDB(dataBaseErrorHandler);
        // create tables
        CreateTables tables = new CreateTables(this.connectionDB);
        tables.createAllTables();
        // init charactersTable
        this.charactersTable = new CharactersTable(this.connectionDB);

    }

    public static void emitEvent(String str) {


        if (dataShow != null) {

            MarvelRequest request = new MarvelRequest();
            try {
                String response = request.getData("comics/" + SearchHandler.getCurrentSearch());
                Comic fetched = deserializeComics(response).getData().getResults()[0];
                System.out.println(fetched);

//                dataShow.onComicAvailable(fetched);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MarvelRequest request = new MarvelRequest();
            try {
                String response = request.getData("comics/" + SearchHandler.getCurrentSearch());
                Comic fetched = deserializeComics(response).getData().getResults()[0];
                System.out.println(fetched);
//                dataShow = new DataShow(fetched);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public ConnectionDB getConnectionDB() {
        return connectionDB;
    }

    public CharactersTable getCharactersTable() {
        return charactersTable;
    }
}


