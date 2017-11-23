package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.*;

/**
 * Window used to display detailed datas on characters, comics ...
 * <br/>
 * can display :
 * <ul>
 *     <li>Comics</li>
 * </ul>
 *
 * @author Théo Basty
 * @version 2.0
 * TODO move all subrequests to EDT
 */
public class DataShow{
    /**
     * Function to draw comic details on the window
     *
     * @param panel The panel in which to draw comic infos
     * @param comic The comic to display
     */
    public static void DrawComic(JPanel panel,Comic comic) {
        panel.removeAll();
        MarvelRequest request = new MarvelRequest();
        panel.setMinimumSize(new Dimension(600, 500));
        panel.setLayout(new BorderLayout());

        //region title display
        JLabel head = new JLabel();
        head.setBorder(new EmptyBorder(10, 10, 0, 10));
        head.setFont(Fonts.title1);
        head.setText(comic.getTitle());
        panel.add(head, BorderLayout.NORTH);
        //endregion
        //region detail display
        JPanel detail = new JPanel();
        detail.setLayout(new BoxLayout(detail, BoxLayout.PAGE_AXIS));
        detail.setBorder(new EmptyBorder(10, 5, 0, 10));

        //region references
        LinkedHashMap<String, String> references = new LinkedHashMap<>();
        references.put("ISBN : "        , comic.getIsbn());
        references.put("UPC : "         , comic.getUpc());
        references.put("Diamond Code : ", comic.getDiamondCode());
        references.put("EAN : "         , comic.getEan());
        references.put("ISSN : "        , comic.getIssn());

        JPanel referencesPane = new JPanel();
        referencesPane.setLayout(new GridLayout(3, 2));
        referencesPane.setBackground(Color.lightGray);
        referencesPane.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(2,2,2,2), BorderFactory.createLineBorder(Color.darkGray)));
        for(String reference : references.keySet()){
            JLabel title = new JLabel(reference);
            title.setFont(Fonts.boldRef);
            JLabel content = new JLabel(references.get(reference));
            content.setFont(Fonts.ref);
            JPanel refLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
            refLine.setOpaque(false);
            refLine.add(title);
            refLine.add(content);
            referencesPane.add(refLine);
        }
//        referencesPane.setMaximumSize(referencesPane.getLayout().minimumLayoutSize(referencesPane));
        JPanel alignedRefLeft = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        alignedRefLeft.add(referencesPane);
        detail.add(alignedRefLeft);
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

        JPanel detailPane = new JPanel();
        detailPane.setLayout(new BoxLayout(detailPane, BoxLayout.PAGE_AXIS));
        for(String detailLine : details.keySet()){
            JLabel title = new JLabel(detailLine);
            title.setFont(Fonts.boldContent);
            JLabel content = new JLabel(details.get(detailLine));
            content.setFont(Fonts.content);
            JPanel refLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            refLine.setOpaque(false);
            refLine.add(title);
            refLine.add(content);
            detailPane.add(refLine);
        }
        detailPane.setBorder(new EmptyBorder(5,0,0,0));
        detail.add(detailPane);
        //endregion

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper.add(detail);
        panel.add(wrapper, BorderLayout.CENTER);
