package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;

import javax.swing.*;

public class ReadButton extends ToggleStateButton {
    private static final ImageIcon readIcon = new ImageIcon(ComicsSearchListRenderer.class.getResource("view_add.png"));
    private static final ImageIcon unreadIcon = new ImageIcon(ComicsSearchListRenderer.class.getResource("view_remove.png"));


    ReadButton() {
        super(readIcon, unreadIcon);
        this.setIcon(unreadIcon);
    }

    @Override
    void updateTooltipText() {
        if (this.isState()) {
            this.setToolTipText("Not read");
        } else {
            this.setToolTipText("Read");
        }
    }

    @Override
    protected boolean updateState(ElementAssociationRow row) {
        return row.isRead();
    }
}
