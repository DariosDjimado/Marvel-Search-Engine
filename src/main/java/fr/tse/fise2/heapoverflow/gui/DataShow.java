package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.database.FirstAppearanceRow;
import fr.tse.fise2.heapoverflow.database.FirstAppearanceTable;
import fr.tse.fise2.heapoverflow.database.WikipediaUrlsTable;
import fr.tse.fise2.heapoverflow.main.AppConfig;
import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.*;
import fr.tse.fise2.heapoverflow.marvelapi.Event;
import fr.tse.fise2.heapoverflow.marvelapi.Image;
import fr.tse.fise2.heapoverflow.tasks.FirstAppearance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class used to manage à {@link JPanel} used to show detailed datas on characters and comics. Can display :
 * <ul>
 *     <li>Comics</li>
 *     <li>Characters</li>
 * </ul>
 *
 * @author Théo Basty
 * @version 2.0
 *
 */
public class DataShow {
    //region Attributes
//    Controller controllerLink;
    public static final Logger LOGGER = LoggerFactory.getLogger(DataShow.class);

    /**
     * The object managing background requests
     */
    private InfoSubRequestsThread isrt;

    /**
     * The hashcode of the displayed element, used to manage multithreaded request
     */
    private int elementToken;

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

    private ReactivePanel btnPane;

    //endregion

    //region Constructors

