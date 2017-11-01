package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Extending JPanel for Level 1 title display.
 * @author Théo Basty
 * @version 0.1
 */
public class Title1Panel extends JPanel {
    /**
     * Text of the title
     */
    String text_;

    /**
     * Class constructor
     * @param text
     *      Text of the title
     */
    public Title1Panel(String text) {
        text_=text;
    }

    /**
     * function called to redraw window content
     * @param g
     *      Graphics object from AWT
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setFont(Fonts.title1);
        g.drawString(text_, 10,30);
    }
}
