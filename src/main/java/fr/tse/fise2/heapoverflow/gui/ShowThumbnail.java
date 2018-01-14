package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.CacheImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

/**
 * Jpanel extension to draw comic thumbnail on datashow window
 *
 * @author Théo Basty
 */
class ShowThumbnail extends JPanel implements Observer {
    /**
     * Image drawn on the panel
     */
    private BufferedImage image_;

    /**
     * Constructor of the panel
     *
     * @param image The image to be drawn
     */
    public ShowThumbnail(BufferedImage image) {
        this.image_ = image;
    }

    /**
     * Function called to draw the panel on the window
     *
     * @param g awt Graphics Object to draw
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

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o.getClass() == CacheImage.class) {
            if (arg != null) {
                this.setImage_((BufferedImage) arg);
            }
        }
    }
}
