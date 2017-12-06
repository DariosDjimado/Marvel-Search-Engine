package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Character;

import javax.swing.*;
import java.awt.*;

public class CharactersListRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {

    private TemplatePreviewListRenderer renderer;


    public CharactersListRenderer() {
        super();
        renderer = new TemplatePreviewListRenderer() {
            @Override
            protected void fillCardData() {
                Character character = (Character) this.data;
                this.cardTitle.setText(character.getName().substring(0, 4) + "..." + character.getId());
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
