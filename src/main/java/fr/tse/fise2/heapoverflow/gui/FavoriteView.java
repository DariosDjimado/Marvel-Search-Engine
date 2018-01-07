package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.authentication.User;
import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;
import fr.tse.fise2.heapoverflow.database.ElementsAssociation;
import fr.tse.fise2.heapoverflow.main.Controller;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class FavoriteView extends JPanel implements Observer {
    private static Controller controller;
    private final DefaultListModel<Comic> comicsListModel;


    FavoriteView() {
        super(new BorderLayout());
        UserAuthenticationModel.getInstance().addObserver(this);
        this.comicsListModel = new DefaultListModel<>();
        JList<Comic> comicsList = new JList<>(this.comicsListModel);
        comicsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        comicsList.setVisibleRowCount(-1);
        comicsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        comicsList.setCellRenderer(new ComicsListRenderer());

        // right click
        comicsList.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = comicsList.locationToIndex(e.getPoint());
                    comicsList.setSelectedIndex(row);
                }
            }
        });

        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem viewMenuItem = new JMenuItem("View");
        viewMenuItem.addActionListener(e -> {
            if (controller != null) {
                controller.showComic(comicsList.getSelectedValue());
                controller.gotoSearchView();
            }
        });

        popupMenu.add(viewMenuItem);
        comicsList.setComponentPopupMenu(popupMenu);
        CustomScrollPane comicScrollPane = new CustomScrollPane(comicsList);
        comicScrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(comicScrollPane, BorderLayout.CENTER);
    }

    public static void setController(Controller controller) {
        FavoriteView.controller = controller;
    }

    private void refresh() {
        this.comicsListModel.clear();

        User user = UserAuthenticationModel.getUser();
        if (user != null) {
            List<ElementAssociationRow> elementAssociationRows = ElementsAssociation.findFavoriteComicsByUser(user.getId());
            CollectionsView.fillList(elementAssociationRows, comicsListModel);
        }
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
        if (o == UserAuthenticationModel.getInstance()) {
            if (arg != null) {
                this.refresh();
            } else {
                this.comicsListModel.clear();
            }
        }
    }
}
