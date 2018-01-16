package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;
import fr.tse.fise2.heapoverflow.database.UserRow;
import fr.tse.fise2.heapoverflow.database.UsersTable;

import javax.swing.*;
import java.awt.*;

/**
 * @author Darios DJIMADO
 */
public class CommentView extends JPanel {
    public static final String EMPTY_COMMENT = "no comment";

    private final CircleButton userAvatarButton;
    private final JTextArea commentArea;

    CommentView() {
        this.userAvatarButton = new CircleButton("");
        this.commentArea = new CustomTextArea(EMPTY_COMMENT);
        this.setOpaque(false);
        this.initComponent();
    }

    public void setElementAssociationRow(ElementAssociationRow elementAssociationRow) {
        UserRow userRow = UsersTable.findUserById(elementAssociationRow.getUserId());
        this.userAvatarButton.setText(userRow.getUsername().substring(0, 1));
        this.userAvatarButton.setToolTipText(userRow.getUsername());
        this.commentArea.setText(elementAssociationRow.getComment());
    }

    private void initComponent() {
        this.userAvatarButton.setBackground(UIColor.ACCENT_COLOR);
        this.commentArea.setBackground(UIColor.TEXT_FIELD_DISABLE_COLOR);
        this.commentArea.setBackground(Color.lightGray);
        this.commentArea.setMargin(new Insets(2, 5, 2, 2));

        GroupLayout groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);

        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(this.userAvatarButton)
                        .addComponent(this.commentArea)

        );

        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup()
                        .addComponent(this.userAvatarButton)
                        .addComponent(this.commentArea)
        );
    }
}
