package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.authentication.User;
import fr.tse.fise2.heapoverflow.database.CollectionsRow;
import fr.tse.fise2.heapoverflow.database.CommentHandler;
import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;
import fr.tse.fise2.heapoverflow.database.ElementsAssociation;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelElement;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * @author Darios DJIMADO
 */
public class ReactivePanel extends JPanel {
    private final OwnButtonView ownedButtonView;
    private final LibraryButton collectionButton;
    private final ReadButtonView readButtonView;
    private final GradesPanelView gradesPanelView;
    private final FavoriteButtonView favoriteButtonView;
    private final CommentButtonView commentButtonView;
    private final JTextArea commentTextArea;
    private final JButton saveCommentButton;
    private final CustomScrollPane commentTextAreaScrollPane;
    private final CustomScrollPane collectionsScrollPane;
    private final JList<CollectionsRow> collectionsRowJList;
    private final CustomDialog collectionsDialog;
    private final JButton confirmCollectionSelectedButton;
    private final DefaultListModel<ElementAssociationRow> commentListModel;
    private final JPanel commentContainer;


    ReactivePanel() {
        this.ownedButtonView = new OwnButtonView();
        this.collectionButton = new LibraryButton();
        this.favoriteButtonView = new FavoriteButtonView();
        this.readButtonView = new ReadButtonView();
        this.gradesPanelView = new GradesPanelView();
        this.commentTextArea = new JTextArea(2, 1);
        this.commentButtonView = new CommentButtonView(this.commentTextArea);

        // subscribe comment button to user authentication model

        UserAuthenticationModel.getInstance().addObserver(this.commentButtonView);
        this.saveCommentButton = new DefaultButton("save");
        this.saveCommentButton.setVisible(false);

        this.commentTextArea.setText("no comment");
        this.commentTextArea.setDisabledTextColor(Color.GRAY);
        this.commentTextArea.setEnabled(false);
        this.commentTextArea.setLineWrap(true);
        this.commentTextArea.setWrapStyleWord(true);

        this.commentTextAreaScrollPane = new CustomScrollPane(this.commentTextArea);
        this.commentTextAreaScrollPane.setBorder(BorderFactory.createEmptyBorder());


        this.collectionsRowJList = new JList<>(CollectionsView.getInstance().getCollectionsListModel());
        this.collectionsRowJList.setCellRenderer(new CollectionsListRenderer());

        this.collectionsScrollPane = new CustomScrollPane(this.collectionsRowJList,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.collectionsDialog = new CustomDialog(null, "Select a collection");

        this.confirmCollectionSelectedButton = new PrimaryButton("Ok");


        this.commentListModel = new DefaultListModel<>();
        JList<ElementAssociationRow> commentList = new JList<>(this.commentListModel);
        CustomScrollPane commentScrollPane = new CustomScrollPane(commentList,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.commentContainer = new JPanel(new BorderLayout());
        this.commentContainer.setPreferredSize(new Dimension(this.commentContainer.getWidth(), 60));
        this.commentContainer.add(commentScrollPane, BorderLayout.CENTER);
        commentScrollPane.setBorder(BorderFactory.createEmptyBorder());

        commentList.setCellRenderer(new CommentListRender());


        this.initComponent();
        this.commentButtonsActionListener();
    }

    private void initComponent() {
        GroupLayout groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);
        this.setOpaque(false);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(
                                groupLayout.createSequentialGroup()
                                        .addComponent(this.collectionButton)
                                        .addComponent(this.ownedButtonView)
                                        .addComponent(this.favoriteButtonView)
                                        .addComponent(this.readButtonView)
                                        .addComponent(this.gradesPanelView, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        )
                        .addGroup(GroupLayout.Alignment.LEADING,
                                groupLayout.createSequentialGroup()
                                        .addComponent(this.commentButtonView)
                                        .addComponent(this.saveCommentButton)
                        )
                        .addComponent(this.commentTextAreaScrollPane)
                        .addComponent(this.commentContainer)
        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(
                                groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(this.collectionButton)
                                        .addComponent(this.ownedButtonView)
                                        .addComponent(this.favoriteButtonView)
                                        .addComponent(this.readButtonView)
                                        .addComponent(this.gradesPanelView)
                        )
                        .addGroup(
                                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(this.commentButtonView)
                                        .addComponent(this.saveCommentButton)
                        )
                        .addComponent(this.commentTextAreaScrollPane)
                        .addComponent(this.commentContainer, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)

        );
        this.createCollectionDialog();
        this.confirmCollectionSelectedButtonActionListener();
    }

    private void confirmCollectionSelectedButtonActionListener() {
        this.confirmCollectionSelectedButton.addActionListener(e -> {
            if (UserAuthenticationModel.isAuthencated()) {
                User user = UserAuthenticationModel.getUser();
                ElementsAssociation.updateCollectionCreateAsNeeded(this.collectionButton.getComic().getId(),
                        this.collectionButton.getComic().getTitle(),
                        Objects.requireNonNull(user).getId(),
                        this.collectionsRowJList.getSelectedValue().getCollectionId(),
                        MarvelElement.COMIC);
                this.collectionsDialog.dispose();
                CollectionsView.getInstance().refreshComicList();
            }
        });
    }


    private void commentButtonsActionListener() {
        this.commentButtonView.addActionListener(e -> {
            User user = UserAuthenticationModel.getUser();

            if (user != null) {
                this.commentTextArea.setEnabled(true);
                this.saveCommentButton.setVisible(true);
                this.commentTextAreaScrollPane.setBorder(BorderFactory.createLineBorder(UIColor.HEADER_SHADOW_COLOR));
            }
        });

        this.saveCommentButton.addActionListener(e -> {
            User user = UserAuthenticationModel.getUser();
            if (user != null) {
                this.commentTextArea.setEnabled(false);
                this.saveCommentButton.setVisible(false);
                this.commentTextAreaScrollPane.setBorder(BorderFactory.createEmptyBorder());
                ElementsAssociation.updateCommentCreateAsNeeded(this.favoriteButtonView.getId(),
                        this.favoriteButtonView.getElementName(),
                        user.getId(),
                        this.commentTextArea.getText().trim(),
                        this.favoriteButtonView.getType());
            }
        });


        this.collectionButton.addActionListener(e -> {
            if (UserAuthenticationModel.isAuthencated() && this.collectionButton.getComic() != null) {
                this.collectionsDialog.customSetVisible();
            }
        });
    }

    private void createCollectionDialog() {
        this.collectionsDialog.setVisible(false);
        JPanel panel = (JPanel) this.collectionsDialog.getContentPane();
        this.collectionsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
        panel.setLayout(new BorderLayout());

        GroupLayout groupLayout = new GroupLayout(panel);
        panel.setLayout(groupLayout);

        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);

        groupLayout
                .setHorizontalGroup(
                        groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(this.collectionsScrollPane)
                                .addComponent(this.confirmCollectionSelectedButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                );

        groupLayout
                .setVerticalGroup(
                        groupLayout
                                .createSequentialGroup()
                                .addComponent(this.collectionsScrollPane)
                                .addComponent(this.confirmCollectionSelectedButton)
                );


        this.collectionsDialog.setMaximumSize(new Dimension(300, 350));
        this.collectionsDialog.setPreferredSize(new Dimension(300, 350));
        this.collectionsDialog.setMinimumSize(new Dimension(300, 350));
    }


    public JButton getCollectionButton() {
        return collectionButton;
    }

    public OwnButtonView getOwnedButtonView() {
        return ownedButtonView;
    }

    public ReadButtonView getReadButtonView() {
        return readButtonView;
    }

    public GradesPanelView getGradesPanelView() {
        return gradesPanelView;
    }

    public FavoriteButtonView getFavoriteButtonView() {
        return favoriteButtonView;
    }

    public CommentButtonView getCommentButtonView() {
        return commentButtonView;
    }

    public JTextArea getCommentTextArea() {
        return commentTextArea;
    }

    public void setComic(Comic comic) {
        User user = UserAuthenticationModel.getUser();
        ElementAssociationRow row = null;

        if (user != null) {
            row = ElementsAssociation.findElement(user.getId(), comic.getId(), MarvelElement.COMIC);
            if (row != null) {
                this.commentTextArea.setText(row.getComment().isEmpty() ? "no comment" : row.getComment());
            }
        }


        this.commentListModel.clear();
        for (ElementAssociationRow elementAssociationRow : CommentHandler.findCommentsByComic(comic.getId())) {
            this.commentListModel.addElement(elementAssociationRow);
        }


        this.favoriteButtonView.setComic(comic, row);
        this.readButtonView.setComic(comic, row);
        this.gradesPanelView.setComic(comic, row);
        this.commentButtonView.setComic(comic, row);
        this.collectionButton.setComic(comic);
        this.ownedButtonView.setEnabled(true);
        this.ownedButtonView.setComic(comic, row);
    }

    public void setCharacter(Character character) {
        User user = UserAuthenticationModel.getUser();
        ElementAssociationRow row = null;

        if (user != null) {
            row = ElementsAssociation.findElement(user.getId(), character.getId(), MarvelElement.CHARACTER);
            if (row != null) {
                this.commentTextArea.setText(row.getComment().isEmpty() ? "no comment" : row.getComment());
            }
        }

        this.favoriteButtonView.setCharacter(character, row);
        this.readButtonView.setCharacter(character, row);
        this.gradesPanelView.setCharacter(character, row);
        this.commentButtonView.setCharacter(character, row);
        this.ownedButtonView.setEnabled(false);
    }
}
