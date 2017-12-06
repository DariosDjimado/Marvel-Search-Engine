package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Character;

import javax.swing.*;
import java.awt.*;

public class CharactersSerachListRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {
    private static final ImageIcon icon = new ImageIcon(ComicsSearchListRenderer.class.getResource("h_for_hero.png"));
    private TemplateSearchListRenderer renderer;

    public CharactersSerachListRenderer() {
        super();
        this.renderer = new TemplateSearchListRenderer(icon) {
            @Override
            protected String getTitle(Object o) {
                return ((Character) o).getName();
            }
        };

    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        this.renderer.setSelected(isSelected);
        this.renderer.setData(value);
        return renderer;
    }
}
