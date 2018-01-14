package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.authentication.User;
import fr.tse.fise2.heapoverflow.database.CollectionsRow;
import fr.tse.fise2.heapoverflow.database.CollectionsTable;
import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;
import fr.tse.fise2.heapoverflow.database.ElementsAssociation;
import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import fr.tse.fise2.heapoverflow.main.Controller;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.ComicDataWrapper;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * Collection view implements how collections will appear on the UI. It also provides all necessary button listeners.
 *
 * @author Lionel RAJAONA
 * @author Darios DJIMADO
 */
public class CollectionsView extends JPanel implements Observer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionsView.class);
    private static final ImageIcon EMPTY_STATE_IMAGE_ICON = new ImageIcon(CollectionsView.class.getResource("empty_collection.png"));
    private static final ImageIcon addIcon = new ImageIcon(CollectionsView.class.getResource("addIcon.png"));
    private static final ImageIcon deleteIcon = new ImageIcon(CollectionsView.class.getResource("deleteIcon.png"));
    private static final ImageIcon editIcon = new ImageIcon(CollectionsView.class.getResource("editIcon.png"));
    private static CollectionsView instance;
    private static Controller controller;
    private final JButton createCollectionsButton;
    private final JPanel leftPanel;
    private final JPanel rightPanel;
    private final JButton deleteCollection;
    private final JButton editCollection;
    private final JList<Comic> comicsList;
    private final DefaultListModel<Comic> listModel;
    private final CustomScrollPane comicsListScrollPane;
    private final JList<CollectionsRow> collectionsList;
    private final CustomScrollPane collectionsListScrollPane;
    private final DefaultListModel<CollectionsRow> collectionsListModel;
    private final JPanel comicsListScrollPaneContainer;
    private final JPopupMenu comicListPopupMenu;
    private final CustomDialog editCollectionDialog;
    private final JTextField editCollectionDialogTextField;
    private final JTextArea editCollectionDialogTextArea;
    private final JButton editCollectionDialogSaveButton;
    private final JButton editCollectionDialogCancelButton;
    private final JPanel emptyStatePanel;
    private final JLabel emptyStateLabel;
    private final GroupLayout selGroupLayout;
    private CollectionsRow currentCollectionsRow;
    private boolean createNewCollection;


    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    private CollectionsView() {


        UserAuthenticationModel.getInstance().addObserver(this);

        this.createCollectionsButton = new DecorateButtonFormat(addIcon);


        this.leftPanel = new JPanel(new BorderLayout());
        this.rightPanel = new JPanel();

        this.editCollection = new DecorateButtonFormat(editIcon);
        this.editCollection.setOpaque(true);

        this.deleteCollection = new DecorateButtonFormat(deleteIcon);

        this.listModel = new DefaultListModel<>();
        this.comicsList = new JList<>(this.listModel);

        this.comicsListScrollPane = new CustomScrollPane(this.comicsList);

        this.collectionsListModel = new DefaultListModel<>();
        this.collectionsList = new JList<>(this.collectionsListModel);
        this.collectionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.collectionsList.setCellRenderer(new CollectionsListRenderer());
        this.collectionsListScrollPane = new CustomScrollPane(this.collectionsList);
        this.comicsListScrollPaneContainer = new JPanel(new BorderLayout());

        this.comicListPopupMenu = new JPopupMenu();

        this.editCollectionDialog = new CustomDialog(null, "Collection");
        this.editCollectionDialogTextField = new CustomTextField("Title");
        this.editCollectionDialogTextArea = new CustomTextArea("Description");

        this.editCollectionDialogSaveButton = new PrimaryButton("Save");
        this.editCollectionDialogCancelButton = new DefaultButton("Cancel");

        // empty state
        this.emptyStatePanel = new JPanel(new BorderLayout());
        this.emptyStatePanel.setBackground(UIColor.EMPTY_STATE);
        this.emptyStateLabel = new JLabel();

        // layout
        this.selGroupLayout = new GroupLayout(this);

        this.initComponent();
        this.createCollectionsButtonActionsListener();
        this.selectCollectionListener();
        this.deleteButtonActionListener();
        this.editCollectionActionListener();
        this.comicListContextMenu();
        this.setList();
    }

    public static CollectionsView getInstance() {
        if (instance == null) {
            instance = new CollectionsView();
        }
        return instance;
    }

    public static void fillList(List<ElementAssociationRow> elementAssociationRows, DefaultListModel<Comic> listModel) {
        for (ElementAssociationRow ear : elementAssociationRows) {
            MarvelRequest.getInstance().asyncGetData("comics/" + ear.getElementID(), null, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    AppErrorHandler.onError(e);
                    if (LOGGER.isErrorEnabled()) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }

                @Override
                public void onResponse(Call call, Response res) throws IOException {
                    MarvelRequest.endRequest(call.request().url().toString());
                    String response;
                    ResponseBody body = res.body();
                    if (body != null) {
                        response = body.string();
                        ComicDataWrapper comicDataWrapper = MarvelRequest.deserializeComics(response);
                        listModel.addElement(comicDataWrapper.getData().getResults()[0]);
                    }
                }
            });

        }
    }

    public static void setController(Controller controller) {
        CollectionsView.controller = controller;
    }

    private void setList() {
        this.collectionsListModel.clear();
        if (UserAuthenticationModel.getUser() != null) {
            List<CollectionsRow> collections = CollectionsTable.findCollectionsByUserId(
                    Objects.requireNonNull(UserAuthenticationModel.getUser()).getId());

            if (collections.size() == 0) {
                this.rightPanel.setVisible(false);
                this.leftPanel.setVisible(false);
                this.emptyStatePanel.setVisible(true);
            } else {
                this.rightPanel.setVisible(true);
                this.leftPanel.setVisible(true);
                this.emptyStatePanel.setVisible(false);

                for (CollectionsRow row : collections) {
                    this.collectionsListModel.addElement(row);
                }
            }
        }
    }

    private void initComponent() {
        // empty state
        this.emptyStateLabel.setIcon(EMPTY_STATE_IMAGE_ICON);
        this.emptyStateLabel.setText("You haven't created any collection yet");
        this.emptyStateLabel.setOpaque(false);
        this.emptyStateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.emptyStatePanel.add(emptyStateLabel, BorderLayout.CENTER);

        // self
        this.setBackground(UIColor.MAIN_BACKGROUND_COLOR);

        // create collection button
        this.createCollectionsButton.setOpaque(false);

        // left panel
        this.leftPanel.add(new JLabel("List of collections"), BorderLayout.NORTH);
        this.leftPanel.setMaximumSize(new Dimension(300, Short.MAX_VALUE));
        this.collectionsListScrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.leftPanel.add(this.collectionsListScrollPane, BorderLayout.CENTER);
        this.leftPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIColor.PRIMARY_COLOR));

        // right
        this.rightPanel.setOpaque(false);
        this.rightPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, UIColor.PRIMARY_COLOR));

        this.comicsList.setCellRenderer(new ComicsListRenderer());
        this.comicsList.setVisibleRowCount(-1);
        this.comicsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        this.comicsListScrollPane.setBorder(BorderFactory.createEmptyBorder());

        this.comicsListScrollPaneContainer.add(this.comicsListScrollPane);

        GroupLayout rightPanelGroupLayout = new GroupLayout(this.rightPanel);
        this.rightPanel.setLayout(rightPanelGroupLayout);

        rightPanelGroupLayout
                .setHorizontalGroup(
                        rightPanelGroupLayout.createParallelGroup()
                                .addGroup(
                                        rightPanelGroupLayout.createSequentialGroup()
                                                .addGap(10)
                                                .addComponent(this.editCollection)
                                                .addGap(10)
                                                .addComponent(this.deleteCollection)
                                )
                                .addComponent(this.comicsListScrollPaneContainer)

                );


        rightPanelGroupLayout
                .setVerticalGroup(
                        rightPanelGroupLayout.createSequentialGroup()
                                .addGap(10)
                                .addGroup(
                                        rightPanelGroupLayout.createParallelGroup()
                                                .addComponent(this.editCollection)
                                                .addComponent(this.deleteCollection)
                                )
                                .addGap(10)
                                .addComponent(this.comicsListScrollPaneContainer)
                );

        this.setLayout(this.selGroupLayout);
        this.selGroupLayout.setHonorsVisibility(true);
        this.selGroupLayout
                .setHorizontalGroup(
                        this.selGroupLayout.createParallelGroup()
                                .addGap(10)
                                .addComponent(this.createCollectionsButton)
                                .addGroup(
                                        this.selGroupLayout.createSequentialGroup()
                                                .addComponent(this.leftPanel)
                                                .addComponent(this.rightPanel)
                                )
                                .addComponent(this.emptyStatePanel)
                );

        this.selGroupLayout
                .setVerticalGroup(
                        this.selGroupLayout.createSequentialGroup()
                                .addGap(10)
                                .addComponent(this.createCollectionsButton)
                                .addGap(10)
                                .addGroup(
                                        this.selGroupLayout.createParallelGroup()
                                                .addComponent(this.leftPanel)
                                                .addComponent(this.rightPanel)
                                )
                                .addComponent(this.emptyStatePanel)
                );

        this.buildEditCollectionDialog();
    }

    private void buildEditCollectionDialog() {
        // edit collection dialog text field
        this.editCollectionDialogTextField.setPreferredSize(new Dimension(300, 25));


        // edit collection dialog text area
        this.editCollectionDialogTextArea.setRows(8);
        this.editCollectionDialogTextArea.setMaximumSize(new Dimension(300, 300));

        // save button
        this.editCollectionDialogSaveButton.addActionListener(e -> {
            User user = UserAuthenticationModel.getUser();
            if (user != null) {
                if (this.createNewCollection) {
                    // insert into CollectionTable
                    CollectionsTable.insertCollection(this.editCollectionDialogTextField.getText(),
                            this.editCollectionDialogTextArea.getText(),
                            user.getId());
                    // refresh
                    this.setList();
                } else {
                    // update in database
                    if (this.currentCollectionsRow != null) {
                        CollectionsTable.updateCollection(this.currentCollectionsRow.getCollectionId(), user.getId(),
                                this.editCollectionDialogTextField.getText(), this.editCollectionDialogTextArea.getText());
                        // update in list model
                        this.currentCollectionsRow.setTitle(this.editCollectionDialogTextField.getText());
                        this.currentCollectionsRow.setDescription(this.editCollectionDialogTextArea.getText());
                        this.leftPanel.repaint();
                    }
                }

                this.editCollectionDialog.dispose();

            }
        });

        // cancel button
        this.editCollectionDialogCancelButton.addActionListener(e -> this.editCollectionDialog.dispose());

        // dialog
        JPanel mainPanel = (JPanel) this.editCollectionDialog.getContentPane();
        mainPanel.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
        GroupLayout editCollectionDialogLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(editCollectionDialogLayout);

        editCollectionDialogLayout.setAutoCreateContainerGaps(true);
        editCollectionDialogLayout.setAutoCreateGaps(true);


        editCollectionDialogLayout
                .setHorizontalGroup(
                        editCollectionDialogLayout.createParallelGroup()
                                .addComponent(this.editCollectionDialogTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(this.editCollectionDialogTextArea)
                                .addGroup(GroupLayout.Alignment.TRAILING,
                                        editCollectionDialogLayout.createSequentialGroup()
                                                .addComponent(this.editCollectionDialogSaveButton)
                                                .addComponent(this.editCollectionDialogCancelButton)
                                )
                );

        editCollectionDialogLayout
                .setVerticalGroup(
                        editCollectionDialogLayout.createSequentialGroup()
                                .addComponent(this.editCollectionDialogTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(this.editCollectionDialogTextArea)
                                .addGroup(
                                        editCollectionDialogLayout.createParallelGroup()
                                                .addComponent(this.editCollectionDialogSaveButton)
                                                .addComponent(this.editCollectionDialogCancelButton)
                                )
                );


    }

    private void createCollectionsButtonActionsListener() {
        this.createCollectionsButton.addActionListener(e -> {
            if (UserAuthenticationModel.isAuthencated()) {
                // set create new collection to true as we need this to handle edit/new
                this.createNewCollection = true;
                this.editCollectionDialogTextField.setText("");
                this.editCollectionDialogTextArea.setText("");
                this.editCollectionDialog.customSetVisible();
            }
        });
    }

    private void selectCollectionListener() {
        this.collectionsList.addListSelectionListener(this::valueChanged);
        this.comicsList.addMouseListener(new MouseAdapter() {
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
    }

    private void comicListContextMenu() {
        // vew pop up menu
        final JMenuItem viewMenuItem = new JMenuItem("View");
        viewMenuItem.addActionListener(e -> {
            if (UserAuthenticationModel.isAuthencated() && controller != null && this.comicsList.getSelectedValue() != null) {
                controller.showComic(this.comicsList.getSelectedValue());
                controller.gotoSearchView();
            }
        });

        // remove
        final JMenuItem removeMenuItem = new JMenuItem("Remove");
        removeMenuItem.addActionListener(e -> {
            if (UserAuthenticationModel.isAuthencated() && controller != null && this.comicsList.getSelectedValue() != null) {
                User user = UserAuthenticationModel.getUser();
                ElementsAssociation.removeCollection(Objects.requireNonNull(user).getId(), this.currentCollectionsRow.getCollectionId(),
                        this.comicsList.getSelectedValue().getId());
                this.listModel.remove(this.comicsList.getSelectedIndex());
            }
        });

        this.comicListPopupMenu.add(viewMenuItem);
        this.comicListPopupMenu.add(removeMenuItem);
        this.comicsList.setComponentPopupMenu(this.comicListPopupMenu);
    }

    private void deleteButtonActionListener() {
        this.deleteCollection.addActionListener(e -> {
            User user = UserAuthenticationModel.getUser();
            if (user != null && this.currentCollectionsRow != null) {
                CollectionsTable.removeCollection(this.currentCollectionsRow.getCollectionId(), user.getId());
                this.setList();
            }
        });
    }

    private void editCollectionActionListener() {
        this.editCollection.addActionListener(e -> {
            if (UserAuthenticationModel.isAuthencated() && this.currentCollectionsRow != null) {
                // set create new collection to false as we need this to handle edit/new
                this.createNewCollection = false;
                this.editCollectionDialogTextField.setText(this.currentCollectionsRow.getTitle());
                this.editCollectionDialogTextArea.setText(this.currentCollectionsRow.getDescription());
                this.editCollectionDialog.customSetVisible();
            }
        });
    }

    private void setCurrentCollectionsRow(CollectionsRow currentCollectionsRow) {
        this.currentCollectionsRow = currentCollectionsRow;
    }

    public DefaultListModel<CollectionsRow> getCollectionsListModel() {
        return collectionsListModel;
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
        if (arg != null) {
            this.setList();
        } else {
            this.collectionsListModel.clear();
        }
    }

    private void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            this.setCurrentCollectionsRow(this.collectionsList.getSelectedValue());
            this.refreshComicList();
        }
    }

    public void refreshComicList() {
        this.listModel.clear();
        if (this.collectionsList.getSelectedValue() != null) {
            List<ElementAssociationRow> elementAssociationRows = ElementsAssociation.
                    findComicsByCollection(Objects.requireNonNull(UserAuthenticationModel.getUser()).getId(),
                            this.collectionsList.getSelectedValue().getCollectionId());
            fillList(elementAssociationRows, listModel);
        }
    }
}
