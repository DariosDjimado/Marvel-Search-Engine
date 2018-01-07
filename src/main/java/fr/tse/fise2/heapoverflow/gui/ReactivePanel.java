package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.authentication.User;
import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;
import fr.tse.fise2.heapoverflow.database.ElementsAssociation;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelElement;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Darios DJIMADO
 */
public class ReactivePanel extends JPanel {
    private final OwnButtonView ownedButtonView;
    private final ReadButtonView readButtonView;
    private final GradesPanelView gradesPanelView;
    private final FavoriteButtonView favoriteButtonView;
    private final CommentButtonView commentButtonView;
    private final JTextArea commentTextArea;
    private final JButton saveCommentButton;
    private final CustomScrollPane scrollPane;


    ReactivePanel() {
        this.ownedButtonView = new OwnButtonView();
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

        this.scrollPane = new CustomScrollPane(this.commentTextArea);
        this.scrollPane.setBorder(BorderFactory.createEmptyBorder());

        GroupLayout groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);
        this.setOpaque(false);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(GroupLayout.Alignment.LEADING,
                                groupLayout.createSequentialGroup()
                                        .addComponent(this.commentButtonView)
                                        .addComponent(this.saveCommentButton)
                        )
                        .addComponent(this.scrollPane)
                        .addGroup(
                                groupLayout.createSequentialGroup()
                                        .addComponent(this.ownedButtonView)
                                        .addComponent(this.favoriteButtonView)
                                        .addComponent(this.readButtonView)
                                        .addComponent(this.gradesPanelView, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        )
        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(
                                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(this.commentButtonView)
                                        .addComponent(this.saveCommentButton)
                        )
                        .addComponent(this.scrollPane)
                        .addGroup(
                                groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(this.ownedButtonView)
                                        .addComponent(this.favoriteButtonView)
                                        .addComponent(this.readButtonView)
                                        .addComponent(this.gradesPanelView)
                        )
        );
        this.commentButtonsActionListener();
    }


    private void commentButtonsActionListener() {
        this.commentButtonView.addActionListener(e -> {
            User user = UserAuthenticationModel.getUser();

            if (user != null) {
                this.commentTextArea.setEnabled(true);
                this.saveCommentButton.setVisible(true);
                this.scrollPane.setBorder(BorderFactory.createLineBorder(UIColor.HEADER_SHADOW_COLOR));
            }
        });

        this.saveCommentButton.addActionListener(e -> {
            User user = UserAuthenticationModel.getUser();
            if (user != null) {
                this.commentTextArea.setEnabled(false);
                this.saveCommentButton.setVisible(false);
                this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
                ElementsAssociation.updateCommentCreateAsNeeded(this.favoriteButtonView.getId(),
                        this.favoriteButtonView.getElementName(),
                        user.getId(),
                        this.commentTextArea.getText().trim(),
                        this.favoriteButtonView.getType());
            }
        });
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

        this.favoriteButtonView.setComic(comic, row);
        this.readButtonView.setComic(comic, row);
        this.gradesPanelView.setComic(comic, row);
        this.commentButtonView.setComic(comic, row);
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
