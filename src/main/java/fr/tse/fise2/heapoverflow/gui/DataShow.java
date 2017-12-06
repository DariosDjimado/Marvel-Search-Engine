package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.main.AppConfig;
import fr.tse.fise2.heapoverflow.main.Controller;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.*;
import fr.tse.fise2.heapoverflow.marvelapi.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.List;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.*;

/**
 * Class used to manage à {@link JPanel} used to show detailed datas on characters and comics. Can display :
 * <ul>
 *     <li>Comics</li>
 *     <li>Characters</li>
 * </ul>
 *
 * @author Théo Basty
 * @version 2.0
 * TODO move all subrequests to Thread
 * TODO reduce repetition in code
 * TODO Review JavaDocs annotations
 *
 */
public class DataShow {
    //region Attributes
//    Controller controllerLink;


    /**
     * The panel used to show the data (Character or Comic)
     */
    private JPanel panel;

    /**
     * The thumbnail od the element displayed
     */
    private ShowThumbnail thumbnail;

    /**
     * The title of the Panel
     */
    private JLabel head;

    /**
     * The panel containing all the details about the element
     */
    private JPanel detail;

    /**
     * The subpanel of {@link DataShow#detail} containing the details about the element
     */
    private JPanel detailPane;

    /**
     * The subpanel of {@link DataShow#detail} containing the reference of the comic. Hidden when a character is displayed
     */
    private JPanel referencesPane;

    /**
     * The component used to display the description
     */
    private JEditorPane description;

    /**
     * The component containing all the tabs displying different lists (depend on the type of element displayed)
     */
    private JTabbedPane tabs;

    /**
     * A map referring to all tabs displayed in the panel
     */
    private Map<String, JList> tabsJLists;
    //endregion

    //region Constructors