//        panel.add(detailOld, BorderLayout.CENTER);
        //endregion
        //region Tabs display
        JTabbedPane tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(panel.getWidth(), 150));
        //region Description
        JEditorPane description = new JEditorPane();
        description.setText(comic.getDescription());
        description.setEditable(false);
        tabs.addTab("Description", new JScrollPane(description));
        //endregion
        //region Character
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

            DefaultListModel<MarvelListElement> characterListModel = new DefaultListModel<>();
            for (Character character : fetched) {
                characterListModel.addElement(new MarvelListElement(character.getName(), character.getResourceURI(), MarvelType.Character));
            }
            JList<MarvelListElement> characters = new JList<>(characterListModel);
            tabs.addTab("Character", new JScrollPane(characters));
        }
        catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        //endregion
        //region Creators
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

            DefaultListModel<MarvelListElement> creatorListModel = new DefaultListModel<>();
            for (Creator oneCreator : fetched) {
                creatorListModel.addElement(new MarvelListElement(oneCreator.getFullName(), oneCreator.getResourceURI(), MarvelType.Creator));
            }
            JList<MarvelListElement> creators = new JList<>(creatorListModel);
            tabs.addTab("Creators", new JScrollPane(creators));
        }
        catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }

        //endregion
        //region variants
        if(comic.getVariants().length > 0) {
            DefaultListModel<MarvelListElement> variantsListModel = new DefaultListModel<>();
            for (ComicSummary comicVariant : comic.getVariants()) {
                variantsListModel.addElement(new MarvelListElement(comicVariant.getName(), comic.getRessourceURI(), MarvelType.Comic));
            }
            JList<MarvelListElement> variants = new JList<>(variantsListModel);
            tabs.addTab("Variants", new JScrollPane(variants));
        }
        //endregion
        //region collections
//        if(true) {
        if(comic.getCollections().length > 0) {
            DefaultListModel<MarvelListElement> CollectionsListModel = new DefaultListModel<>();
            for (ComicSummary comicCollection : comic.getCollections()) {
                CollectionsListModel.addElement(new MarvelListElement(comicCollection.getName(), comic.getRessourceURI(), MarvelType.Comic));
            }
            JList<MarvelListElement> collections = new JList<>(CollectionsListModel);
            tabs.addTab("Collections", new JScrollPane(collections));
        }
        //endregion
        //region collected issues
