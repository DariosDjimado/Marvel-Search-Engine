package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.main.SearchListenner;
import fr.tse.fise2.heapoverflow.marvelapi.*;
import fr.tse.fise2.heapoverflow.marvelapi.Character;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

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
 */
public class DataShow extends JFrame implements SearchListenner {
    /**
     * Function to display comics
     * @param comic
     *      The comic object to display
     */
    public DataShow(final Comic comic) throws HeadlessException {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                DrawComic(comic);
            }
        });

    }

    /**
     * Function to display Character
     *
     * @param character The character object to display
     */
    public DataShow(final Character character) throws HeadlessException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DrawCharacter(character);
            }
        });

    }

    /**
     * Function to draw comic details on the window
     *
     * @param comic The comic to display
     */
    public void DrawComic(Comic comic) {
        this.setTitle(comic.getTitle());
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.getContentPane().setLayout(new BorderLayout());

        //region title display
        JLabel head = new JLabel();
        head.setBorder(new EmptyBorder(10, 10, 0, 10));
        head.setFont(Fonts.title1);
        head.setText(comic.getTitle());
        this.getContentPane().add(head, BorderLayout.NORTH);
        //endregion

        //region detail display
//        ShowComicDetails detail = new ShowComicDetails(comic);
        JPanel detail = new JPanel();
        detail.setLayout(new BoxLayout(detail, BoxLayout.PAGE_AXIS));
        detail.setBorder(new EmptyBorder(10, 5, 0, 10));

        LinkedHashMap<String, String> references = new LinkedHashMap<>();
        references.put("ISBN : "        , comic.getIsbn());
        references.put("UPC : "         , comic.getUpc());
        references.put("Diamond Code : ", comic.getDiamondCode());
        references.put("EAN : "         , comic.getEan());
        references.put("ISSN : "        , comic.getIssn());
        JPanel referencesPane = new JPanel();
        referencesPane.setLayout(new BoxLayout(referencesPane, BoxLayout.Y_AXIS));
        referencesPane.setBackground(Color.lightGray);
        referencesPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.darkGray), new EmptyBorder(5,5,5,5)));
        for(String reference : references.keySet()){
            JLabel title = new JLabel(reference);
            title.setFont(Fonts.boldContent);
            JLabel content = new JLabel(references.get(reference));
            content.setFont(Fonts.content);
            JPanel refLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            refLine.setOpaque(false);
            refLine.add(title);
            refLine.add(content);
            referencesPane.add(refLine);
        }
        referencesPane.setMaximumSize(referencesPane.getLayout().minimumLayoutSize(referencesPane));
        JPanel alignedRefLeft = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        alignedRefLeft.add(referencesPane);
        detail.add(alignedRefLeft);


        this.getContentPane().add(detail, BorderLayout.CENTER);
        //endregion

        //region Tabs display
        JTabbedPane tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(this.getWidth(), 150));
        //region Description
        JEditorPane description = new JEditorPane();
        description.setText(comic.getDescription());
        description.setEditable(false);
        tabs.addTab("Description", new JScrollPane(description));
        //endregion
        //region Character
        DefaultListModel<CharacterListElement> charListModel = new DefaultListModel<>();
        for(CharacterSummary character : comic.getCharacters().getItems()){
            charListModel.addElement(new CharacterListElement(character));
        }
        JList<CharacterListElement> characters = new JList<>(charListModel);
        tabs.addTab("Characters", new JScrollPane(characters));
        //endregion
        //region Creators
        DefaultListModel<CreatorListElement> creaListModel = new DefaultListModel<>();
        for(CreatorSummary creator : comic.getCreators().getItems()){
            creaListModel.addElement(new CreatorListElement(creator));
        }
        JList<CreatorListElement> creators = new JList<>(creaListModel);
        tabs.addTab("Creators", new JScrollPane(creators));
        //endregion

        this.getContentPane().add(tabs, BorderLayout.SOUTH);
        //endregion

        //region thumbnail display
        try {
            ShowThumbnail thumb = new ShowThumbnail(MarvelRequest.getImage(comic.getThumbnail(), UrlBuilder.ImageVariant.PORTRAIT_FANTASTIC));
            thumb.setPreferredSize(new Dimension(200, 274));
            thumb.setBorder(new EmptyBorder(10,10,0,5));
            this.getContentPane().add(thumb, BorderLayout.WEST);
        } catch (Exception e){
            System.out.println(e);
        }
        //endregion

        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * Function to draw character details on the window
     * TODO: fetch all comics and series appearances from API
     * @param character
     *
     */
    public void DrawCharacter(Character character) {
        this.setTitle(character.getName());
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.getContentPane().setLayout(new BorderLayout());

        //region title display
        JLabel head = new JLabel();
        head.setBorder(new EmptyBorder(10, 10, 0, 10));
        head.setFont(Fonts.title1);
        head.setText(character.getName());
        this.getContentPane().add(head, BorderLayout.NORTH);
        //endregion

        //region detail display
        ShowCharacterDetails detail = new ShowCharacterDetails(character);
        this.getContentPane().add(detail, BorderLayout.CENTER);
        //endregion

        //region Tabs display
        JTabbedPane tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(this.getWidth(), 150));
        //region Description
        JEditorPane description = new JEditorPane();
        description.setText(character.getDescription());
        description.setEditable(false);
        tabs.addTab("Description", new JScrollPane(description));
        //endregion
        //region Series
        DefaultListModel<SerieListElement> seriesListModel = new DefaultListModel<>();
        for(SeriesSummary serie : character.getSeries().getItems()){
            seriesListModel.addElement(new SerieListElement(serie));
        }
        JList<SerieListElement> series = new JList<>(seriesListModel);
        tabs.addTab("Series", new JScrollPane(series));
        //endregion
        //region Comics
        DefaultListModel<ComicListElement> comicListModel = new DefaultListModel<>();
        for(ComicSummary comic : character.getComics().getItems()){
            comicListModel.addElement(new ComicListElement(comic));
        }
        JList<ComicListElement> comics = new JList<>(comicListModel);
        tabs.addTab("Comics", new JScrollPane(comics));
        //endregion

        this.getContentPane().add(tabs, BorderLayout.SOUTH);
        //endregion

        //region thumbnail display
        try {
            ShowThumbnail thumb = new ShowThumbnail(MarvelRequest.getImage(character.getThumbnail(), UrlBuilder.ImageVariant.PORTRAIT_FANTASTIC));
            thumb.setPreferredSize(new Dimension(200, 274));
            thumb.setBorder(new EmptyBorder(10, 10, 0, 5));
            this.getContentPane().add(thumb, BorderLayout.WEST);
        } catch (Exception e) {
            System.out.println(e);
        }
        //endregion

        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void onComicAvailable(Comic c) {
        this.setContentPane(new JPanel());
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                DrawComic(c);
            }
        });

    }

    @Override
    public void onCharacterAvailable(Character c) {
        this.setContentPane(new JPanel());
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DrawCharacter(c);
            }
        });
    }
}

