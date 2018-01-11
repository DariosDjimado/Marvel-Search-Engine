package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Comic;

import javax.swing.*;
import java.awt.*;

public class ComicsListRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {

    private TemplatePreviewListRenderer renderer;


    ComicsListRenderer() {
        super();
        renderer = new TemplatePreviewListRenderer() {
            @Override
            protected void fillCardData() {
                Comic comic = (Comic) this.data;
                this.cardTitle.setText("<html><body style='width: 110px'>");
                if(comic.getTitle().length() > 80){
                    this.cardTitle.setText(this.cardTitle.getText() + comic.getTitle().substring(0, 80) + "...");
                }
                else{
                    this.cardTitle.setText(this.cardTitle.getText() + comic.getTitle());
                }

                this.ownButton.setComic(comic);
                this.favoriteButton.setComic(comic);
                this.readButton.setComic(comic);
                this.gradesPanel.setComic(comic);

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
