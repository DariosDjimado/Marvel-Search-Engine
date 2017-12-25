package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LibraryButton extends ButtonFormat {

    private static final ImageIcon libraryIcon = new ImageIcon(ComicsSearchListRenderer.class.getResource("library_add.png"));
    private static final ImageIcon libraryDefaultIcon = new ImageIcon(ComicsSearchListRenderer.class.getResource("library_remove.png"));

    public LibraryButton() {
        this.setIcon(libraryIcon);

        this.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(libraryIcon);
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(libraryDefaultIcon);
            }
        });

    }
}