    /**
     * Constructor, initialize every subpanel which will contain the informations on the element displayed.
     *
     * @param panel_ The panel on which the element will be displayed
     */
    public DataShow(JPanel panel_) {
//        this.controllerLink = controller;
        this.panel = panel_;
        panel.setLayout(new BorderLayout());

        JPanel leftPane = new JPanel();
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
        leftPane.setBorder(new EmptyBorder(10, 10, 0, 5));
        panel.add(leftPane, BorderLayout.WEST);

        thumbnail = new ShowThumbnail(new BufferedImage(168, 252, BufferedImage.TYPE_INT_ARGB));
        thumbnail.setPreferredSize(new Dimension(200, 274));
        leftPane.add(thumbnail);

        referencesPane = new JPanel();
        referencesPane.setLayout(new GridLayout(5, 1));
        referencesPane.setBackground(Color.lightGray);
        referencesPane.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(2, 2, 2, 2), BorderFactory.createLineBorder(Color.darkGray)));
        leftPane.add(referencesPane);

        head = new JLabel();
        panel.add(head, BorderLayout.NORTH);
        head.setBorder(new EmptyBorder(10, 10, 0, 10));
        head.setFont(Fonts.title1);

        detail = new JPanel();
        detail.setLayout(new BoxLayout(detail, BoxLayout.PAGE_AXIS));
        detail.setBorder(new EmptyBorder(10, 5, 0, 10));
        JPanel detailWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        detailWrapper.add(detail);
        panel.add(detailWrapper, BorderLayout.CENTER);

        description = new JEditorPane();
        description.setEditable(false);
        JScrollPane descriptionScroll = new JScrollPane(description, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        descriptionScroll.setPreferredSize(new Dimension(200, 100));
        detail.add(new JScrollPane(descriptionScroll));

        detailPane = new JPanel();
        detailPane.setLayout(new BoxLayout(detailPane, BoxLayout.PAGE_AXIS));
        detailPane.setBorder(new EmptyBorder(5, 0, 0, 0));
        detail.add(detailPane);

        tabs = new JTabbedPane();
        tabsJLists = new HashMap<>();
        tabs.setPreferredSize(new Dimension(panel.getWidth(), 120));
        panel.add(tabs, BorderLayout.SOUTH);

        panel.setMinimumSize(panel.getLayout().minimumLayoutSize(panel));
        panel.setVisible(true);
    }
    //endregion

    /**
     * Function to draw informations for comics
     *
     * @param comic The comic to display
     */
    public void DrawComic(Comic comic) {
        MarvelRequest request = new MarvelRequest();

        //region title display
        head.setText(comic.getTitle());
        //endregion
        //region detail display

        //region Description
        description.setText(comic.getDescription());
        //endregion
        //region references
        referencesPane.setVisible(true);
        LinkedHashMap<String, String> references = new LinkedHashMap<>();
        references.put("ISBN : "        , comic.getIsbn());
        references.put("UPC : "         , comic.getUpc());
        references.put("Diamond Code : ", comic.getDiamondCode());
        references.put("EAN : "         , comic.getEan());
        references.put("ISSN : "        , comic.getIssn());

        fillPaneWithLabels(referencesPane, references, Fonts.boldRef, Fonts.ref);
        //endregion
        //region detail content
        LinkedHashMap<String, String> details = new LinkedHashMap<>();
        for(ComicDate date:comic.getDates()) {
            if (date.getType().equals("onsaleDate")) {
                details.put("Release Date : ", date.getDate().substring(0, 10));
            }
        }
        details.put("Format : ", comic.getFormat());
        details.put("Serie : ", comic.getSeries().getName());
        if(comic.getIssueNumber() > 0) {
            details.put("Issue Number : ", comic.getIssueNumber().toString());
        }
        details.put("Prices : ", "");
        for (ComicPrice price : comic.getPrices()) {
            details.put("", "- " + price.getType() + " : " + price.getPrice());
        }
        details.put("Page Count : ", Integer.valueOf(comic.getPageCount()).toString());

        fillPaneWithLabels(detailPane, details);
        //endregion
        detail.revalidate();
        //endregion
        //region Tabs display
        //region Character
        DefaultListModel<MarvelListElement> characterListModel = new DefaultListModel<>();
        JList<MarvelListElement> characters = new JList<>(characterListModel);
        tabsJLists.put("Character", characters);

        try {
            CharacterDataWrapper responseObj;
            int reqCount = 0;
            Set<Character> fetched = new TreeSet<>();
            do {
                String response = request.getData(comic.getCharacters().getCollectionURI().substring(36) + "?limit=100&offset=" + 100*reqCount);
                responseObj = deserializeCharacters(response);
                fetched.addAll(Arrays.asList(responseObj.getData().getResults()));
                reqCount++;
            } while (responseObj.getData().getOffset()+responseObj.getData().getCount() < responseObj.getData().getTotal());

            for (Character character : fetched) {
                characterListModel.addElement(new MarvelListElement(character.getName(), character.getResourceURI(), MarvelType.Character));
            }
            tabs.addTab("Character", new JScrollPane(characters));
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        //endregion
        //region Creators
        DefaultListModel<MarvelListElement> creatorListModel = new DefaultListModel<>();
        JList<MarvelListElement> creators = new JList<>(creatorListModel);
        tabsJLists.put("Creators", creators);

        try {
            CreatorDataWrapper responseObj;
            int reqCount = 0;
            Set<Creator> fetched = new TreeSet<>();
            do {
                String response = request.getData(comic.getCreators().getCollectionURI().substring(36) + "?limit=100&offset=" + 100*reqCount);
                responseObj = deserializeCreators(response);
                fetched.addAll(Arrays.asList(responseObj.getData().getResults()));
                reqCount++;
            } while (responseObj.getData().getOffset()+responseObj.getData().getCount() < responseObj.getData().getTotal());

            for (Creator oneCreator : fetched) {
                creatorListModel.addElement(new MarvelListElement(oneCreator.getFullName(), oneCreator.getResourceURI(), MarvelType.Creator));
            }
            tabs.addTab("Creators", new JScrollPane(creators));
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }

        //endregion
        //region variants
        DefaultListModel<MarvelListElement> variantsListModel = new DefaultListModel<>();
        JList<MarvelListElement> variants = new JList<>(variantsListModel);
        tabsJLists.put("Variants", variants);
        if (comic.getVariants().length > 0) {
            for (ComicSummary comicVariant : comic.getVariants()) {
                variantsListModel.addElement(new MarvelListElement(comicVariant.getName(), comic.getRessourceURI(), MarvelType.Comic));
            }
            tabs.addTab("Variants", new JScrollPane(variants));
        }
        //endregion
        //region collections
//        if(true) {
        DefaultListModel<MarvelListElement> CollectionsListModel = new DefaultListModel<>();
        JList<MarvelListElement> collections = new JList<>(CollectionsListModel);
        tabsJLists.put("Collections", collections);

        if (comic.getCollections().length > 0) {
            for (ComicSummary comicCollection : comic.getCollections()) {
                CollectionsListModel.addElement(new MarvelListElement(comicCollection.getName(), comic.getRessourceURI(), MarvelType.Comic));
            }
            tabs.addTab("Collections", new JScrollPane(collections));
        }
        //endregion
        //region collected issues
//        if(true) {
        DefaultListModel<MarvelListElement> CollectedListModel = new DefaultListModel<>();
        JList<MarvelListElement> collected = new JList<>(CollectedListModel);
        tabsJLists.put("Collected Issues", collected);

        if (comic.getCollectedIssues().length > 0) {
            for (ComicSummary comicCollected : comic.getCollectedIssues()) {
                CollectedListModel.addElement(new MarvelListElement(comicCollected.getName(), comic.getRessourceURI(), MarvelType.Comic));
            }
            tabs.addTab("Collected Issues", new JScrollPane(collected));
        }
        //endregion
        //region stories
        DefaultListModel<MarvelListElement> storiesListModel = new DefaultListModel<>();
        JList<MarvelListElement> stories = new JList<>(storiesListModel);
        tabsJLists.put("Stories", stories);

        try {
            ComicDataWrapper responseObj;
            int reqCount = 0;
            Set<Comic> fetched = new TreeSet<>();
            do {
                String response = request.getData(comic.getStories().getCollectionURI().substring(36) + "?limit=100&offset=" + 100*reqCount);
                responseObj = deserializeComics(response);
                fetched.addAll(Arrays.asList(responseObj.getData().getResults()));
                reqCount++;
            } while (responseObj.getData().getOffset()+responseObj.getData().getCount() < responseObj.getData().getTotal());

            for (Comic oneComic : fetched) {
                storiesListModel.addElement(new MarvelListElement(oneComic.getTitle(), oneComic.getRessourceURI(), MarvelType.Story));
            }
            tabs.addTab("Stories", new JScrollPane(stories));
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        //endregion
        //region events
        DefaultListModel<MarvelListElement> eventsListModel = new DefaultListModel<>();
        JList<MarvelListElement> events = new JList<>(eventsListModel);
        tabsJLists.put("Events", events);
        try {
            ComicDataWrapper responseObj;
            int reqCount = 0;
            Set<Comic> fetched = new TreeSet<>();
            do {
                String response = request.getData(comic.getEvents().getCollectionURI().substring(36) + "?limit=100&offset=" + 100*reqCount);
                responseObj = deserializeComics(response);
                fetched.addAll(Arrays.asList(responseObj.getData().getResults()));
                reqCount++;
            } while (responseObj.getData().getOffset()+responseObj.getData().getCount() < responseObj.getData().getTotal());

            for (Comic oneComic : fetched) {
                eventsListModel.addElement(new MarvelListElement(oneComic.getTitle(), oneComic.getRessourceURI(), MarvelType.Story));
            }
            tabs.addTab("Events", new JScrollPane(events));
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        //endregion
        tabs.revalidate();
        //endregion
        //region thumbnail display
        setThumbnail(comic.getThumbnail());
        //endregion

        panel.repaint();

    }

    /**
     * Function to draw informations for characters
     * @param character The Character to display
     *
     */
    public void DrawCharacter(final Character character) {

        //region title display
        head.setText(character.getName());
        //endregion
        //region detail display
        referencesPane.setVisible(false);
        LinkedHashMap<String, String> details = new LinkedHashMap<>();
        details.put("Appears in : ", "");
        details.put(" ", "- " + Integer.valueOf(character.getSeries().getAvailable()).toString() + " Series");
        details.put("", " - " + Integer.valueOf(character.getComics().getAvailable()).toString() + " Comics");
        details.put("Last Modification : ", character.getModified().substring(0, 10));

        fillPaneWithLabels(detailPane, details);
        detail.revalidate();
        //endregion
        //region Tabs display
        tabsJLists.clear();
        tabs.removeAll();
        //region Description
        description.setText(character.getDescription());
        tabs.addTab("Description", new JScrollPane(description));
        //endregion
        //region Series
        MarvelRequest request = new MarvelRequest();

        DefaultListModel<MarvelListElement> seriesListModel = new DefaultListModel<>();
        seriesListModel.addElement(new MarvelListElement("Loading...", null, null));
        JList<MarvelListElement> series = new JList<>(seriesListModel);
        tabsJLists.put("Series", series);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ComicDataWrapper responseObj;
                    int reqCount = 0;
                    Set<Comic> fetched = new TreeSet<>();
                    do {
                        String response = request.getData(character.getSeries().getCollectionURI().substring(36) + "?limit=100&offset=" + 100 * reqCount);
                        responseObj = deserializeComics(response);
                        fetched.addAll(Arrays.asList(responseObj.getData().getResults()));
                        reqCount++;
                    }
                    while (responseObj.getData().getOffset() + responseObj.getData().getCount() < responseObj.getData().getTotal());

                    DefaultListModel<MarvelListElement> comicListModel = new DefaultListModel<>();
                    for (Comic comic : fetched) {
                        comicListModel.addElement(new MarvelListElement(comic.getTitle(), comic.getRessourceURI(), MarvelType.Serie));
                    }
                    series.setModel(comicListModel);
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        });
        tabs.addTab("Series", new JScrollPane(series));
        //endregion
        //region Comics
        DefaultListModel<MarvelListElement> comicListModel = new DefaultListModel<>();
        comicListModel.addElement(new MarvelListElement("Loading...", null, null));
        JList<MarvelListElement> comics = new JList<>(comicListModel);
        tabsJLists.put("Comics", comics);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ComicDataWrapper responseObj;
                    int reqCount = 0;
                    Set<Comic> fetched = new TreeSet<>();
                    do {
                        String response = request.getData(character.getComics().getCollectionURI().substring(36) + "?limit=100&offset=" + 100 * reqCount);
                        responseObj = deserializeComics(response);
                        fetched.addAll(Arrays.asList(responseObj.getData().getResults()));
                        reqCount++;
                    }
                    while (responseObj.getData().getOffset() + responseObj.getData().getCount() < responseObj.getData().getTotal());

                    comicListModel.clear();
                    for (Comic comic : fetched) {
                        comicListModel.addElement(new MarvelListElement(comic.getTitle(), comic.getRessourceURI(), MarvelType.Comic));
                    }
                } catch (SocketTimeoutException ex) {
                    comicListModel.clear();
                    comicListModel.addElement(new MarvelListElement("Request Timeout", null, null));
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        });
        tabs.addTab("Comics", new JScrollPane(comics));
        //endregion
        //region Stories
        DefaultListModel<MarvelListElement> storiesListModel = new DefaultListModel<>();
        storiesListModel.addElement(new MarvelListElement("Loading...", null, null));
        JList<MarvelListElement> stories = new JList<>(storiesListModel);
        tabsJLists.put("Stories", stories);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ComicDataWrapper responseObj;
                    int reqCount = 0;
                    Set<Comic> fetched = new TreeSet<>();
                    do {
                        String response = request.getData(character.getStories().getCollectionURI().substring(36) + "?limit=100&offset=" + 100 * reqCount);
                        responseObj = deserializeComics(response);
                        fetched.addAll(Arrays.asList(responseObj.getData().getResults()));
                        reqCount++;
                    }
                    while (responseObj.getData().getOffset() + responseObj.getData().getCount() < responseObj.getData().getTotal());

                    storiesListModel.clear();
                    for (Comic story : fetched) {
                        storiesListModel.addElement(new MarvelListElement(story.getTitle(), story.getRessourceURI(), MarvelType.Story));
                    }
                } catch (SocketTimeoutException ex) {
                    comicListModel.clear();
                    comicListModel.addElement(new MarvelListElement("Request Timeout", null, null));
                } catch (Exception ex) {
                    System.out.println(ex);
                    ex.printStackTrace();
                }
            }
        });
        tabs.addTab("Stories", new JScrollPane(stories));
        //endregion
        //region Events
        DefaultListModel<MarvelListElement> eventsListModel = new DefaultListModel<>();
        eventsListModel.addElement(new MarvelListElement("Loading...", null, null));
        JList<MarvelListElement> events = new JList<>(eventsListModel);
        tabsJLists.put("Events", events);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ComicDataWrapper responseObj;
                    int reqCount = 0;
                    Set<Comic> fetched = new TreeSet<>();
                    do {
                        String response = request.getData(character.getEvents().getCollectionURI().substring(36) + "?limit=100&offset=" + 100 * reqCount);
                        responseObj = deserializeComics(response);
                        fetched.addAll(Arrays.asList(responseObj.getData().getResults()));
                        reqCount++;
                    }
                    while (responseObj.getData().getOffset() + responseObj.getData().getCount() < responseObj.getData().getTotal());

                    eventsListModel.clear();
                    for (Comic event : fetched) {
                        eventsListModel.addElement(new MarvelListElement(event.getTitle(), event.getRessourceURI(), MarvelType.Story));
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        });

        tabs.addTab("Events", new JScrollPane(events));
        //endregion
        tabs.revalidate();
        //endregion
        //region thumbnail display
        setThumbnail(character.getThumbnail());
        //endregion

        panel.repaint();
    }

    /**
     * Method to fill a JPanel with lines containin couples content title, content
     * @param pane The {@link JPanel} to fill
     * @param m The {@link Map} containing the couples to add
     * @param titleFont font for the title of the element
     * @param contentFont font for the conten
     */
    private static void fillPaneWithLabels(JPanel pane, Map<String, String> m, Font titleFont, Font contentFont) {
        pane.removeAll();
        for (String detailLine : m.keySet()) {
            JLabel title = new JLabel(detailLine);
            title.setFont(titleFont);
            JLabel content = new JLabel(m.get(detailLine));
            content.setFont(contentFont);
            JPanel refLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            refLine.setOpaque(false);
            refLine.add(title);
            refLine.add(content);
            pane.add(refLine);
        }
    }

    /**
     * Method to fill a JPanel with lines containin couples content title, content
     *
     * @param pane The {@link JPanel} to fill
     * @param m    The {@link Map} containing the couples to add
     */
    private static void fillPaneWithLabels(JPanel pane, Map<String, String> m) {
        fillPaneWithLabels(pane, m, Fonts.boldContent, Fonts.content);
    }

    /**
     * Method to change the thumbnail of the panel
     *
     * @param ThumbPartialUrl the partial URI from the API
     */
    public void setThumbnail(Image ThumbPartialUrl) {
        try {
            thumbnail.setImage_(MarvelRequest.getImage(ThumbPartialUrl, UrlBuilder.ImageVariant.PORTRAIT_FANTASTIC, AppConfig.tmpDir));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Method to change the thumbnail of panel
     * @param thumbnail the {@link BufferedImage} of the image
     */
    public void setThumbnail(BufferedImage thumbnail) {
        this.thumbnail.setImage_(thumbnail);
    }
}

/**
 * Class to adapt marvel API elements for JList display and use
 * TODO: Make it public
 * @author Théo Basty
 */
class MarvelListElement{
    String dispName;
    String shortURI;
    MarvelType type;

    public MarvelListElement(String dispName, String shortURI, MarvelType type) {
        this.dispName = dispName;
        if(shortURI != null && shortURI.substring(0, 4).equals("http")){
            this.shortURI = shortURI.substring(36);
        }
        else {
            this.shortURI = shortURI;
        }
        this.type = type;
    }

    public String getDispName() {
        return dispName;
    }

    public void setDispName(String dispName) {
        this.dispName = dispName;
    }

    public String getShortURI() {
        return shortURI;
    }

    public void setShortURI(String shortURI) {
        this.shortURI = shortURI;
    }

    public MarvelType getType() {
        return type;
    }

    public void setType(MarvelType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return dispName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarvelListElement that = (MarvelListElement) o;

        if (dispName != null ? !dispName.equals(that.dispName) : that.dispName != null) return false;
        if (shortURI != null ? !shortURI.equals(that.shortURI) : that.shortURI != null) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = dispName != null ? dispName.hashCode() : 0;
        result = 31 * result + (shortURI != null ? shortURI.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
enum MarvelType{
    Character, Comic, Serie, Creator, Story
}