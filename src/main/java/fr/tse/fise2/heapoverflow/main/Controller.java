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
import fr.tse.fise2.heapoverflow.marvelapi.Series;

import java.awt.*;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.*;

public class Controller {
    private static DataShow dataShow;
    private final ConnectionDB connectionDB;
    private final CharactersTable charactersTable;
    private final ComicsTable comicsTable;
    private final UI ui;
    private final SearchButtonLIstenner searchButtonLIstenner;
    private final SelectionChangedListenner selectionChangedListenner;
    private final SelectionChangedListenner selectionChangedListennerExtra;
    private final AutoCompletion autoCompletion;
    private MarvelRequest request;


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
        this.autoCompletion = new AutoCompletion(this, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 1f);
        //
        this.searchButtonLIstenner = new SearchButtonLIstenner(this);
        ui.getUiSearchComponent().setSearchButtonListener(this.searchButtonLIstenner);
        //
        this.selectionChangedListenner = new SelectionChangedListenner(this);
        this.ui.getUiSearchComponent().setSelectionChangedListener(this.selectionChangedListenner);
        //
        this.selectionChangedListennerExtra = new SelectionChangedListenner(this);
        this.ui.getUiExtraComponent().setSelectionChangedListenner(this.selectionChangedListennerExtra);
        //
        this.request = new MarvelRequest();
    }

    public void onSelectionChanged(String name) {
        System.out.println(name);
        try {

            if (this.ui.getUiSearchComponent().getCharactersRadioButton().isSelected()) {
                String response = this.request.getData("characters/" + name.toLowerCase());
                Character fetched = deserializeCharacters(response).getData().getResults()[0];
                DataShow.DrawCharacter(this.ui.getCenterWrapperPanel(), fetched);
                this.ui.revalidate();
            }
            if (this.ui.getUiSearchComponent().getComicsRadioButton().isSelected()) {
                String response = this.request.getData("comics/" + name.toLowerCase());
                Comic fetched = deserializeComics(response).getData().getResults()[0];
                DataShow.DrawComic(this.ui.getCenterWrapperPanel(), fetched);
                this.ui.revalidate();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void searchStartsWith(String text) {
        this.autoCompletion.getAutoSuggestionPopUpWindow().setVisible(false);
        try {
            if (this.ui.getUiSearchComponent().getCharactersRadioButton().isSelected()) {
                String response = this.request.getData("characters?nameStartsWith=" + text.toLowerCase() + "&limit=50");
                Character[] fetched = deserializeCharacters(response).getData().getResults();
                this.ui.getUiSearchComponent().setResultsCharacters(fetched);
                this.ui.revalidate();
            }
            if (this.ui.getUiSearchComponent().getComicsRadioButton().isSelected()) {
                String response = this.request.getData("comics?titleStartsWith=" + text.toLowerCase() + "&limit=50");
                Comic[] fetched = deserializeComics(response).getData().getResults();
                this.ui.getUiSearchComponent().setResultsComics(fetched);
                this.ui.revalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emitSearchCharacterById(String id) {
        try {
            String response = this.request.getData("characters/" + SearchHandler.getCurrentSearch());
            Character fetched = deserializeCharacters(response).getData().getResults()[0];
            DataShow.DrawCharacter(this.getUi().getCenterWrapperPanel(), fetched);
            this.ui.revalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emitSearchComicById(String id) {
        try {
            String response = this.request.getData("comics/" + SearchHandler.getCurrentSearch());
            Comic fetched = deserializeComics(response).getData().getResults()[0];

            // serie
            String serie = fetched.getSeries().getName().split("\\(")[0].trim();

            String responseSeries = this.request.getData("series?title=" + serie);
            Series[] series = deserializeSeries(responseSeries).getData().getResults();
            // comics
            String responseComics = this.request.getData("series/" + series[0].getId() + "/comics");
            Comic[] com = deserializeComics(responseComics).getData().getResults();
            this.ui.getUiExtraComponent().setResultsComics(com);


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


