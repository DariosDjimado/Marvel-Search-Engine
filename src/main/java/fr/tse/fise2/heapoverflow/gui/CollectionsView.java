package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.database.CollectionsTable;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class CollectionsView extends JPanel {
    private static final ImageIcon addIcon = new ImageIcon(CollectionsView.class.getResource("addIcon.png"));
    private static final ImageIcon deleteIcon = new ImageIcon(CollectionsView.class.getResource("deleteIcon.png"));
    private static final ImageIcon editIcon = new ImageIcon(CollectionsView.class.getResource("editIcon.png"));

    private final JButton createCollectionsButton;
    private final JPanel leftPanel;
    private final JPanel rightPanel;

    private final JButton deleteCollection;
    private final JButton editCollection;
    private final JLabel collectionName;
    private final JTextArea collectionDescription;
    private final JList comicsList;
    private final DefaultListModel listModel;
    private final CustomScrollPane comicsListScrollPane;
    private final JList collectionsList;
    private final CustomScrollPane collectionsListScrollPane;
    private final DefaultListModel collectionsListModel;


    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public CollectionsView() {
        this.createCollectionsButton = new DecorateButtonFormat(addIcon);


        this.leftPanel = new JPanel(new BorderLayout());
        this.rightPanel = new JPanel();

        this.editCollection = new DecorateButtonFormat(editIcon);
        this.editCollection.setOpaque(true);

        this.deleteCollection = new DecorateButtonFormat(deleteIcon);


        this.collectionDescription = new JTextArea(5, 1);
        this.collectionName = new JLabel("collection's name");

        this.listModel = new DefaultListModel();
        this.comicsList = new JList(this.listModel);

        for (int i = 0; i < 100; i++) {
            this.listModel.addElement("listobject");
        }

        this.comicsListScrollPane = new CustomScrollPane(this.comicsList);

        this.collectionsListModel = new DefaultListModel();
        this.collectionsList = new JList(this.collectionsListModel);
        this.collectionsList.setCellRenderer(new CollectionsListRenderer());
        this.collectionsListScrollPane = new CustomScrollPane(this.collectionsList);

        for (int i = 0; i < 100; i++) {
            this.collectionsListModel.addElement("listobject");
        }


        this.initComponent();
        this.createCollectionsButtonActionsListener();
    }

    private void initComponent() {

        // self
        this.setBackground(UIColor.MAIN_BACKGROUND_COLOR);


        // create collection button
        this.createCollectionsButton.setOpaque(false);

        // left panel
        this.leftPanel.setOpaque(false);
        this.leftPanel.add(new JLabel("List of collections"), BorderLayout.NORTH);

        this.leftPanel.add(this.collectionsListScrollPane, BorderLayout.CENTER);

        this.leftPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, UIColor.PRIMARY_COLOR));

        // right
        this.rightPanel.setOpaque(false);
        this.rightPanel.setBorder(BorderFactory.createMatteBorder(2, 1, 0, 0, UIColor.PRIMARY_COLOR));

        Font font = this.collectionName.getFont();
        Font deriveFont = font.deriveFont(font.getStyle(), 27);

        this.collectionName.setFont(deriveFont);
        this.collectionDescription.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));


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

                                .addComponent(this.collectionName)
                                .addComponent(this.collectionDescription)
                                .addComponent(this.comicsListScrollPane)

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
                                .addComponent(this.collectionName)
                                .addGap(10)
                                .addComponent(this.collectionDescription)
                                .addGap(10)
                                .addComponent(this.comicsListScrollPane)
                );


        GroupLayout groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);


        groupLayout
                .setHorizontalGroup(
                        groupLayout.createParallelGroup()
                                .addGap(10)
                                .addComponent(this.createCollectionsButton)
                                .addGroup(
                                        groupLayout.createSequentialGroup()
                                                .addComponent(this.leftPanel)
                                                .addComponent(this.rightPanel)
                                )
                );

        groupLayout
                .setVerticalGroup(
                        groupLayout.createSequentialGroup()
                                .addGap(10)
                                .addComponent(this.createCollectionsButton)
                                .addGap(10)
                                .addGroup(
                                        groupLayout.createParallelGroup()
                                                .addComponent(this.leftPanel)
                                                .addComponent(this.rightPanel)
                                )
                );


    }

    private void createCollectionsButtonActionsListener() {
        this.createCollectionsButton.addActionListener(e -> {
            final JLabel titleLabel = new JLabel("Title");
            final JTextField textField = new JTextField();
            final JLabel descLabel = new JLabel("Description");
            final JTextArea descArea = new JTextArea(10, 1);

            int option = JOptionPane.showConfirmDialog(null, new Object[]{titleLabel, textField, descLabel, descArea},
                    "Collections", JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null);

            if (option == JOptionPane.YES_OPTION) {
                try {
                    CollectionsTable.insertCollection(textField.getText(), descArea.getText());

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
