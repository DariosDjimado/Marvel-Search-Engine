package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.database.CharactersTable;
import fr.tse.fise2.heapoverflow.database.ComicsTable;
import fr.tse.fise2.heapoverflow.database.ConnectionDB;
import fr.tse.fise2.heapoverflow.database.CreateTables;
import fr.tse.fise2.heapoverflow.gui.AutoCompletion;
import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.gui.SearchHandler;
import fr.tse.fise2.heapoverflow.gui.UI;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;

import java.awt.*;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeCharacters;
import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

public class Controller {
    private static DataShow dataShow;
    private final ConnectionDB connectionDB;
    private final CharactersTable charactersTable;
    private final ComicsTable comicsTable;
    private final UI ui;
    private  MarvelRequest request;


    public Controller(UI ui) {

        DataBaseErrorHandler dataBaseErrorHandler = new DataBaseErrorHandler();
        // init connection to database
        this.connectionDB = new ConnectionDB(dataBaseErrorHandler);
        // create tables
        CreateTables tables = new CreateTables(this.connectionDB);
        tables.createComicsTable();
        // init charactersTable
        this.charactersTable = new CharactersTable(this.connectionDB);
        // init comics table
        this.comicsTable = new ComicsTable(this.connectionDB);
        // store ui
        this.ui = ui;

        AutoCompletion autoCompletion = new AutoCompletion(this, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 1f);

        //
        this.request = new MarvelRequest();
    }

    public static void emitEvent(String str) {


        if (dataShow != null) {

            MarvelRequest request = new MarvelRequest();
            try {
                String response = request.getData("comics/" + SearchHandler.getCurrentSearch());
                Comic fetched = deserializeComics(response).getData().getResults()[0];
                System.out.println(fetched);

                //dataShow.onComicAvailable(fetched);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MarvelRequest request = new MarvelRequest();
            try {
                String response = request.getData("comics/" + SearchHandler.getCurrentSearch());
                Comic fetched = deserializeComics(response).getData().getResults()[0];
                //  System.out.println(fetched);
                // dataShow = new DataShow(fetched);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void emitSearchCracterById(String id) {
        try {
            String response = this.request.getData("characters/" + SearchHandler.getCurrentSearch());
            Character fetched = deserializeCharacters(response).getData().getResults()[0];
            DataShow.DrawCharacter(this.getUi().getCenterWrapperPanel(), fetched);
            this.ui.revalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void emitSearchComicById(String id){
        try {
            String response = this.request.getData("comics/" + SearchHandler.getCurrentSearch());
            Comic fetched = deserializeComics(response).getData().getResults()[0];
            DataShow.DrawComic(this.getUi().getCenterWrapperPanel(), fetched);
            this.ui.revalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public UI getUi() {
        return ui;
    }
}


