package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

public class CommentButtonView extends ToggleStateButton implements Observer {
    private static final ImageIcon commentAddIcon = new ImageIcon(ComicsSearchListRenderer.class.getResource("comment_add.png"));
    private static final ImageIcon commentRemoveIcon = new ImageIcon(ComicsSearchListRenderer.class.getResource("comment_remove.png"));

    private final JTextArea commentTextArea;

    CommentButtonView(JTextArea commentTextArea) {
        super(commentAddIcon, commentRemoveIcon);
        this.setIcon(commentRemoveIcon);
        this.commentTextArea = commentTextArea;
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
        this.commentTextArea.setText(row.getComment().isEmpty() ? "no comment" : row.getComment());
        return row.isRead();
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
        this.refresh();
    }
}