//        if(true) {
        if(comic.getCollectedIssues().length > 0) {
            DefaultListModel<MarvelListElement> CollectedListModel = new DefaultListModel<>();
            for (ComicSummary comicCollected : comic.getCollectedIssues()) {
                CollectedListModel.addElement(new MarvelListElement(comicCollected.getName(), comic.getRessourceURI(), MarvelType.Comic));
            }
            JList<MarvelListElement> collected = new JList<>(CollectedListModel);
            tabs.addTab("Collected Issues", new JScrollPane(collected));
        }
        //endregion
        //region stories
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

            DefaultListModel<MarvelListElement> storiesListModel = new DefaultListModel<>();
            for (Comic oneComic : fetched) {
                storiesListModel.addElement(new MarvelListElement(oneComic.getTitle(), oneComic.getRessourceURI(), MarvelType.Story));
            }
            JList<MarvelListElement> stories = new JList<>(storiesListModel);
            tabs.addTab("Stories", new JScrollPane(stories));
        }
        catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        //endregion
        //region events
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

            DefaultListModel<MarvelListElement> eventsListModel = new DefaultListModel<>();
            for (Comic oneComic : fetched) {
                eventsListModel.addElement(new MarvelListElement(oneComic.getTitle(), oneComic.getRessourceURI(), MarvelType.Story));
            }
            JList<MarvelListElement> events = new JList<>(eventsListModel);
            tabs.addTab("Events", new JScrollPane(events));
        }
        catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        //endregion

        panel.add(tabs, BorderLayout.SOUTH);
        //endregion
        //region thumbnail display
        try {
            ShowThumbnail thumb = new ShowThumbnail(MarvelRequest.getImage(comic.getThumbnail(), UrlBuilder.ImageVariant.PORTRAIT_FANTASTIC));
            thumb.setPreferredSize(new Dimension(200, 274));
            thumb.setBorder(new EmptyBorder(10,10,0,5));
            panel.add(thumb, BorderLayout.WEST);
        } catch (Exception e){
            System.out.println(e);
        }
        //endregion

    }

    /**
     * Function to draw character details on the window
     * @param panel The panel in which to draw Character infos
     * @param character The Character to draw
     *
     */
    public static void DrawCharacter(JPanel panel, Character character) {
        panel.removeAll();
        panel.setMinimumSize(new Dimension(600, 500));
        panel.setLayout(new BorderLayout());

        //region title display
        JLabel head = new JLabel();
        head.setBorder(new EmptyBorder(10, 10, 0, 10));
        head.setFont(Fonts.title1);
        head.setText(character.getName());
        panel.add(head, BorderLayout.NORTH);
        //endregion
        //region detail display
        JPanel detail = new JPanel();
        detail.setLayout(new BoxLayout(detail, BoxLayout.PAGE_AXIS));
        detail.setBorder(new EmptyBorder(10, 5, 0, 10));

        LinkedHashMap<String, String> details = new LinkedHashMap<>();
        details.put("Appears in : ", "");
        details.put(" ", "- " + Integer.valueOf(character.getSeries().getAvailable()).toString() + " Series");
        details.put("", " - " + Integer.valueOf(character.getComics().getAvailable()).toString() + " Comics");
        details.put("Last Modification : ", character.getModified().substring(0, 10));

        for(String detailLine : details.keySet()){
            JLabel title = new JLabel(detailLine);
            title.setFont(Fonts.boldContent);
            JLabel content = new JLabel(details.get(detailLine));
            content.setFont(Fonts.content);
            JPanel refLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            refLine.setOpaque(false);
            refLine.add(title);
            refLine.add(content);
            detail.add(refLine);
        }
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper.add(detail);
        panel.add(wrapper, BorderLayout.CENTER);
        //endregion
        //region Tabs display
        JTabbedPane tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(panel.getWidth(), 150));
        //region Description
        JEditorPane description = new JEditorPane();
        description.setText(character.getDescription());
        description.setEditable(false);
        tabs.addTab("Description", new JScrollPane(description));
        //endregion
        //region Series
        MarvelRequest request = new MarvelRequest();
        try {
            ComicDataWrapper responseObj;
            int reqCount = 0;
            Set<Comic> fetched = new TreeSet<>();
            do {
                String response = request.getData(character.getSeries().getCollectionURI().substring(36) + "?limit=100&offset=" + 100*reqCount);
                responseObj = deserializeComics(response);
                fetched.addAll(Arrays.asList(responseObj.getData().getResults()));
                reqCount++;
            } while (responseObj.getData().getOffset()+responseObj.getData().getCount() < responseObj.getData().getTotal());

            DefaultListModel<MarvelListElement> comicListModel = new DefaultListModel<>();
            for (Comic comic : fetched) {
                comicListModel.addElement(new MarvelListElement(comic.getTitle(), comic.getRessourceURI(), MarvelType.Serie));
            }
            JList<MarvelListElement> comics = new JList<>(comicListModel);
            tabs.addTab("Series", new JScrollPane(comics));
        }
        catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }

