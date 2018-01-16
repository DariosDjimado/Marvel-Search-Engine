package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.Event;
import fr.tse.fise2.heapoverflow.marvelapi.Story;
import sun.reflect.generics.tree.Tree;
import sun.swing.SwingAccessor;

import javax.swing.*;
import java.awt.*;
import java.util.Set;
import java.util.TreeSet;

public class StoriesEventsPopUp extends JFrame implements SubRequestCaller {

    JTabbedPane tabs;
    final DefaultListModel<Comic> comicsListModel;
    final DefaultListModel<Character> charactersListModel;
    final InfoSubRequestsThread isrt;
    final JPanel loadingPane;
    final JList<Comic> comicsList;
    final JList<Character> charactersList;
    private Object dispedO;
    private int elementToken;

    public StoriesEventsPopUp() {
        this.isrt = InfoSubRequestsThread.getInstance();

        this.loadingPane = new JPanel();
        JLabel loadingLabel = new JLabel("Loading...");
        loadingLabel.setFont(Fonts.title1);

        this.comicsListModel = new DefaultListModel<>();
        comicsList = new JList<>(this.comicsListModel);
        comicsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        comicsList.setVisibleRowCount(-1);
        comicsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        comicsList.setCellRenderer(new ComicsListRenderer());

        this.charactersListModel = new DefaultListModel<>();
        charactersList = new JList<>(this.charactersListModel);
        charactersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        charactersList.setVisibleRowCount(-1);
        charactersList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        charactersList.setCellRenderer(new CharactersListRenderer());

        this.tabs = new JTabbedPane();
        tabs.addTab("Comics", new CustomScrollPane(comicsList));
        tabs.addTab("Characters", new CustomScrollPane(charactersList));

        this.setContentPane(tabs);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setMinimumSize(new Dimension(650, 600));
    }

    synchronized public void setDispedO(Object dispedO) {
        if(dispedO.hashCode() != this.elementToken) {
            comicsListModel.clear();
            charactersListModel.clear();
            if (dispedO.getClass() == Event.class) {
                this.dispedO = dispedO;
                this.elementToken = dispedO.hashCode();
                isrt.addJob("Comic", "Comics", ((Event) dispedO).getComics().getCollectionURI().substring(36), this.elementToken, this);
                isrt.addJob("Character", "Characters", ((Event) dispedO).getCharacters().getCollectionURI().substring(36), this.elementToken, this);
                this.setTitle(((Event) dispedO).getTitle());
            } else if (dispedO.getClass() == Story.class) {
                this.dispedO = dispedO;
                this.elementToken = dispedO.hashCode();
                isrt.addJob("Comic", "Comics", ((Story) dispedO).getComics().getCollectionURI().substring(36), this.elementToken, this);
                isrt.addJob("Character", "Characters", ((Story) dispedO).getCharacters().getCollectionURI().substring(36), this.elementToken, this);
                this.setTitle(((Story) dispedO).getTitle());
            } else {
                return;
            }
        }

        this.setVisible(true);
    }

    @Override
    synchronized public void updateList(Set elements, String elementType, String tab, int token, boolean end, MarvelListElement lastElement) {
        if(this.elementToken == token) {
            switch (elementType) {
                case "Comic":
                    for (Comic oneComic : (TreeSet<Comic>) elements) {
                        comicsListModel.addElement(oneComic);
                    }
                    break;
                case "Character":
                    for (Character oneCharacter : (TreeSet<Character>) elements) {
                        charactersListModel.addElement(oneCharacter);
                    }
                    break;
            }
        }
    }

    public JList<Comic> getComicsList() {
        return comicsList;
    }

    public JList<Character> getCharactersList() {
        return charactersList;
    }
}