/**
 * Class to adapt Character Summary for JList display
 * @author Théo Basty
 */
class CharacterListElement{
    /**
     * CharacterSummary to be listed
     */
    CharacterSummary character;

    /**
     * Constructor
     * @param character
     *      Character to be listed
     */
    public CharacterListElement(CharacterSummary character) {
        this.character = character;
    }

    /**
     * CharacterSummary getter
     * @return
     *      The character represented
     */
    public CharacterSummary getCharacter() {
        return character;
    }

    /**
     * CharacterSummary setter
     * @param character
     *      Character to be listed
     */
    public void setCharacter(CharacterSummary character) {
        this.character = character;
    }

    /**
     * Override toString to return only the name of the character
     * @return
     *      name of the character
     */
    @Override
    public String toString() {
        return this.character.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharacterListElement that = (CharacterListElement) o;

        return character != null ? character.equals(that.character) : that.character == null;
    }

    @Override
    public int hashCode() {
        return character.hashCode();
    }
}/**

 * Class to adapt Creator Summary for JList display
 * @author Théo Basty
 */
class CreatorListElement{
    /**
     * CharacterSummary to be listed
     */
    CreatorSummary creator;

    /**
     * Constructor
     * @param creator
     *      Creator to be listed
     */
    public CreatorListElement(CreatorSummary creator) {
        this.creator = creator;
    }

    /**
     * CreatorSummary getter
     * @return
     *      The creator represented
     */
    public CreatorSummary getCharacter() {
        return creator;
    }

    /**
     * CreatorSummary setter
     * @param creator
     *      Creator to be listed
     */
    public void setCharacter(CreatorSummary creator) {
        this.creator = creator;
    }

    /**
     * Override toString to return only the name of the character
     * @return
     *      name of the creator
     */
    @Override
    public String toString() {
        return this.creator.getName() + " : " + this.creator.getRole();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreatorListElement that = (CreatorListElement) o;

        return creator != null ? creator.equals(that.creator) : that.creator == null;
    }

    @Override
    public int hashCode() {
        return creator.hashCode();
    }
}

 /** Class to adapt Comic Summary for JList display
   * @author Théo Basty
   */
class ComicListElement{
    /**
     * ComicSummary to be listed
     */
    ComicSummary comic;

    /**
     * Constructor
     * @param comic
     *      Comic to be listed
     */
    public ComicListElement(ComicSummary comic) {
        this.comic = comic;
    }

    /**
     * ComicSummary getter
     * @return
     *      The comic represented
     */
    public ComicSummary getCharacter() {
        return comic;
    }

    /**
     * ComicSummary setter
     * @param comic
     *      Comic to be listed
     */
    public void setCharacter(ComicSummary comic) {
        this.comic = comic;
    }

    /**
     * Override toString to return only the name of the comic
     * @return
     *      name of the comic
     */
    @Override
    public String toString() {
        return this.comic.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComicListElement that = (ComicListElement) o;

        return comic != null ? comic.equals(that.comic) : that.comic == null;
    }

    @Override
    public int hashCode() {
        return comic.hashCode();
    }
}

 /** Class to adapt Serie Summary for JList display
  * @author Théo Basty
  */
class SerieListElement{
    /**
     * SeriesSummary to be listed
     */
    SeriesSummary serie;

    /**
     * Constructor
     * @param serie
     *      Serie to be listed
     */
    public SerieListElement(SeriesSummary serie) {
        this.serie = serie;
    }

    /**
     * CreatorSummary getter
     * @return
     *      The serie represented
     */
    public SeriesSummary getCharacter() {
        return serie;
    }

    /**
     * SeriesSummary setter
     * @param serie
     *      Serie to be listed
     */
    public void setCharacter(SeriesSummary serie) {
        this.serie = serie;
    }

    /**
     * Override toString to return only the name of the serie
     * @return
     *      name of the serie
     */
    @Override
    public String toString() {
        return this.serie.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SerieListElement that = (SerieListElement) o;

        return serie != null ? serie.equals(that.serie) : that.serie == null;
    }

    @Override
    public int hashCode() {
        return serie.hashCode();
    }
}