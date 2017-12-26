package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;

import javax.swing.*;

class FavoriteButton extends ToggleStateButton {
    private static final ImageIcon iconSelected = new ImageIcon(ComicsSearchListRenderer.class.getResource("favorite_add.png"));
    private static final ImageIcon iconUnselected = new ImageIcon(ComicsSearchListRenderer.class.getResource("favorite_remove.png"));

    FavoriteButton() {
        super(iconSelected, iconUnselected);
        this.setIcon(iconUnselected);
    }

    @Override
    void updateTooltipText() {
        if (this.isState()) {
            this.setToolTipText("Remove favorite");
        } else {
            this.setToolTipText("Add favorite");
        }
    }

    @Override
    protected boolean updateState(ElementAssociationRow row) {
        return row.isFavorite();
    }
}
