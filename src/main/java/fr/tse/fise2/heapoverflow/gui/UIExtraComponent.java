package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.events.SelectionChangedListener;
import fr.tse.fise2.heapoverflow.interfaces.UIComponent;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;

public class UIExtraComponent implements UIComponent {
    private final JPanel rightWrapperPanel;
    private SelectionChangedListener selectionChangedListener;

    public UIExtraComponent(JPanel rightWrapperPanel) {
        this.rightWrapperPanel = rightWrapperPanel;
    }

    public void setSelectionChangedListenner(SelectionChangedListener selectionChangedListener) {
        this.selectionChangedListener = selectionChangedListener;
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
        DefaultListModel<Comic> listModel = new DefaultListModel<>();
        for (Comic comic : comics) {

            listModel.addElement(comic);
        }
        JList<Comic> jList = new JList<>(listModel);
        jList.setCellRenderer(new ComicsListRenderer(jList));
        jList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jList.addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                this.selectionChangedListener.showComic(jList.getSelectedValue());
            }
        });
        this.rightWrapperPanel.add(new JScrollPane(jList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        this.rightWrapperPanel.revalidate();
        this.rightWrapperPanel.repaint();
    }


    public void setResultsCharacters(Character[] characters) {
        this.rightWrapperPanel.removeAll();

        if (characters != null) {
            DefaultListModel<Character> listModel = new DefaultListModel<>();
            for (Character character : characters) {
                listModel.addElement(character);
            }
            JList<Character> jList = new JList<>(listModel);
            jList.setCellRenderer(new CharactersListRenderer(jList));
            jList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            jList.addListSelectionListener((ListSelectionEvent e) -> {
                if (e.getValueIsAdjusting()) {
                    this.selectionChangedListener.showCharacter(jList.getSelectedValue());
                }
            });
            this.rightWrapperPanel.add(new JScrollPane(jList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        }

        this.rightWrapperPanel.revalidate();
        this.rightWrapperPanel.repaint();
    }
}
