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

/**
 * Favorite view
 *
 * @author Darios DJIMADO
 */
public class FavoriteView extends JPanel implements Observer {
    private static final ImageIcon EMPTY_STATE_IMAGE_ICON = new ImageIcon(FavoriteView.class.getResource("empty_heart.png"));
    private static Controller controller;
    final DefaultListModel<Comic> comicsListModel;
    private final CustomScrollPane comicScrollPane;
    private final JPanel emptyStatePanel;
    private final JLabel emptyStateLabel;

    /**
     * Creates a Favorite view with default settings.
     */
    FavoriteView() {
        super(new BorderLayout());
        UserAuthenticationModel.getInstance().addObserver(this);
        this.comicsListModel = new DefaultListModel<>();
        JList<Comic> comicsList = new JList<>(this.comicsListModel);
        comicsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        comicsList.setVisibleRowCount(-1);
        comicsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        comicsList.setCellRenderer(new ComicsListRenderer());

        // empty state
        this.emptyStatePanel = new JPanel(new BorderLayout());
        this.emptyStatePanel.setBackground(UIColor.EMPTY_STATE);
        this.emptyStateLabel = new JLabel();
        this.initEmptyState("Your don't have any favorites yet", EMPTY_STATE_IMAGE_ICON);
        this.emptyStateLabel.setOpaque(false);
        this.emptyStateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.emptyStatePanel.add(emptyStateLabel, BorderLayout.CENTER);
        JButton emptyStateButton = new PrimaryButton("Search and add new now...");
        emptyStateButton.addActionListener(e -> {
            if (controller != null) {
                controller.gotoSearchView();
            }
        });

        this.emptyStatePanel.add(emptyStateButton, BorderLayout.NORTH);

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
        this.comicScrollPane = new CustomScrollPane(comicsList);
        comicScrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(comicScrollPane, BorderLayout.CENTER);
    }

    /**
     * Setter
     *
     * @param controller if provide it will be used to switch between ui tabs
     */
    public static void setController(Controller controller) {
        FavoriteView.controller = controller;
    }

    /**
     * Refreshes comics list
     */
    public void refresh() {
        this.comicsListModel.clear();

        User user = UserAuthenticationModel.getUser();
        if (user != null) {
            List<ElementAssociationRow> elementAssociationRows = ElementsAssociation.findFavoriteComicsByUser(user.getId());
            this.toggleState(elementAssociationRows.size() != 0);
            CollectionsView.fillList(elementAssociationRows, comicsListModel);
        }
    }

    /**
     * Initializes the empty state label
     *
     * @param text text of the empty state
     * @param icon empty state icon
     */
    void initEmptyState(String text, ImageIcon icon) {
        this.emptyStateLabel.setIcon(icon);
        this.emptyStateLabel.setText(text);
    }

    /**
     * Toggles the sate of the component. It will show empty state icon if the list is empty.
     *
     * @param visible weather the empty state must be shown
     */
    void toggleState(boolean visible) {
        if (!visible) {
            this.removeAll();
            this.add(this.emptyStatePanel, BorderLayout.CENTER);
        } else {
            this.removeAll();
            this.add(this.comicScrollPane, BorderLayout.CENTER);
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
            if (arg == null) {
                this.comicsListModel.clear();
            }
        }
    }
}
