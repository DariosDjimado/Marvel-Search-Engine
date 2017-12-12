package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.database.*;
import fr.tse.fise2.heapoverflow.events.RequestListener;
import fr.tse.fise2.heapoverflow.events.SelectionChangedListener;
import fr.tse.fise2.heapoverflow.gui.AutoCompletion;
import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.gui.UI;
import fr.tse.fise2.heapoverflow.gui.UITopComponent;
import fr.tse.fise2.heapoverflow.interfaces.*;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;
import okhttp3.Cache;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Iterator;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeCharacters;
import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

public class Controller extends InternalController implements IRequestListener, ISelectionChangedListener, ComicsRequestObserver, CharactersRequestObserver {
    // Model
    private final static Logger LOGGER = Logger.getLogger(Controller.class);
    private static LoggerObserver LOGGER_OBSERVER;
    private static Controller con;
    private final SelectionChangedListener selectionChangedListenerExtra;
    private final RequestListener requestListener;
    private static Cache urlsCache;
    private final UserAuthentication userAuthentication;
    // Vue
    private final DataShow dataShow;
    private final UI ui;
    private final AutoCompletion autoCompletion;
    private MarvelRequest request;


    public Controller(UI ui, final LoggerObserver loggerObserver) {
        con = this;

        DataBaseErrorHandler dataBaseErrorHandler = new DataBaseErrorHandler();
        // init connection to database

        // init charactersTable

/*
        DatabaseMetaData dbm;
        try {
            dbm = ConnectionDB.getConnectionDB().getConnection().getMetaData();
            ResultSet tables = dbm.getTables(null, null, "USERS", null);
            if (!tables.next()) {
                System.out.println(new CreateTables(ConnectionDB.getConnectionDB()).createUsersTable());
                System.out.println("--------------Table USERS was not found and was created-------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        // store ui
        this.ui = ui;
        this.autoCompletion = new AutoCompletion(this, Color.WHITE.brighter(), Color.BLUE, Color.RED, 1f);
        //
        this.dataShow = new DataShow(this.getUi().getCenterWrapperPanel());
        //
        this.ui.getUiSearchComponent().setController(this);
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

        this.initUserAuthentication();

        urlsCache = new Cache(new File("CacheResponse.tmp"), 10 * 1024 * 1024);
        this.initCacheUrlsTable();

        this.ui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(ui,
                        "Are you sure to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

                    try {
                        ConnectionDB.getConnectionDB().getConnection().close();
                        System.out.println(ConnectionDB.getConnectionDB().getConnection().isClosed());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    System.exit(0);
                }
            }
        });
        userAuthentication = UserAuthentication.getUserAuthentication();
    }

    public static Controller getController() {
        return con;
    }

    public static LoggerObserver getLoggerObserver() {
        return LOGGER_OBSERVER;
    }

    public static void setLoggerObserver(LoggerObserver loggerObserver) {
        LOGGER_OBSERVER = loggerObserver;
    }

    private void initUserAuthentication() {
        UserAuthentication.subscribe(this.ui.getUiTopComponent());


        this.ui.getUiTopComponent().buildAuthenticationPanel();
        // sign in
        ui.getUiTopComponent().getLogInButton().addActionListener(e -> {
            ui.getUiTopComponent().showSignUpField(false);

            int option = JOptionPane.showConfirmDialog(ui,
                    ui.getUiTopComponent().getAuthenticationPanel(), "Sign in", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null);

            if (option == JOptionPane.YES_OPTION) {
                // find user
                final String username = ui.getUiTopComponent().getEmailTextField().getText();
                final char[] password = ui.getUiTopComponent().getPasswordTextField().getPassword();
                try {
                    UserRow user = UsersTable.findUserByUsername(username);
                    if (user != null) {
                        if (userAuthentication.passwordsMatch(password, user.getPassword())) {
                            userAuthentication.login(user.getUsername(), user.getPassword());
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        // sign up
        ui.getUiTopComponent().getSignUpButton().addActionListener(e -> {
            if (ui.getUiTopComponent().getSignUpButton().getText().equals("Sign up")) {
                ui.getUiTopComponent().showSignUpField(true);

                int option = JOptionPane.showConfirmDialog(ui,
                        ui.getUiTopComponent().getAuthenticationPanel(), "Sign up", JOptionPane.YES_NO_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null);

                if (option == JOptionPane.YES_OPTION) {
                    final UITopComponent tc = ui.getUiTopComponent();
                    try {
                        String encryptedPassword = userAuthentication
                                .encryptPassword(tc.getPasswordTextField().getPassword());
                        final UserRow userRow = new UserRow(tc.getUsernameTextField().getText(),
                                tc.getEmailTextField().getText(),
                                tc.getLastNameTextField().getText(),
                                tc.getFirstNameTextField().getText(),
                                encryptedPassword);

                        UsersTable.insertUser(userRow);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                userAuthentication.logout();
            }
        });
    }

    void init() {
        this.ui.getUiSearchComponent().getSearchTextField().requestFocusInWindow();
        this.ui.setVisible(true);
    }

    private void initCacheUrlsTable() {
        try {
            CacheUrlsTable.empty();
            final Iterator<String> urls = urlsCache.urls();
            Controller.LOGGER_OBSERVER.onInfo(LOGGER, "Importing urls from cache");
            while (urls.hasNext()) {
                final String completeUrl = urls.next();
                String shortenUrl;
                if (completeUrl.contains("&apikey")) {
                    shortenUrl = completeUrl.substring(0, completeUrl.indexOf("&apikey"));
                } else {
                    shortenUrl = completeUrl.substring(0, completeUrl.indexOf("?apikey"));
                }

                if (!CacheUrlsTable.exists(shortenUrl)) {
                    CacheUrlsTable.insertUrls(shortenUrl, completeUrl);
                }
            }
            Controller.LOGGER_OBSERVER.onInfo(LOGGER, "Urls imported successfully");
        } catch (IOException | SQLException e) {
            Controller.LOGGER_OBSERVER.onError(LOGGER, e);
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
            final String id = String.valueOf(MarvelElementTable.findCharacterByName(word).getId());
            Thread fetchCharacterById = new FetchData(this, "characters/" + id, FetchData.CharactersType.CHARACTER_BY_ID);
            fetchCharacterById.run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void emitSearchComicById(String word) {
        try {

            final String id = String.valueOf(MarvelElementTable.findComicByTitle(word).getId());

            System.out.println(MarvelElementTable.findComicByTitle(word));

            Thread fetchComic = new FetchData(this, "comics/" + id, FetchData.ComicsType.COMIC_BY_ID);
            fetchComic.run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UI getUi() {
        return ui;
    }

    @Override
    public void startLoading(String name) {
        this.ui.setTitle("start loading " + name);
        this.ui.getUiBottomComponent().getUrlLabel().setText("start: " + name);
        this.ui.getUiBottomComponent().getProgressBar().setVisible(true);
        this.ui.repaint();
        this.ui.revalidate();

    }

    @Override
    public void endLoading(String name) {
        this.ui.setTitle("end loading " + name);
        this.ui.getUiBottomComponent().getUrlLabel().setText("end: " + name);
        this.ui.getUiBottomComponent().getProgressBar().setVisible(false);
        this.ui.repaint();
        this.ui.revalidate();
    }

    @Override
    public void showComic(Comic comic) {
        EventQueue.invokeLater(() -> {
            try {
                EventQueue.invokeLater(() -> {
                    dataShow.DrawComic(comic);
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
            dataShow.DrawCharacter(character);
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
            dataShow.DrawComic(comic);
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
            dataShow.DrawCharacter(character);
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


    public static Cache getUrlsCache() {
        return urlsCache;
    }

}


