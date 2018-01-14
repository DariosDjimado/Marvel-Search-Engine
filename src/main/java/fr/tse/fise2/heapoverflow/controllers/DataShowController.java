package fr.tse.fise2.heapoverflow.controllers;

import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.gui.MarvelListElement;
import fr.tse.fise2.heapoverflow.gui.StoriesEventsPopUp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Observable;
import java.util.Observer;

public class DataShowController implements Observer{

    private static final Logger LOGGER = LoggerFactory.getLogger(DataShow.class);
    private final DataShow dataShow;
    private final StoriesEventsPopUp popup;

    private ListSelectionListener updateListener;
    private ListSelectionListener popupListener;

    public DataShowController(DataShow dataShow) {
        this.dataShow = dataShow;
        this.dataShow.addObserver(this);
        this.popup = new StoriesEventsPopUp();
        new PopupController(this.popup, this.dataShow);


        updateListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList source = (JList)e.getSource();
                MarvelListElement selected = (MarvelListElement)source.getSelectedValue();
                if(selected != null && selected.getDispedO() != null){
                    dataShow.DrawObject(selected.getDispedO());
                }
            }
        };

        popupListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList source = (JList)e.getSource();
                MarvelListElement selected = (MarvelListElement)source.getSelectedValue();
                if(selected != null && selected.getDispedO() != null){
                    popup.setDispedO(selected.getDispedO());
                }
            }
        };
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if(arg.getClass() == String.class){
            if (arg == "unregister"){
                for(String listName : dataShow.getTabsJLists().keySet()){
                    switch (listName){
                        case "Characters":
                        case "Comics":
                            dataShow.getTabsJLists().get(listName).removeListSelectionListener(updateListener);
                            break;
                        case "Stories":
                        case "Events":
                            dataShow.getTabsJLists().get(listName).removeListSelectionListener(popupListener);
                            break;
                    }
                }
            }
            else if(arg == "register"){
                for(String listName : dataShow.getTabsJLists().keySet()){
                    switch (listName){
                        case "Characters":
                        case "Comics":
                            dataShow.getTabsJLists().get(listName).addListSelectionListener(updateListener);
                            break;
                        case "Stories":
                        case "Events":
                            dataShow.getTabsJLists().get(listName).addListSelectionListener(popupListener);
                            break;
                    }
                }
            }
            else {
                if(LOGGER.isWarnEnabled()){
                    LOGGER.warn("Invalid update argument \"" + arg + "\"");
                }
            }
        }
    }
}
