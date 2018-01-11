package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.authentication.User;
import fr.tse.fise2.heapoverflow.database.CacheUrlsTable;
import fr.tse.fise2.heapoverflow.database.ConnectionDB;
import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;
import fr.tse.fise2.heapoverflow.database.ElementsAssociation;
import fr.tse.fise2.heapoverflow.events.RequestListener;
import fr.tse.fise2.heapoverflow.gui.*;
import fr.tse.fise2.heapoverflow.interfaces.CharactersRequestObserver;
import fr.tse.fise2.heapoverflow.interfaces.ComicsRequestObserver;
import fr.tse.fise2.heapoverflow.interfaces.IRequestListener;
import fr.tse.fise2.heapoverflow.interfaces.ISelectionChangedListener;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class Controller extends InternalController implements IRequestListener, ISelectionChangedListener, ComicsRequestObserver, CharactersRequestObserver {
    // Model
    private final static Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    // Vue
    private final DataShow dataShow;
    private final UI ui;


    public Controller(UI ui) {
        this.ui = ui;

        //
        this.dataShow = new DataShow(this.getUi().getCenterWrapperPanel());
        this.gradesPanelMVC();
        //
        this.ui.getUiSearchComponent().setController(this);
        //
        this.ui.getUiExtraComponent().setSelectionChangedListener(this);

        //
        RequestListener requestListener = new RequestListener(this);
        MarvelRequest.getInstance().addRequestListener(requestListener);


        this.initFavoriteButton();
        this.initReadButton();
        this.initOwnButton();

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
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    System.exit(0);
                }
            }
        });

        UserAuthenticationModel.getInstance().addObserver(this.ui.getUiExtraComponent());
        // this.emitSearchComicById("61522");


        FavoriteView.setController(this);
        CollectionsView.setController(this);

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
                    if (favRow != null) {
                        ElementsAssociation.updateFavorite(favRow.getUid(), favRow.getUserId(), false);
                        favoriteButtonView.setState(false);
                        this.ui.getUiExtraComponent().getRightWrapperPanel().repaint();
                    }
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
                    if (elementAssociationRow != null) {
                        ElementsAssociation.updateRead(elementAssociationRow.getUid(), elementAssociationRow.getUserId(), false);
                        readButtonView.setState(false);
                        this.ui.getUiExtraComponent().getRightWrapperPanel().repaint();
                    }
                } else {
                    int userId = user.getId();
                    ElementsAssociation.updateReadCreateAsNeeded(readButtonView.getId(),
                            readButtonView.getElementName(), userId, true, readButtonView.getType());
                    readButtonView.setState(true);
                    this.ui.getUiExtraComponent().getRightWrapperPanel().repaint();
                }
            }
        });
    }


    private void initOwnButton() {

        final OwnButtonView ownButtonView = this.dataShow.getBtnPane().getOwnedButtonView();

        UserAuthenticationModel.getInstance().addObserver(ownButtonView);

        ownButtonView.addActionListener(e -> {
            User user = UserAuthenticationModel.getUser();
            if (user != null) {
                if (ownButtonView.isState()) {
                    ElementAssociationRow elementAssociationRow = ElementsAssociation
                            .findElement(user.getId(),
                                    ownButtonView.getId(), ownButtonView.getType());
                    if (elementAssociationRow != null) {
                        ElementsAssociation.updateOwned(elementAssociationRow.getUid(), elementAssociationRow.getUserId(), false);
                        ownButtonView.setState(false);
                        this.ui.getUiExtraComponent().getRightWrapperPanel().repaint();
                    }
                } else {
                    int userId = user.getId();
                    ElementsAssociation.updateOwnedCreateAsNeeded(ownButtonView.getId(),
                            ownButtonView.getElementName(), userId, true);

                    ownButtonView.setState(true);


                    this.ui.getUiExtraComponent().getRightWrapperPanel().repaint();
                }
            }
        });
    }


    void init() {
        AutoCompletion autoCompletion = new AutoCompletion(this.ui, this.ui.getUiSearchComponent().getSearchTextField(), this);
        autoCompletion.initComponent();
        this.ui.getUiSearchComponent().setAutoCompletion(autoCompletion);
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
            final Iterator<String> urls = AppConfig.getInstance().getCacheUrls().urls();
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
                fetchCharacterById.start();
            }
            if (this.ui.getUiSearchComponent().getComicsRadioButton().isSelected()) {
                Thread fetchCharacterById = new FetchData(this, "comics", "titleStartsWith=" + text.toLowerCase() +
                        "&limit=50", FetchData.ComicsType.COMICS);
                fetchCharacterById.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emitSearchCharacterById(int id) {
        Thread fetchCharacterById = new FetchData(this, "characters/" + id, null, FetchData.CharactersType.CHARACTER_BY_ID);
        fetchCharacterById.start();
    }

    public void emitSearchComicById(int id) {
        Thread fetchComic = new FetchData(this, "comics/" + id, null, FetchData.ComicsType.COMIC_BY_ID);
        fetchComic.start();
    }

    public UI getUi() {
        return ui;
    }

    public void gotoSearchView() {
        this.ui.getTabbedPane().setSelectedIndex(0);
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
        customDrawCharacter(character);
    }

    @Override
    public void onFetchingComics(String url) {
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

        if (comic.getSeries() != null) {
            // since we have the comic now we are going to fetch all comics in same series by using series id returned
            Thread fetchComicsInSameSeries = new FetchData(this, "series/" + comic.getSeries().getResourceURI().substring(43) + "/comics", null, FetchData.ComicsType.COMICS_IN_SAME_SERIES);
            fetchComicsInSameSeries.start();
        }
    }

    @Override
    public void onFetchedComicsInSameSeries(Comic[] comics) {
        this.ui.getUiExtraComponent().setResultsComics(comics);
    }

    @Override
    public void onFetchingCharacters(String url) {
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
            fetchCharactersInSameComic.start();
        }
    }

    @Override
    public void onFetchedCharactersInSameComic(Character[] characters) {
        EventQueue.invokeLater(() -> this.ui.getUiExtraComponent().setResultsCharacters(characters));
    }

}


