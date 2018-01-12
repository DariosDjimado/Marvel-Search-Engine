package fr.tse.fise2.heapoverflow.marvelapi;

import fr.tse.fise2.heapoverflow.main.AppErrorHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static fr.tse.fise2.heapoverflow.marvelapi.UrlBuilder.imageUrl;

/**
 * Caches image to avoid making future requests
 *
 * @author Darios DJIMADO
 */
public class CacheImage extends Observable implements Runnable {
    private static Set<String> DOWNLOADING_IMAGES_SET = ConcurrentHashMap.newKeySet();

    private final UrlBuilder.ImageVariant imageVariant;
    private final Image image;
    private final String tmpDir;
    private final Observer observer;

    /**
     * @param image        Marvel Image. We get some information from Marvel image itself
     * @param imageVariant Marvel Image Variant
     * @param tmpDir       Where to save image
     */
    public CacheImage(Image image, UrlBuilder.ImageVariant imageVariant, String tmpDir, Observer observer) {
        this.image = image;
        this.imageVariant = imageVariant;
        this.tmpDir = tmpDir;
        this.observer = observer;
    }

    /**
     * Runs the thread.
     */
    @Override
    public void run() {
        try {
            MarvelRequest.startLoading(image.getPath());
            if (!DOWNLOADING_IMAGES_SET.contains(image.getPath())) {
                DOWNLOADING_IMAGES_SET.add(image.getPath());

                File file = new File(this.tmpDir + UrlBuilder.imageCachedName(image));
                if (!file.isFile()) {
                    BufferedImage bufferedImage = ImageIO.read(imageUrl(image, imageVariant));
                    ImageIO.write(bufferedImage, image.getExtension(), file.getCanonicalFile());
                    // remove image from set after it's loaded
                    DOWNLOADING_IMAGES_SET.remove(image.getPath());
                    if (this.observer != null) {
                        this.observer.update(this, bufferedImage);
                    }
                }
            }
        } catch (IOException e) {
            AppErrorHandler.onError(e);
        } finally {
            MarvelRequest.endRequest(image.getPath());
        }
    }
}
