package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Comic;

import javax.swing.*;
import java.awt.*;

public class ComicsListRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {

    private TemplatePreviewListRenderer renderer;


    public ComicsListRenderer() {
        super();
        renderer = new TemplatePreviewListRenderer() {
            @Override
            protected void fillCardData() {
                Comic comic = (Comic) this.data;
                this.cardTitle.setText(comic.getTitle().substring(0, 4) + "..." + comic.getIssueNumber());
            }

            @Override
            public Comic getData() {
                return (Comic) this.data;
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
