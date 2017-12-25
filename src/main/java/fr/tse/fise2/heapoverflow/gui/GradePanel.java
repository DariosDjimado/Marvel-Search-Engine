package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GradePanel extends JPanel {

    private static final ImageIcon readIcon = new ImageIcon(ComicsSearchListRenderer.class.getResource("star_add.png"));
    private static final ImageIcon unreadIcon = new ImageIcon(ComicsSearchListRenderer.class.getResource("star_remove.png"));


    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public GradePanel() {



        this.setOpaque(false);
        for (int i = 0; i < 5; i++) {
            this.add(new GradeButton(i));
        }

    }


    private class GradeButton extends ButtonFormat {


        private final int grade;

        public GradeButton(int grade) {
            super();
            this.grade = grade;

            this.setFocusPainted(false);
            this.setBorderPainted(false);
            this.setContentAreaFilled(false);
            this.setOpaque(false);

            Dimension dimension = new Dimension(10,12);
            this.setMinimumSize(dimension);
            this.setPreferredSize(dimension);
            this.setMaximumSize(dimension);

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
}
