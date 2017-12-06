package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.marvelapi.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Cache image to avoid making future requests
 *
 * @author Darios DJIMADO
 */
public class CacheImage extends Thread {

    private final BufferedImage bufferedImage;
    private final Image image;
    private final String tmpDir;

    /**
     * @param bufferedImage image to save
     * @param image         Marvel Image. We get some information from Marvel image itself
     * @param tmpDir        Where to save image
     */
    public CacheImage(BufferedImage bufferedImage, Image image, String tmpDir) {
        this.bufferedImage = bufferedImage;
        this.image = image;
        this.tmpDir = tmpDir;
    }

    /**
     * Runs the thread.
     */
    @Override
    public void run() {
        try {
            ImageIO.write(this.bufferedImage, image.getExtension(), new java.io.File(this.tmpDir + image.getPath().substring(image.getPath().lastIndexOf('/') + 1) + '.' + image.getExtension()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