//        DefaultListModel<SerieListElement> seriesListModel = new DefaultListModel<>();
//        for(SeriesSummary serie : character.getSeries().getItems()){
//            seriesListModel.addElement(new SerieListElement(serie));
//        }
//        JList<SerieListElement> series = new JList<>(seriesListModel);
//        tabs.addTab("Series", new JScrollPane(series));
        //endregion
        //region Comics
        try {
            ComicDataWrapper responseObj;
            int reqCount = 0;
            Set<Comic> fetched = new TreeSet<>();
            do {
                String response = request.getData(character.getComics().getCollectionURI().substring(36) + "?limit=100&offset=" + 100*reqCount);
                responseObj = deserializeComics(response);
                fetched.addAll(Arrays.asList(responseObj.getData().getResults()));
                reqCount++;
            } while (responseObj.getData().getOffset()+responseObj.getData().getCount() < responseObj.getData().getTotal());

            DefaultListModel<MarvelListElement> comicListModel = new DefaultListModel<>();
            for (Comic comic : fetched) {
                comicListModel.addElement(new MarvelListElement(comic.getTitle(), comic.getRessourceURI(), MarvelType.Comic));
            }
            JList<MarvelListElement> comics = new JList<>(comicListModel);
            tabs.addTab("Comics", new JScrollPane(comics));
        } catch (SocketTimeoutException ex) {
            tabs.addTab("Comics", new JLabel("Request timeout"));
        }
        catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        //endregion
        //region Stories
        try {
            ComicDataWrapper responseObj;
            int reqCount = 0;
            Set<Comic> fetched = new TreeSet<>();
            do {
                String response = request.getData(character.getStories().getCollectionURI().substring(36) + "?limit=100&offset=" + 100*reqCount);
                responseObj = deserializeComics(response);
                fetched.addAll(Arrays.asList(responseObj.getData().getResults()));
                reqCount++;
            } while (responseObj.getData().getOffset()+responseObj.getData().getCount() < responseObj.getData().getTotal());

            DefaultListModel<MarvelListElement> storiesListModel = new DefaultListModel<>();
            for (Comic story : fetched) {
                storiesListModel.addElement(new MarvelListElement(story.getTitle(), story.getRessourceURI(), MarvelType.Story));
            }
            JList<MarvelListElement> stories = new JList<>(storiesListModel);
            tabs.addTab("Stories", new JScrollPane(stories));
        }
        catch (SocketTimeoutException ex){
            tabs.addTab("Stories", new JLabel("Request timeout"));
        }
        catch (Exception ex){
            System.out.println(ex);
            ex.printStackTrace();
        }
        //endregion
        //region Events
        try {
            ComicDataWrapper responseObj;
            int reqCount = 0;
            Set<Comic> fetched = new TreeSet<>();
            do {
                String response = request.getData(character.getEvents().getCollectionURI().substring(36) + "?limit=100&offset=" + 100*reqCount);
                responseObj = deserializeComics(response);
                fetched.addAll(Arrays.asList(responseObj.getData().getResults()));
                reqCount++;
            } while (responseObj.getData().getOffset()+responseObj.getData().getCount() < responseObj.getData().getTotal());

            DefaultListModel<MarvelListElement> eventsListModel = new DefaultListModel<>();
            for (Comic event : fetched) {
                eventsListModel.addElement(new MarvelListElement(event.getTitle(), event.getRessourceURI(), MarvelType.Story));
            }
            JList<MarvelListElement> events = new JList<>(eventsListModel);
            tabs.addTab("Events", new JScrollPane(events));
        }
        catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        //endregion

        panel.add(tabs, BorderLayout.SOUTH);
        //endregion
        //region thumbnail display
        try {
            ShowThumbnail thumb = new ShowThumbnail(MarvelRequest.getImage(character.getThumbnail(), UrlBuilder.ImageVariant.PORTRAIT_FANTASTIC));
            thumb.setPreferredSize(new Dimension(200, 274));
            thumb.setBorder(new EmptyBorder(10, 10, 0, 5));
            panel.add(thumb, BorderLayout.WEST);
        } catch (Exception e) {
            System.out.println(e);
        }

        //endregion
    }

    /**
     * Function to draw an empty layout on a panel
     *
     * @param panel The panel in which to draw the empty layout
     */
    public static void DrawEmpty(JPanel panel){
        panel.removeAll();
        panel.setMinimumSize(new Dimension(600, 500));
        panel.setLayout(new BorderLayout());

        //region title display
        JLabel head = new JLabel();
        head.setBorder(new EmptyBorder(10, 10, 0, 10));
        head.setFont(Fonts.title1);
        head.setText("Choose something to display");
        panel.add(head, BorderLayout.NORTH);
        //endregion
        //region Tabs display
        JTabbedPane tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(panel.getWidth(), 150));
        panel.add(tabs, BorderLayout.SOUTH);
        //endregion
        //region thumbnail display
        try {
            ShowThumbnail thumb = new ShowThumbnail(new BufferedImage(168, 252, BufferedImage.TYPE_INT_ARGB));
            thumb.setPreferredSize(new Dimension(200, 274));
            thumb.setBorder(new EmptyBorder(10, 10, 0, 5));
            panel.add(thumb, BorderLayout.WEST);
        } catch (Exception e) {
            System.out.println(e);
        }
        //endregion
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