package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReadButton extends ButtonFormat {
    private static final ImageIcon readIcon = new ImageIcon(ComicsSearchListRenderer.class.getResource("view_add.png"));
    private static final ImageIcon unreadIcon = new ImageIcon(ComicsSearchListRenderer.class.getResource("view_remove.png"));


    public ReadButton() {
        this.setIcon(unreadIcon);
        this.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(readIcon);
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(unreadIcon);
            }
        });
    }
}
