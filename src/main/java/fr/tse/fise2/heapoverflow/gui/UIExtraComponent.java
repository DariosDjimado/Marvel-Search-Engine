package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.interfaces.UIComponent;
import fr.tse.fise2.heapoverflow.main.SelectionChangedListenner;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;

public class UIExtraComponent implements UIComponent {
    private final JPanel rightWrapperPanel;
    private SelectionChangedListenner selectionChangedListenner;

    public UIExtraComponent(JPanel rightWrapperPanel) {
        this.rightWrapperPanel = rightWrapperPanel;
    }

    public void setSelectionChangedListenner(SelectionChangedListenner selectionChangedListenner) {
        this.selectionChangedListenner = selectionChangedListenner;
    }

    @Override
    public void setSize() {

    }

    @Override
    public void build() {

    }

    @Override
    public void setVisible() {

    }

    public void setResultsComics(Comic[] comics) {
        this.rightWrapperPanel.removeAll();
        JScrollPane jscrollPane = new JScrollPane();
        JList jList = new JList();

        jList.addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                this.selectionChangedListenner.emitSelectionChanged(jList.getSelectedValue().toString().split("\\|")[2]);
            }
        });

        jscrollPane.setViewportView(jList);


        DefaultListModel listModel = new DefaultListModel();

        for (int i = 0; i < comics.length; i++) {
            listModel.add(i, comics[i].getTitle() + "||" + comics[i].getId());
        }
        jList.setModel(listModel);

        this.rightWrapperPanel.add(jscrollPane, BorderLayout.WEST);
    }


   /* public void setResultsCharacters(Character[] characters) {
        JScrollPane jscrollPane = new JScrollPane();
        JList jList = new JList();

        jList.addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                this.selectionChangedListenner.emitSelectionChanged(jList.getSelectedValue().toString().split("\\|")[2]);
            }
        });

        jscrollPane.setViewportView(jList);


        DefaultListModel listModel = new DefaultListModel();

        for (int i = 0; i < characters.length; i++) {
            listModel.add(i, characters[i].getName() + "||" + characters[i].getId());
        }
        jList.setModel(listModel);


        this.searchResultsPanel.add(jscrollPane);
    }*/
}