    /**
     * Constructor, initialize every subpanel which will contain the informations on the element displayed.
     *
     * @param panel_ The panel on which the element will be displayed
     */
    public DataShow(JPanel panel_) {
//        this.controllerLink = controller;
        isrt = new InfoSubRequestsThread(this);

        this.elementToken = 0;
        this.panel = panel_;
        panel.setLayout(new BorderLayout());

        JPanel leftPane = new JPanel();
        leftPane.setOpaque(false);
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
        leftPane.setBorder(new EmptyBorder(10, 10, 0, 5));
        panel.add(leftPane, BorderLayout.WEST);

        thumbnail = new ShowThumbnail(new BufferedImage(168, 252, BufferedImage.TYPE_INT_ARGB));
        thumbnail.setPreferredSize(new Dimension(200, 274));
        leftPane.add(thumbnail);

        referencesPane = new JPanel();
        referencesPane.setOpaque(false);
        referencesPane.setLayout(new GridLayout(5, 1));
        referencesPane.setBackground(Color.lightGray);
        referencesPane.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(2, 2, 2, 2), BorderFactory.createLineBorder(Color.darkGray)));
        leftPane.add(referencesPane);

        head = new JLabel();
        panel.add(head, BorderLayout.NORTH);
        head.setBorder(new EmptyBorder(10, 10, 0, 10));
        head.setFont(Fonts.title1);

        detail = new JPanel();
        detail.setOpaque(false);
        detail.setLayout(new BorderLayout());
        detail.setBorder(new EmptyBorder(10, 5, 0, 10));
        JPanel detailWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        detailWrapper.setOpaque(false);
        detailWrapper.add(detail);
        panel.add(detail, BorderLayout.CENTER);

        description = new JEditorPane();
        description.setEditable(false);
        JScrollPane descriptionScroll = new CustomScrollPane(description, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        descriptionScroll.setPreferredSize(new Dimension(200, 100));
        detail.add(new CustomScrollPane(descriptionScroll), BorderLayout.NORTH);

        detailPane = new JPanel();
        detailPane.setOpaque(false);
        detailPane.setLayout(new BoxLayout(detailPane, BoxLayout.PAGE_AXIS));
        detailPane.setBorder(new EmptyBorder(5, 0, 0, 0));
        JPanel detailPaneWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        detailPaneWrapper.setOpaque(false);
        detailPaneWrapper.add(detailPane);
        detail.add(detailPaneWrapper, BorderLayout.CENTER);

        btnPane = new ReactivePanel();
        detail.add(btnPane, BorderLayout.SOUTH);
        //btnPane.setVisible(false);

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
    synchronized public void DrawComic(Comic comic) {
        //Clearing jobs for the previous element
        isrt.clearJobsFor(elementToken);
        //Getting token for the next element
        elementToken = comic.hashCode();
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
        tabs.removeAll();
        //region Character
        DefaultListModel<MarvelListElement> characterListModel = new DefaultListModel<>();
        characterListModel.addElement(new MarvelListElement("Loading...", null, null));
        JList<MarvelListElement> characters = new JList<>(characterListModel);
        tabsJLists.put("Characters", characters);
        tabs.addTab("Characters", new CustomScrollPane(characters));
        isrt.addJob("Character", "Characters", comic.getCharacters().getCollectionURI().substring(36), elementToken);
        //endregion
        //region Creators
        DefaultListModel<MarvelListElement> creatorListModel = new DefaultListModel<>();
        creatorListModel.addElement(new MarvelListElement("Loading...", null, null));
        JList<MarvelListElement> creators = new JList<>(creatorListModel);
        tabsJLists.put("Creators", creators);
        tabs.addTab("Creators", new CustomScrollPane(creators));
        isrt.addJob("Creator", "Creators", comic.getCreators().getCollectionURI().substring(36), elementToken);

        //endregion
        //region variants
        DefaultListModel<MarvelListElement> variantsListModel = new DefaultListModel<>();
        JList<MarvelListElement> variants = new JList<>(variantsListModel);
        tabsJLists.put("Variants", variants);
        if (comic.getVariants().length > 0) {
            for (ComicSummary comicVariant : comic.getVariants()) {
                variantsListModel.addElement(new MarvelListElement(comicVariant.getName(), comic.getRessourceURI(), MarvelType.Comic));
            }
            tabs.addTab("Variants", new CustomScrollPane(variants));
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
            tabs.addTab("Collections", new CustomScrollPane(collections));
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
            tabs.addTab("Collected Issues", new CustomScrollPane(collected));
        }
        //endregion
        //region stories
        DefaultListModel<MarvelListElement> storiesListModel = new DefaultListModel<>();
        storiesListModel.addElement(new MarvelListElement("Loading...", null, null));
        JList<MarvelListElement> stories = new JList<>(storiesListModel);
        tabsJLists.put("Stories", stories);
        tabs.addTab("Stories", new CustomScrollPane(stories));
        isrt.addJob("Story", "Stories", comic.getStories().getCollectionURI().substring(36), elementToken);
        //endregion
        //region events
        DefaultListModel<MarvelListElement> eventsListModel = new DefaultListModel<>();
        eventsListModel.addElement(new MarvelListElement("Loading...", null, null));
        JList<MarvelListElement> events = new JList<>(eventsListModel);
        tabsJLists.put("Events", events);
        tabs.addTab("Events", new CustomScrollPane(events));
        isrt.addJob("Event", "Events", comic.getEvents().getCollectionURI().substring(36), elementToken);
        // external links
        List<LinkView>  linkViews = Arrays.stream(comic.getUrls()).map((Url url)-> new LinkView(url.getType(), url.getUrl())).collect(Collectors.toList());
        tabs.add("External Links", new CustomScrollPane(new ExternalLinksView(linkViews)));
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
    synchronized public void DrawCharacter(final Character character) {
        //Clearing jobs for the previous element
        isrt.clearJobsFor(elementToken);
        //Getting token for the next element
        elementToken = character.hashCode();

        //region title display
        head.setText(character.getName());
        //endregion
        //region detail display
        referencesPane.setVisible(false);
        btnPane.setVisible(false);
        LinkedHashMap<String, String> details = new LinkedHashMap<>();
        details.put("Appears in : ", "");
        details.put(" ", "- " + Integer.valueOf(character.getSeries().getAvailable()).toString() + " Serie");
        details.put("", " - " + Integer.valueOf(character.getComics().getAvailable()).toString() + " Comics");
        details.put("Last Modification : ", character.getModified().substring(0, 10));

        FirstAppearanceRow  firstAppearanceRow = FirstAppearanceTable.getFirstAppearanceRow(character.getName().toLowerCase());
        if(firstAppearanceRow != null) {
            details.put(
                    "First appearance : ",
                    firstAppearanceRow.getComic() +", "+ firstAppearanceRow.getDate());
        }

        fillPaneWithLabels(detailPane, details);
        detail.revalidate();
        //endregion
        //region Tabs display
        tabsJLists.clear();
        tabs.removeAll();
        //region Description
        description.setText(character.getDescription());
        //endregion
        //region Serie
        MarvelRequest request = MarvelRequest.getInstance();

        DefaultListModel<MarvelListElement> seriesListModel = new DefaultListModel<>();
        seriesListModel.addElement(new MarvelListElement("Loading...", null, null));
        JList<MarvelListElement> series = new JList<>(seriesListModel);
        tabsJLists.put("Series", series);
        tabs.addTab("Series", new CustomScrollPane(series));
        isrt.addJob("Serie", "Series", character.getSeries().getCollectionURI().substring(36), elementToken);
        //endregion
        //region Comics
        DefaultListModel<MarvelListElement> comicListModel = new DefaultListModel<>();
        comicListModel.addElement(new MarvelListElement("Loading...", null, null));
        JList<MarvelListElement> comics = new JList<>(comicListModel);
        tabsJLists.put("Comics", comics);
        tabs.addTab("Comics", new CustomScrollPane(comics));
        isrt.addJob("Comic", "Comics", character.getComics().getCollectionURI().substring(36), elementToken);
        //endregion
        //region Stories
        DefaultListModel<MarvelListElement> storiesListModel = new DefaultListModel<>();
        storiesListModel.addElement(new MarvelListElement("Loading...", null, null));
        JList<MarvelListElement> stories = new JList<>(storiesListModel);
        tabsJLists.put("Stories", stories);
        tabs.addTab("Stories", new CustomScrollPane(stories));
        isrt.addJob("Story", "Stories", character.getStories().getCollectionURI().substring(36), elementToken);
        //endregion
        //region Events
        DefaultListModel<MarvelListElement> eventsListModel = new DefaultListModel<>();
        eventsListModel.addElement(new MarvelListElement("Loading...", null, null));
        JList<MarvelListElement> events = new JList<>(eventsListModel);
        tabsJLists.put("Events", events);
        tabs.addTab("Events", new CustomScrollPane(events));
        isrt.addJob("Event", "Events", character.getEvents().getCollectionURI().substring(36), elementToken);
        // external links
        List<LinkView>  linkViews = Arrays.stream(character.getUrls()).map((Url url)-> new LinkView(url.getType(), url.getUrl())).collect(Collectors.toList());
        String url = WikipediaUrlsTable.findByLabel(character.getName());
        if(!url.isEmpty()){
            linkViews.add(0,new LinkView("wikipedia",url));
        }
        tabs.add("External Links", new CustomScrollPane(new ExternalLinksView(linkViews)));
        //endregion
        tabs.revalidate();
        //endregion
        //region thumbnail display
        setThumbnail(character.getThumbnail());
        //endregion

        panel.repaint();
    }

    /**
     * Method to fill a JPanel with lines containing couples content title, content
     * @param pane The {@link JPanel} to fill
     * @param m The {@link Map} containing the couples to add
     * @param titleFont font for the title of the element
     * @param contentFont font for the content
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
            thumbnail.setImage_(MarvelRequest.getImage(ThumbPartialUrl, UrlBuilder.ImageVariant.PORTRAIT_FANTASTIC, AppConfig.getInstance().getTmpDir()));
        } catch (Exception e) {
            AppErrorHandler.onError(e);
            if(LOGGER.isErrorEnabled()){
                LOGGER.error(e.getMessage(),e);
            }
        }
    }

    public ReactivePanel getBtnPane() {
        return btnPane;
    }

    /**
     * Method to change the thumbnail of panel
     * @param thumbnail the {@link BufferedImage} of the image
     */
            public void setThumbnail(BufferedImage thumbnail) {
                this.thumbnail.setImage_(thumbnail);
            }


            /**
             * Method called by thread requested tabs content in parallel, to fill those tabs
             * @param elements the set of elements to add
             * @param elementType the type of elements to add
             * @param tab the tab to fill
             * @param token the token to check if the data correspond to the element shown
             */
            synchronized public void updateList(Set elements, String elementType, String tab, int token){
                if(token == elementToken){
                    ((DefaultListModel<MarvelListElement>)tabsJLists.get(tab).getModel()).clear();
                    switch(elementType){
                        case "Comic":
                            for (Comic oneComic : (TreeSet<Comic>)elements) {
                                ((DefaultListModel<MarvelListElement>)tabsJLists.get(tab).getModel()).addElement(new MarvelListElement(oneComic.getTitle(), oneComic.getRessourceURI(), MarvelType.Comic));
                            }
                    break;
                case "Character":
                    for (Character oneCharacter : (TreeSet<Character>)elements) {
                        ((DefaultListModel<MarvelListElement>)tabsJLists.get(tab).getModel()).addElement(new MarvelListElement(oneCharacter.getName(), oneCharacter.getResourceURI(), MarvelType.Character));
                    }
                    break;
                case "Creator":
                    for (Creator oneCreator : (TreeSet<Creator>)elements) {
                        ((DefaultListModel<MarvelListElement>)tabsJLists.get(tab).getModel()).addElement(new MarvelListElement(oneCreator.getFullName(), oneCreator.getResourceURI(), MarvelType.Creator));
                    }
                    break;
                case "Story":
                    for (Story oneStory : (TreeSet<Story>)elements) {
                        ((DefaultListModel<MarvelListElement>)tabsJLists.get(tab).getModel()).addElement(new MarvelListElement(oneStory.getTitle(), oneStory.getResourceURI(), MarvelType.Story));
                    }
                    break;
                case "Event":
                    for (Event oneEvent : (TreeSet<Event>)elements) {
                        ((DefaultListModel<MarvelListElement>)tabsJLists.get(tab).getModel()).addElement(new MarvelListElement(oneEvent.getTitle(), oneEvent.getResourceURI(), MarvelType.Event));
                    }
                    break;
                case "Serie":
                    for (Serie oneSerie : (TreeSet<Serie>)elements) {
                        ((DefaultListModel<MarvelListElement>)tabsJLists.get(tab).getModel()).addElement(new MarvelListElement(oneSerie.getTitle(), oneSerie.getResourceURI(), MarvelType.Serie));
                    }
                    break;
                case "TimeOut":
                    ((DefaultListModel<MarvelListElement>)tabsJLists.get(tab).getModel()).addElement(new MarvelListElement("Request Timeout", null, MarvelType.Error));
                default:
                    break;
            }
            if(elements.isEmpty() & !elementType.equals("TimeOut")){
                ((DefaultListModel<MarvelListElement>)tabsJLists.get(tab).getModel()).addElement(new MarvelListElement("<Empty>", null, MarvelType.Void));
            }

            panel.repaint();
        }
    }
}

enum MarvelType{
    Character, Comic, Serie, Creator, Story, Event, Error, Void
}