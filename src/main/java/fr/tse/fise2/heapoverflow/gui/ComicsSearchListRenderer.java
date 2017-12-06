package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Comic;

import javax.swing.*;
import java.awt.*;

public class ComicsSearchListRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {
    private static final ImageIcon icon = new ImageIcon(ComicsSearchListRenderer.class.getResource("c_for_comic.png"));
    private TemplateSearchListRenderer renderer;

    public ComicsSearchListRenderer() {
        super();
        this.renderer = new TemplateSearchListRenderer(icon) {
            @Override
            protected String getTitle(Object o) {
                return ((Comic) o).getTitle();
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
