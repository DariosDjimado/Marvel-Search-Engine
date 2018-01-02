package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.authentication.User;
import fr.tse.fise2.heapoverflow.database.*;
import fr.tse.fise2.heapoverflow.events.RequestListener;
import fr.tse.fise2.heapoverflow.events.SelectionChangedListener;
import fr.tse.fise2.heapoverflow.gui.*;
import fr.tse.fise2.heapoverflow.interfaces.CharactersRequestObserver;
import fr.tse.fise2.heapoverflow.interfaces.ComicsRequestObserver;
import fr.tse.fise2.heapoverflow.interfaces.IRequestListener;
import fr.tse.fise2.heapoverflow.interfaces.ISelectionChangedListener;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;
import okhttp3.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class Controller extends InternalController implements IRequestListener, ISelectionChangedListener, ComicsRequestObserver, CharactersRequestObserver {
    // Model
    private final static Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private static Controller con;
    private static Cache urlsCache;
    private final SelectionChangedListener selectionChangedListenerExtra;
    private final RequestListener requestListener;
    // Vue
    private final DataShow dataShow;
    private final UI ui;
    private AutoCompletion autoCompletion;
    private MarvelRequest request;


    public Controller(UI ui) {
        con = this;

        // store ui
        this.ui = ui;

        //
        this.dataShow = new DataShow(this.getUi().getCenterWrapperPanel());
        this.gradesPanelMVC();
        //
        this.ui.getUiSearchComponent().setController(this);
        //
        this.selectionChangedListenerExtra = new SelectionChangedListener(this);
        this.ui.getUiExtraComponent().setSelectionChangedListener(this.selectionChangedListenerExtra);
        //
        this.request = MarvelRequest.getInstance();
        //
        this.requestListener = new RequestListener(this);
        this.request.addRequestListener(this.requestListener);


        try {
            AppConfig.tmpDir = Files.createTempDirectory("appdarios") + "/";
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.initFavoriteButton();
        this.initReadButton();
        this.initCreateCollectionButton();

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
                        ConnectionDB.getInstance().getConnection().close();
                        System.out.println(ConnectionDB.getInstance().getConnection().isClosed());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    System.exit(0);
                }
            }
        });

        UserAuthenticationModel.getInstance().addObserver(this.ui.getUiExtraComponent());
        // this.emitSearchComicById("61522");

    }

    public static Controller getController() {
        return con;
    }

    public static Cache getUrlsCache() {
        return urlsCache;
    }


    private void initFavoriteButton() {
        final FavoriteButtonView favoriteButtonView = this.dataShow.getBtnPane().getFavoriteButtonView();

        UserAuthenticationModel.getInstance().addObserver(favoriteButtonView);

        favoriteButtonView.addActionListener(e -> {
            User user = UserAuthenticationModel.getUser();
            if (user != null) {
                if (favoriteButtonView.isState()) {
                    ElementAssociationRow favRow = ElementsAssociation
                            .findElement(user.getId(),
                                    favoriteButtonView.getId(), favoriteButtonView.getType());
                    LOGGER.debug("Deleting Favorite " + favoriteButtonView.getId());
                    ElementsAssociation.updateFavorite(favRow.getUid(), favRow.getUserId(), false);
                    favoriteButtonView.setState(false);
                    this.ui.getUiExtraComponent().getRightWrapperPanel().repaint();
                } else {
                    LOGGER.debug("Adding Favorite " + favoriteButtonView.getId());
                    int userId = user.getId();
                    ElementsAssociation.updateFavoriteCreateAsNeeded(favoriteButtonView.getId(),
                            favoriteButtonView.getElementName(), userId, true, favoriteButtonView.getType());
                    favoriteButtonView.setState(true);
                    this.ui.getUiExtraComponent().getRightWrapperPanel().repaint();
                }
            }
        });
    }


    private void initReadButton() {

        final ReadButtonView readButtonView = this.dataShow.getBtnPane().getReadButtonView();

        UserAuthenticationModel.getInstance().addObserver(readButtonView);

        readButtonView.addActionListener(e -> {
            User user = UserAuthenticationModel.getUser();
            if (user != null) {
                if (readButtonView.isState()) {
                    ElementAssociationRow elementAssociationRow = ElementsAssociation
                            .findElement(user.getId(),
                                    readButtonView.getId(), readButtonView.getType());


                    System.out.println("Deleting Favorite " + readButtonView.getId());


                    ElementsAssociation.updateRead(elementAssociationRow.getUid(), elementAssociationRow.getUserId(), false);
                    readButtonView.setState(false);


                    this.ui.getUiExtraComponent().getRightWrapperPanel().repaint();
                } else {

                    System.out.println("Adding Favorite " + readButtonView.getId());


                    int userId = user.getId();
                    ElementsAssociation.updateReadCreateAsNeeded(readButtonView.getId(),
                            readButtonView.getElementName(), userId, true, readButtonView.getType());

                    readButtonView.setState(true);


                    this.ui.getUiExtraComponent().getRightWrapperPanel().repaint();
                }
            }
        });
    }


    private void initCreateCollectionButton() {
        this.ui.getUiTopComponent().getCreateCollectionButton().addActionListener(e -> {
            final JLabel titleLabel = new JLabel("Title");
            final JTextField textField = new JTextField();
            final JLabel descLabel = new JLabel("Description");
            final JTextArea descArea = new JTextArea(10, 1);

            int option = JOptionPane.showConfirmDialog(ui, new Object[]{titleLabel, textField, descLabel, descArea},
                    "Sign in", JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null);

            if (option == JOptionPane.YES_OPTION) {
                try {
                    CollectionsListTable.insertCollection(textField.getText(), descArea.getText());

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


    void init() {
        AutoCompletion autoCompletion = new AutoCompletion(this.ui, this.ui.getUiSearchComponent().getSearchTextField(), this);
        autoCompletion.initComponent();
        this.ui.getUiSearchComponent().getSearchTextField().requestFocusInWindow();
        this.ui.setVisible(true);
    }

    private void gradesPanelMVC() {
        final GradesPanelView gradesPanelView = this.dataShow.getBtnPane().getGradesPanelView();
        List<GradesPanel.GradeButton> gradeButtonList = gradesPanelView.getGrades();
        for (GradesPanel.GradeButton button : gradeButtonList) {
            button.addActionListener(e -> {
                User user = UserAuthenticationModel.getUser();
                if (user != null) {
                    ElementsAssociation.updateGradeAsNeeded(gradesPanelView.getId(), gradesPanelView.getElementName(), user.getId(), button.getGrade(), gradesPanelView.getType());
                    this.ui.getUiExtraComponent().getRightWrapperPanel().repaint();
                    gradesPanelView.setCurrentGrade(button.getGrade());
                }
            });
        }
    }


    private void initCacheUrlsTable() {
        try {
            CacheUrlsTable.empty();
            final Iterator<String> urls = urlsCache.urls();
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Importing urls from cache");
            }
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
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Urls imported successfully");
            }
        } catch (IOException | SQLException e) {
            AppErrorHandler.onError(e);
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    public void searchStartsWith(String text) {
        //this.autoCompletion.getPopUpWindow().setVisible(false);
        try {
            if (this.ui.getUiSearchComponent().getCharactersRadioButton().isSelected()) {
                Thread fetchCharacterById = new FetchData(this, "characters", "nameStartsWith=" + text.toLowerCase() +
                        "&limit=50", FetchData.CharactersType.CHARACTERS);
                fetchCharacterById.run();
            }
            if (this.ui.getUiSearchComponent().getComicsRadioButton().isSelected()) {
                Thread fetchCharacterById = new FetchData(this, "comics", "titleStartsWith=" + text.toLowerCase() +
                        "&limit=50", FetchData.ComicsType.COMICS);
                fetchCharacterById.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emitSearchCharacterById(int id) {
        Thread fetchCharacterById = new FetchData(this, "characters/" + id, null, FetchData.CharactersType.CHARACTER_BY_ID);
        fetchCharacterById.run();
    }

    public void emitSearchComicById(int id) {
        Thread fetchComic = new FetchData(this, "comics/" + id, null, FetchData.ComicsType.COMIC_BY_ID);
        fetchComic.run();
    }

    public UI getUi() {
        return ui;
    }

    @Override
    public void startLoading(String name) {
        this.ui.getUiBottomComponent().getUrlLabel().setText("start: " + name);
        if (!this.ui.getUiBottomComponent().getProgressBar().isVisible()) {
            this.ui.getUiBottomComponent().getProgressBar().setVisible(true);
        }
        this.ui.repaint();
        this.ui.revalidate();

    }

    @Override
    public void endLoading(String name) {
        this.ui.getUiBottomComponent().getUrlLabel().setText("end: " + name);
        this.ui.getUiBottomComponent().getProgressBar().setVisible(false);
        this.ui.repaint();
        this.ui.revalidate();
    }

    @Override
    public void showComic(Comic comic) {
        EventQueue.invokeLater(() -> {
            try {
                customDrawComic(comic);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void customDrawComic(Comic comic) {
        EventQueue.invokeLater(() -> {
            this.dataShow.getBtnPane().setComic(comic);
            dataShow.DrawComic(comic);
            this.ui.revalidate();
            this.ui.repaint();
        });
    }

    @Override
    public void showCharacter(Character character) {
        System.out.println(character);
        customDrawCharacter(character);
    }

    @Override
    public void onFetchingComics(String url) {
        this.ui.setTitle("start loading " + url);
        this.ui.repaint();
        this.ui.revalidate();
    }

    @Override
    public void onFetchedComics(Comic[] comics) {
        this.ui.getUiSearchComponent().setResultsComics(comics);
        this.ui.revalidate();
    }

    @Override
    public void onFetchedComicById(Comic comic) {
        // show the comic
        customDrawComic(comic);
        this.fetchComicsInSameSeries(comic);
    }

    public void fetchComicsInSameSeries(Comic comic) {
        System.out.println(comic.getSeries());
        if (comic.getSeries() != null) {
            // since we have the comic now we are going to fetch all comics in same series by using series id returned
            Thread fetchComicsInSameSeries = new FetchData(this, "series/" + comic.getSeries().getResourceURI().substring(43) + "/comics", null, FetchData.ComicsType.COMICS_IN_SAME_SERIES);
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
        this.ui.getUiSearchComponent().setResultsCharacters(characters);
        this.ui.revalidate();
    }

    @Override
    public void onFetchedCharactersById(Character character) {
        // show character
        customDrawCharacter(character);
        this.fetchCharactersInSameComic(character);
    }

    private void customDrawCharacter(Character character) {
        EventQueue.invokeLater(() -> {
            this.dataShow.getBtnPane().setCharacter(character);
            dataShow.DrawCharacter(character);
            this.ui.revalidate();
            this.ui.repaint();
        });
    }

    public void fetchCharactersInSameComic(Character character) {
        // if there are comics returned then we'll get the others characters
        if (character.getComics().getReturned() > 0) {
            Thread fetchCharactersInSameComic = new FetchData(this, "series/" + character.getComics().getItems()[0].getResourceURI().substring(43) + "/characters", null, FetchData.CharactersType.CHARACTERS_IN_SAME_SERIES);
            fetchCharactersInSameComic.run();
        }
    }

    @Override
    public void onFetchedCharactersInSameComic(Character[] characters) {
        EventQueue.invokeLater(() -> this.ui.getUiExtraComponent().setResultsCharacters(characters));
    }

}


