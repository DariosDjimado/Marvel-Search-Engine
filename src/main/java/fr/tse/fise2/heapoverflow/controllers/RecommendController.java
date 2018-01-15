package fr.tse.fise2.heapoverflow.controllers;

import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.gui.RecommendView;
import fr.tse.fise2.heapoverflow.main.Controller;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RecommendController {

    private final ListSelectionListener dispListener;

    private RecommendView view;

    private DataShow dataShow;

    public RecommendController(RecommendView view, DataShow dataShow, Controller controller) {
        this.view = view;
        this.dataShow = dataShow;
        dispListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Object value = ((JList<Comic>)e.getSource()).getSelectedValue();
                if(value != null) {
                    dataShow.DrawObject(value);
                    controller.gotoSearchView();
                }
            }
        };

        view.getBestRankList().addListSelectionListener(dispListener);
        view.getMostFavList().addListSelectionListener(dispListener);
        view.getNextToReadList().addListSelectionListener(dispListener);
    }
}
