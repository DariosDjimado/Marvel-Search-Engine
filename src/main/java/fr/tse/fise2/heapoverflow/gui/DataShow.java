package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Window used to display detailed datas on characters, comics ...
 * <br/>
 * can display :
 * <ul>
 *     <li>Comics</li>
 * </ul>
 *
 * @author Théo Basty
 * @version 0.1
 */
public class DataShow extends JFrame {
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
        ShowComicDetails detail = new ShowComicDetails(comic);
        this.getContentPane().add(detail, BorderLayout.CENTER);
        //endregion

        //region Tabs display
        JTabbedPane tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(this.getWidth(), 150));
        //region Description
        JEditorPane description = new JEditorPane();
        description.setText(comic.getDescription());
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
        }
        catch (Exception e){
            System.out.println(e);
        }
        //endregion

        this.setResizable(false);
        this.setVisible(true);
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