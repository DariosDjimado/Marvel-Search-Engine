package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.database.CharactersTable;
import fr.tse.fise2.heapoverflow.database.ComicsTable;
import fr.tse.fise2.heapoverflow.database.ConnectionDB;
import fr.tse.fise2.heapoverflow.events.RequestListener;
import fr.tse.fise2.heapoverflow.events.SearchButtonListener;
import fr.tse.fise2.heapoverflow.events.SelectionChangedListener;
import fr.tse.fise2.heapoverflow.gui.AutoCompletion;
import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.gui.SearchHandler;
import fr.tse.fise2.heapoverflow.gui.UI;
import fr.tse.fise2.heapoverflow.interfaces.*;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeCharacters;
import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

public class Controller implements IRequestListener, ISelectionChangedListener, ComicsRequestObserver, CharactersRequestObserver {
    private static LoggerObserver LOGGER_OBSERVER;
    private static DataShow dataShow;
    private final ConnectionDB connectionDB;
    private final CharactersTable charactersTable;
    private final ComicsTable comicsTable;
    private final UI ui;
    private final SearchButtonListener searchButtonListener;
    private final SelectionChangedListener selectionChangedListener;
    private final SelectionChangedListener selectionChangedListenerExtra;
    private final AutoCompletion autoCompletion;
    private final RequestListener requestListener;
    private MarvelRequest request;


    public Controller(UI ui, final LoggerObserver loggerObserver) {

        DataBaseErrorHandler dataBaseErrorHandler = new DataBaseErrorHandler();
        // init connection to database
        this.connectionDB = new ConnectionDB(dataBaseErrorHandler);
        // init charactersTable
        this.charactersTable = new CharactersTable(this.connectionDB);
        // init comics table
        this.comicsTable = new ComicsTable(this.connectionDB);
        // store ui
        this.ui = ui;
        this.autoCompletion = new AutoCompletion(this, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 1f);
        //
        this.searchButtonListener = new SearchButtonListener(this);
        ui.getUiSearchComponent().setSearchButtonListener(this.searchButtonListener);
        //
        this.selectionChangedListener = new SelectionChangedListener(this);
        this.ui.getUiSearchComponent().setSelectionChangedListener(this.selectionChangedListener);
        //
        this.selectionChangedListenerExtra = new SelectionChangedListener(this);
        this.ui.getUiExtraComponent().setSelectionChangedListener(this.selectionChangedListenerExtra);
        //
        this.request = new MarvelRequest();
        //
        this.requestListener = new RequestListener(this);
        this.request.addRequestListener(this.requestListener);
        // init logger
        LOGGER_OBSERVER = loggerObserver;


        try {
            AppConfig.tmpDir = Files.createTempDirectory("appdarios") + "/";
        } catch (IOException e) {
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
        Thread fetchCharacterById = new FetchData(this, "characters/" + SearchHandler.getCurrentSearch(), FetchData.CharactersType.CHARACTER_BY_ID);
        fetchCharacterById.run();

        try {


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emitSearchComicById(String id) {
        Thread fetchComic = new FetchData(this, "comics/" + SearchHandler.getCurrentSearch(), FetchData.ComicsType.COMIC_BY_ID);
        fetchComic.run();
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

    public static LoggerObserver getLoggerObserver() {
        return LOGGER_OBSERVER;
    }

    public static void setLoggerObserver(LoggerObserver loggerObserver) {
        LOGGER_OBSERVER = loggerObserver;
    }

    @Override
    public void startLoading(String name) {
        this.ui.setTitle("start loading " + name);
        this.ui.repaint();
        this.ui.revalidate();

    }

    @Override
    public void endLoading(String name) {
        this.ui.setTitle("end loading " + name);
        this.ui.repaint();
        this.ui.revalidate();
    }

    @Override
    public void showComic(Comic comic) {
        EventQueue.invokeLater(() -> {
            try {
                EventQueue.invokeLater(() -> {
                    DataShow.DrawComic(this.ui.getCenterWrapperPanel(), comic);
                    this.ui.revalidate();
                    this.ui.repaint();
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void showCharacter(Character character) {
        System.out.println(character);
        EventQueue.invokeLater(() -> {
            DataShow.DrawCharacter(this.ui.getCenterWrapperPanel(), character);
            this.ui.revalidate();
            this.ui.repaint();
        });
    }

    @Override
    public void onFetchingComics(String url) {
        this.ui.setTitle("start loading " + url);
        this.ui.repaint();
        this.ui.revalidate();
    }

    @Override
    public void onFetchedComics(Comic[] comics) {

    }

    @Override
    public void onFetchedComicById(Comic comic) {
        // show the comic
        EventQueue.invokeLater(() -> {
            DataShow.DrawComic(this.getUi().getCenterWrapperPanel(), comic);
            this.ui.revalidate();
            this.ui.repaint();
        });
        // since we have the comic now we are going to fetch all comics in same series by using series id returned
        Thread fetchComicsInSameSeries = new FetchData(this, "series/" + comic.getSeries().getResourceURI().substring(42) + "/comics", FetchData.ComicsType.COMICS_IN_SAME_SERIES);
        fetchComicsInSameSeries.run();
    }

    @Override
    public void onFetchedComicsInSameSeries(Comic[] comics) {
        this.ui.getUiExtraComponent().setResultsComics(comics);
    }

    @Override
    public void onFetchingCharacters(String url) {
        this.ui.setTitle("start loading " + url);
        this.ui.repaint();
        this.ui.revalidate();
    }

    @Override
    public void onFetchedCharacters(Character[] characters) {

    }

    @Override
    public void onFetchedCharactersById(Character character) {
        // show character
        EventQueue.invokeLater(() -> {
            DataShow.DrawCharacter(this.getUi().getCenterWrapperPanel(), character);
            this.ui.revalidate();
            this.ui.repaint();
        });
        // if there are comics returned then we'll get the others characters
        if (character.getComics().getReturned() > 0) {
            String firstComicId = character.getComics().getItems()[0].getResourceURI().substring(43);
            Thread fetchCharactersInSameComic = new FetchData(this, "series/" + firstComicId + "/characters", FetchData.CharactersType.CHARACTERS_IN_SAME_SERIES);
            fetchCharactersInSameComic.run();
        }
    }

    @Override
    public void onFetchedCharactersInSameComic(Character[] characters) {
        EventQueue.invokeLater(() -> {
            this.ui.getUiExtraComponent().setResultsCharacters(characters);
        });
    }
}


