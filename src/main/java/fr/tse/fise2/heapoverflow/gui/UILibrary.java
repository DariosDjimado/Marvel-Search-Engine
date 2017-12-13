package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class UILibrary {
    JPanel panel;

    JTabbedPane tabs;

    JList<MarvelListElement> libList;
    DefaultListModel<MarvelListElement> libListModel;

    JList<MarvelListElement> favList;
    DefaultListModel<MarvelListElement> favListModel;

    JTextField libTxtSearch;
    JButton libBtnSearch;

    JButton libBtnUnOwn;
    JButton libBtnFav;
    JButton libBtnRead;
    JButton libBtnUnRead;

    JTextField favTxtSearch;
    JButton favBtnSearch;

    JButton favBtnUnFav;
    JButton favBtnOwn;
    JButton favBtnRead;
    JButton favBtnUnRead;

    public UILibrary(JPanel panel) {
        this.panel = panel;
        this.panel.setLayout(new BorderLayout());

        tabs = new JTabbedPane();
        this.panel.add(tabs, BorderLayout.CENTER);
        JPanel libPane = new JPanel(new BorderLayout());
        JPanel favPane = new JPanel(new BorderLayout());
        tabs.add("Library", libPane);
        tabs.add("Favorite", favPane);

        //region Library
        libList = new JList<>();
        libListModel = new DefaultListModel<>();
        libList.setModel(libListModel);
        libPane.add(new JScrollPane(libList), BorderLayout.CENTER);

        JPanel libRightPane = new JPanel(new BorderLayout());
        libPane.add(libRightPane, BorderLayout.EAST);

        libTxtSearch = new JTextField();
        libTxtSearch.setPreferredSize(new Dimension(200, 30));
        libBtnSearch = new JButton("Search");
        JPanel libSearchBar = new JPanel();
        libSearchBar.add(libTxtSearch);
        libSearchBar.add(libBtnSearch);
        libRightPane.add(libSearchBar, BorderLayout.NORTH);

        libBtnUnOwn = new JButton("-Library");
        libBtnFav = new JButton("+Favorite");
        libBtnRead = new JButton("+Read");
        libBtnUnRead = new JButton("-Read");
        JPanel libBtnPane = new JPanel( new GridLayout(2, 2, 4, 4));
        libBtnPane.setBorder(new EmptyBorder(4, 4, 4, 4));
        libBtnPane.add(libBtnFav);
        libBtnPane.add(libBtnRead);
        libBtnPane.add(libBtnUnOwn);
        libBtnPane.add(libBtnUnRead);
        libRightPane.add(libBtnPane, BorderLayout.SOUTH);
        //endregion
        //region Favorite
        favList = new JList<>();
        favListModel = new DefaultListModel<>();
        favList.setModel(favListModel);
        favPane.add(new JScrollPane(favList), BorderLayout.CENTER);

        JPanel favRightPane = new JPanel(new BorderLayout());
        favPane.add(favRightPane, BorderLayout.EAST);

        favTxtSearch = new JTextField();
        favTxtSearch.setPreferredSize(new Dimension(200, 30));
        favBtnSearch = new JButton("Search");
        JPanel favSearchBar = new JPanel();
        favSearchBar.add(favTxtSearch);
        favSearchBar.add(favBtnSearch);
        favRightPane.add(favSearchBar, BorderLayout.NORTH);

        favBtnUnFav = new JButton("-Favorite");
        favBtnOwn = new JButton("+Library");
        favBtnRead = new JButton("+Read");
        favBtnUnRead = new JButton("-Read");
        JPanel favBtnPane = new JPanel( new GridLayout(2, 2, 4, 4));
        favBtnPane.setBorder(new EmptyBorder(4, 4, 4, 4));
        favBtnPane.add(favBtnOwn);
        favBtnPane.add(favBtnRead);
        favBtnPane.add(favBtnUnFav);
        favBtnPane.add(favBtnUnRead);
        favRightPane.add(favBtnPane, BorderLayout.SOUTH);
        //endregion

        tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                switch(tabs.getTitleAt(tabs.getSelectedIndex())){
                    case "Favorite":
                        refreshFav();
                        break;
                    case "Library":
                        refreshLib();
                        break;
                    default:
                        System.err.println("Unknown Tab: " + tabs.getTitleAt(tabs.getSelectedIndex()));
                }
            }
        });

        refreshLib();
//        refreshFav();
        this.panel.setVisible(true);
    }

    public void refreshLib(){
        libListModel.clear();
        libListModel.addElement(new MarvelListElement("Iron man", null, MarvelType.Comic));

        libListModel.addElement(new MarvelListElement("Captain America Civil War", null, MarvelType.Comic));
    }

    public void refreshFav(){
        favListModel.clear();
        favListModel.addElement(new MarvelListElement("Captain America Civil War", null, MarvelType.Comic));

        favListModel.addElement(new MarvelListElement("Iron man", null, MarvelType.Comic));
    }

    public DefaultListModel<MarvelListElement> getLibListModel() {
        return libListModel;
    }

    public DefaultListModel<MarvelListElement> getFavListModel() {
        return favListModel;
    }

    public JTextField getLibTxtSearch() {
        return libTxtSearch;
    }

    public JButton getLibBtnSearch() {
        return libBtnSearch;
    }

    public JButton getLibBtnUnOwn() {
        return libBtnUnOwn;
    }

    public JButton getLibBtnFav() {
        return libBtnFav;
    }

    public JButton getLibBtnRead() {
        return libBtnRead;
    }

    public JButton getLibBtnUnRead() {
        return libBtnUnRead;
    }

    public JTextField getFavTxtSearch() {
        return favTxtSearch;
    }

    public JButton getFavBtnSearch() {
        return favBtnSearch;
    }

    public JButton getFavBtnUnFav() {
        return favBtnUnFav;
    }

    public JButton getFavBtnOwn() {
        return favBtnOwn;
    }

    public JButton getFavBtnRead() {
        return favBtnRead;
    }

    public JButton getFavBtnUnRead() {
        return favBtnUnRead;
    }
}
