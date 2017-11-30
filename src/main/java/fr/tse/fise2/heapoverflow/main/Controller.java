package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.database.CharactersTable;
import fr.tse.fise2.heapoverflow.database.ComicsTable;
import fr.tse.fise2.heapoverflow.database.ConnectionDB;
import fr.tse.fise2.heapoverflow.database.CreateTables;
import fr.tse.fise2.heapoverflow.events.RequestListener;
import fr.tse.fise2.heapoverflow.events.SearchButtonListener;
import fr.tse.fise2.heapoverflow.events.SelectionChangedListener;
import fr.tse.fise2.heapoverflow.gui.AutoCompletion;
import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.gui.SearchHandler;
import fr.tse.fise2.heapoverflow.gui.UI;
import fr.tse.fise2.heapoverflow.interfaces.IRequestListener;
import fr.tse.fise2.heapoverflow.interfaces.ISelectionChangedListener;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.*;

import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.*;

public class Controller implements IRequestListener, ISelectionChangedListener {
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
        this.searchButtonListener = new SearchButtonListener(this);
        ui.getUiSearchComponent().setSearchButtonListener(this.searchButtonListener);
        //
        this.selectionChangedListener = new SelectionChangedListener(this);
        this.ui.getUiSearchComponent().setSelectionChangedListener(this.selectionChangedListener);
        //
        this.selectionChangedListenerExtra = new SelectionChangedListener(this);
        this.ui.getUiExtraComponent().setSelectionChangedListenner(this.selectionChangedListenerExtra);
        //
        this.request = new MarvelRequest();
        //
        this.requestListener = new RequestListener(this);
        this.request.addRequestListener(this.requestListener);
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
            Character character = deserializeCharacters(response).getData().getResults()[0];


            EventQueue.invokeLater(() -> {
                DataShow.DrawCharacter(this.getUi().getCenterWrapperPanel(), character);
                this.ui.revalidate();
                this.ui.repaint();
            });


            EventQueue.invokeLater(() -> {
                String responseCharacters = "";
                try {
                    if (character.getSeries().getReturned() > 0) {
                        // serie
                        String serie = character.getSeries().getItems()[0].getName().split("\\(")[0].trim();
                        String responseSeries = this.request.getData("series?title=" + serie);
                        Series[] series = deserializeSeries(responseSeries).getData().getResults();
                        // Characters
                        responseCharacters = this.request.getData("series/" + series[0].getId() + "/characters");

                    }

                    CharacterDataWrapper characterDataWrapper = deserializeCharacters(responseCharacters);
                    if (characterDataWrapper != null && characterDataWrapper.getData() != null) {
                        this.ui.getUiExtraComponent().setResultsCharacters(characterDataWrapper.getData().getResults());
                    } else {
                        this.ui.getUiExtraComponent().setResultsCharacters(null);
                    }


                } catch (IOException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emitSearchComicById(String id) {
        try {
            String response = this.request.getData("comics/" + SearchHandler.getCurrentSearch());
            Comic fetched = deserializeComics(response).getData().getResults()[0];


            EventQueue.invokeLater(() -> {
                DataShow.DrawComic(this.getUi().getCenterWrapperPanel(), fetched);
                this.ui.revalidate();
                this.ui.repaint();
            });


            EventQueue.invokeLater(() -> {
                String responseComics = null;
                try {
                    // serie
                    String serie = fetched.getSeries().getName().split("\\(")[0].trim();
                    String responseSeries = this.request.getData("series?title=" + serie);
                    Series[] series = deserializeSeries(responseSeries).getData().getResults();
                    // comics
                    responseComics = this.request.getData("series/" + series[0].getId() + "/comics");
                    Comic[] com = deserializeComics(responseComics).getData().getResults();
                    this.ui.getUiExtraComponent().setResultsComics(com);
                } catch (IOException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            });


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
            try {
                EventQueue.invokeLater(() -> {
                    DataShow.DrawCharacter(this.ui.getCenterWrapperPanel(), character);
                    this.ui.revalidate();
                    this.ui.repaint();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}


