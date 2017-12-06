package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Jpanel extension to draw comic thumbnail on datashow window
 * @author Théo Basty
 */
public class ShowThumbnail extends JPanel {
    /**
     * Image drawn on the panel
     */
    BufferedImage image_;

    /**
     * Constructor of the panel
     * @param image
     *      The image to be drawn
     */
    public ShowThumbnail(BufferedImage image) {
        this.image_ = image;
    }

    /**
     * Function called to draw the panel on the window
     * @param g
     *      awt Graphics Object to draw
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(5, 5, 179, 263);
        g.setColor(Color.black);
        g.drawRect(5, 5, 179, 263);
        g.drawImage(image_, 11, 11, null);
    }

    public void setImage_(BufferedImage image_) {
        this.image_ = image_;
        repaint();
    }
}
