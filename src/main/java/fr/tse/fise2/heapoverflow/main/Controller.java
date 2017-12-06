package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.database.*;
import fr.tse.fise2.heapoverflow.events.RequestListener;
import fr.tse.fise2.heapoverflow.events.SelectionChangedListener;
import fr.tse.fise2.heapoverflow.gui.AutoCompletion;
import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.gui.UI;
import fr.tse.fise2.heapoverflow.interfaces.*;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;
import okhttp3.Cache;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Iterator;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeCharacters;
import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

public class Controller implements IRequestListener, ISelectionChangedListener, ComicsRequestObserver, CharactersRequestObserver {
    private static LoggerObserver LOGGER_OBSERVER;
    private static Controller con;
    private final ConnectionDB connectionDB;
    private final CharactersTable charactersTable;
    private final ComicsTable comicsTable;
    private final CacheUrlsTable cacheUrlsTable;
    private final UI ui;
    private final SelectionChangedListener selectionChangedListenerExtra;
    private final AutoCompletion autoCompletion;
    private final RequestListener requestListener;
    private final Cache urlsCache;
    private MarvelRequest request;

    public Controller(UI ui, final LoggerObserver loggerObserver) {
        con = this;

        DataBaseErrorHandler dataBaseErrorHandler = new DataBaseErrorHandler();
        // init connection to database
        this.connectionDB = new ConnectionDB(dataBaseErrorHandler);
        // init charactersTable
        this.charactersTable = new CharactersTable(this.connectionDB);
        // init comics table
        this.comicsTable = new ComicsTable(this.connectionDB);
        // store ui
        this.ui = ui;
        this.autoCompletion = new AutoCompletion(this, Color.WHITE.brighter(), Color.BLUE, Color.RED, 1f);
        //
        this.ui.getUiSearchComponent().setController(this);
        //
        this.selectionChangedListenerExtra = new SelectionChangedListener(this);
        this.ui.getUiExtraComponent().setSelectionChangedListener(this.selectionChangedListenerExtra);
        //
        this.request = new MarvelRequest(this);
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
        this.cacheUrlsTable = new CacheUrlsTable(this.connectionDB);
        urlsCache = new Cache(new File("CacheResponse.tmp"), 10 * 1024 * 1024);
        this.initCacheUrlsTable();
    }

    public static Controller getController() {
        return con;
    }

    public static LoggerObserver getLoggerObserver() {
        System.out.println("comparing");
        System.out.println(LOGGER_OBSERVER == null);
        return LOGGER_OBSERVER;
    }

    public static void setLoggerObserver(LoggerObserver loggerObserver) {
        LOGGER_OBSERVER = loggerObserver;
    }

    void init() {
        System.out.println(LOGGER_OBSERVER);

    }

    private void initCacheUrlsTable() {


        try {

            this.cacheUrlsTable.empty();


            Iterator<String> urls = this.urlsCache.urls();

            while (urls.hasNext()) {
                final String completeUrl = urls.next();
                if (!this.cacheUrlsTable.exists(completeUrl)) {
                    String shortenUrl;
                    if (completeUrl.contains("&apikey")) {
                        System.out.println(completeUrl);
                        shortenUrl = completeUrl.substring(0, completeUrl.indexOf("&apikey"));
                    } else {
                        System.out.println(completeUrl);
                        shortenUrl = completeUrl.substring(0, completeUrl.indexOf("?apikey"));
                    }
                    System.out.println("inserting -----> ");
                    System.out.println(shortenUrl + "--------------->" + completeUrl);
                }
            }


        } catch (IOException | SQLException e) {
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
                System.out.println("tex-------------------------------------------------------------------------------t" + text);
                String response = this.request.getData("comics?titleStartsWith=" + text.toLowerCase() + "&limit=50");
                System.out.println("response" + response);
                Comic[] fetched = deserializeComics(response).getData().getResults();
                this.ui.getUiSearchComponent().setResultsComics(fetched);
                this.ui.revalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emitSearchCharacterById(String word) {


        try {
            final String id = String.valueOf(this.getCharactersTable().findCharacterByName(word).getId());
            Thread fetchCharacterById = new FetchData(this, "characters/" + id, FetchData.CharactersType.CHARACTER_BY_ID);
            fetchCharacterById.run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void emitSearchComicById(String word) {
        try {

            final String id = String.valueOf(getComicsTable().findComicByTitle(word).getId());

            System.out.println(getComicsTable().findComicByTitle(word));

            Thread fetchComic = new FetchData(this, "comics/" + id, FetchData.ComicsType.COMIC_BY_ID);
            fetchComic.run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        this.fetchComicsInSameSeries(comic);
    }

    public void fetchComicsInSameSeries(Comic comic) {
        System.out.println(comic.getSeries());
        if (comic.getSeries() != null) {
            // since we have the comic now we are going to fetch all comics in same series by using series id returned
            System.out.println(comic.getId());

            Thread fetchComicsInSameSeries = new FetchData(this, "series/" + comic.getSeries().getResourceURI().substring(42) + "/comics", FetchData.ComicsType.COMICS_IN_SAME_SERIES);
            fetchComicsInSameSeries.run();
        }
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
        this.fetchCharactersInSameComic(character);
    }

    public void fetchCharactersInSameComic(Character character) {
        // if there are comics returned then we'll get the others characters
        if (character.getComics().getReturned() > 0) {
            Thread fetchCharactersInSameComic = new FetchData(this, "series/" + character.getComics().getItems()[0].getResourceURI().substring(43) + "/characters", FetchData.CharactersType.CHARACTERS_IN_SAME_SERIES);
            fetchCharactersInSameComic.run();
        }
    }

    @Override
    public void onFetchedCharactersInSameComic(Character[] characters) {
        EventQueue.invokeLater(() -> this.ui.getUiExtraComponent().setResultsCharacters(characters));
    }

    public CacheUrlsTable getCacheUrlsTable() {
        return cacheUrlsTable;
    }

    public Cache getUrlsCache() {
        return urlsCache;
    }
}


