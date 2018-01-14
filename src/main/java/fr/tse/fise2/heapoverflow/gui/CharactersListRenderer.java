package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Character;

import javax.swing.*;
import java.awt.*;

class CharactersListRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {

    private TemplatePreviewListRenderer renderer;


    CharactersListRenderer() {
        super();
        renderer = new TemplatePreviewListRenderer() {
            @Override
            protected void fillCardData() {
                Character character = (Character) this.data;
                this.cardTitle.setText("<html><body style='width: 110px'>");
                this.cardTitle.setText(this.cardTitle.getText() + character.getName());
            }


            @Override
            public Character getData() {
                return (Character) this.data;
            }
        };


    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        renderer.setSelected(isSelected);
        renderer.setData(value);
        return renderer;
    }
}
