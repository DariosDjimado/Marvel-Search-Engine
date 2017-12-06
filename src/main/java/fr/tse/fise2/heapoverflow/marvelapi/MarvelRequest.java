package fr.tse.fise2.heapoverflow.marvelapi;

import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import fr.tse.fise2.heapoverflow.events.RequestListener;
import fr.tse.fise2.heapoverflow.main.CacheImage;
import fr.tse.fise2.heapoverflow.main.Controller;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Makes request to Marvel API
 *
 * @author Darios DJIMADO
 */
public final class MarvelRequest extends UrlBuilder {
    private static final Logger LOGGER = Logger.getLogger(MarvelRequest.class);
    // string that is returned when the rateLimit is reached
    private static String requestCanceled = "More than" + Authentication.getRateLimit() + "request";
    private static Set<RequestListener> requestListeners = new HashSet<>();
    private final OkHttpClient client;

    public MarvelRequest() {
        this(Controller.getController());
    }

    public MarvelRequest(@NotNull Controller controller) {
        this.client = new OkHttpClient
                .Builder()
                .cache(controller.getUrlsCache())
                .addInterceptor(new MarvelRequestInterceptor(controller))
                .build();
    }

    /**
     * Converts a json string to CharacterDataWrapper
     *
     * @param json the string that we need to deserialize
     * @return CharacterDataWrapper object or null if the rateLimit is reached
     */
    public static CharacterDataWrapper deserializeCharacters(String json) {
        if (json != null) {
            if (json.equals(requestCanceled)) {
                return null;
            } else {
                Gson gson = new Gson();
                return gson.fromJson(json, CharacterDataWrapper.class);
            }
        } else {
            return null;
        }

    }

    /**
     * Converts a json string to ComicDataWrapper.
     *
     * @param json the string that we need to deserialize
     * @return ComicDataWrapper object or null if the rateLimit is reached
     */
    public static ComicDataWrapper deserializeComics(String json) {
        if (json != null) {
            if (json.equals(requestCanceled)) {
                return null;
            } else {
                Gson gson = new Gson();
                return gson.fromJson(json, ComicDataWrapper.class);
            }
        } else {
            return null;
        }

    }

    /**
     * Converts a json string to ComicDataWrapper.
     *
     * @param json the string that we need to deserialize
     * @return ComicDataWrapper object or null if the rateLimit is reached
     */
    public static CreatorDataWrapper deserializeCreators(String json) {
        if (json.equals(requestCanceled)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(json, CreatorDataWrapper.class);
        }
    }

    /**
     * Converts a json string to SeriesDataWrapper.
     *
     * @param json the string that we need to deserialize
     * @return SeriesDataWrapper object or null if the rateLimit is reached
     */
    public static SeriesDataWrapper deserializeSeries(String json) {
        if (json.equals(requestCanceled)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(json, SeriesDataWrapper.class);
        }
    }

    /**
     * @param image        Marvel Image
     * @param imageVariant Marvel Image Variant
     * @param tmpPath      temporary folder where images will be stored
     * @return BufferedImage
     * @throws IOException cannot read the image
     */
    public static BufferedImage getImage(Image image, ImageVariant imageVariant, String tmpPath) throws IOException {
        File imageTmp = new File(tmpPath + image.getPath().substring(image.getPath().lastIndexOf('/') + 1) + '.' + image.getExtension());
        if (imageTmp.isFile()) {
            Controller.getLoggerObserver().onInfo(LOGGER, "reading file from disk");
            return ImageIO.read(imageTmp);
        } else {
            for (RequestListener requestListener : requestListeners) {
                requestListener.startLoading(image.getPath());
            }

            BufferedImage bufferedReader = ImageIO.read(imageUrl(image, imageVariant));


            for (RequestListener requestListener : requestListeners) {
                requestListener.endLoading(image.getPath());
            }

            Thread cacheImage = new CacheImage(bufferedReader, image, tmpPath);
            cacheImage.run();

            return bufferedReader;
        }
    }

    /**
     * This method takes the required partialUrl and makes a request from the entire url and returns the response.
     *
     * @param partialUrl the part of the url that will be concatenate with the keys and the timestamp
     * @return String. If the rateLimit is reached this method will return the specified string <i>requestCanceled</i>
     * @throws IOException on request execution
     */
    public String getData(String partialUrl) throws IOException {
        if (Authentication.getNumberOfRequest() < Authentication.getRateLimit()) {
            for (RequestListener requestListener : requestListeners) {
                requestListener.startLoading(partialUrl);
            }

            Request request = new Request.Builder()
                    .url(appendBaseUrl(partialUrl))
                    .build();
            try (Response response = client.newCall(request).execute()) {
                Authentication.setNumberOfRequest(Authentication.getNumberOfRequest() + 1);

                for (RequestListener requestListener : requestListeners) {
                    requestListener.endLoading(partialUrl);
                }
                return response.body().string();
            }
        } else {
            return requestCanceled;
        }
    }

    /**
     * Add RequestListener
     *
     * @param listener listener to request
     */
    public void addRequestListener(RequestListener listener) {
        requestListeners.add(listener);
    }

    /**
     * Remove specified RequestListener
     *
     * @param listener a reference to listener which has to be removed
     */
    public void removeRequestListener(RequestListener listener) {
        requestListeners.remove(listener);
    }

}
