package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CommentButton extends ToggleStateButton {
    private static final ImageIcon commentAddIcon = new ImageIcon(ComicsSearchListRenderer.class.getResource("comment_add.png"));
    private static final ImageIcon commentRemoveIcon = new ImageIcon(ComicsSearchListRenderer.class.getResource("comment_remove.png"));


    CommentButton() {
        super(commentAddIcon, commentRemoveIcon);
        this.setIcon(commentRemoveIcon);
        this.initConfig();
    }

    private void initConfig() {
        this.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                updateIcon(true);
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseExited(MouseEvent e) {
                updateIcon(false);
            }
        });
    }

    @Override
    void updateTooltipText() {
        this.setToolTipText("Edit comment");
    }

    @Override
    protected boolean updateState(ElementAssociationRow row) {
        return row.isRead();
    }
}

