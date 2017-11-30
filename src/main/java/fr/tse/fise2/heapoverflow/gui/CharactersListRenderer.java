package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Character;

import javax.swing.*;
import java.awt.*;

public class CharactersListRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {

    private TemplatePreviewListRenderer renderer;


    public CharactersListRenderer(final JList list) {
        super();
        renderer = new TemplatePreviewListRenderer() {
            @Override
            protected void fillCardData() {
                this.cardTitle.setText(((Character) this.data).getName().substring(0, 10));
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
