package fr.tse.fise2.heapoverflow.controllers;

import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.gui.StoriesEventsPopUp;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PopupController {

    private StoriesEventsPopUp popup;

    private DataShow dataShow;

    final private ListSelectionListener showElement;

    public PopupController(StoriesEventsPopUp popup, DataShow dataShow) {
        this.popup = popup;
        this.dataShow = dataShow;

        showElement = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList source = (JList) e.getSource();
                Object selected = source.getSelectedValue();
                if (selected != null) {
                    dataShow.DrawObject(selected);
                    popup.setVisible(false);
                }
            }
        };

        popup.getCharactersList().addListSelectionListener(showElement);
        popup.getComicsList().addListSelectionListener(showElement);
    }
}
