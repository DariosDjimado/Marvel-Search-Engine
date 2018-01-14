package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;

import javax.swing.*;
import java.awt.*;

/**
 * @author Darios DJIMADO
 */
class CommentListRender implements ListCellRenderer<ElementAssociationRow> {
    private CommentView renderer;

    CommentListRender() {
        this.renderer = new CommentView();
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ElementAssociationRow> list, ElementAssociationRow value, int index, boolean isSelected, boolean cellHasFocus) {
        this.renderer.setElementAssociationRow(value);
        return renderer;
    }
}
