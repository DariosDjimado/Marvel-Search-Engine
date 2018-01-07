package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;

import javax.swing.*;

class OwnButton extends ToggleStateButton {
    private static final ImageIcon iconSelected = new ImageIcon(ComicsSearchListRenderer.class.getResource("book_add.png"));
    private static final ImageIcon iconUnselected = new ImageIcon(ComicsSearchListRenderer.class.getResource("book_remove.png"));

    OwnButton() {
        super(iconSelected, iconUnselected);
        this.setIcon(iconUnselected);
    }

    @Override
    void updateTooltipText() {
        if (this.isState()) {
            this.setToolTipText("Remove from library");
        } else {
            this.setToolTipText("Add to library");
        }
    }

    @Override
    protected boolean updateState(ElementAssociationRow row) { return row.isOwned(); }
}
