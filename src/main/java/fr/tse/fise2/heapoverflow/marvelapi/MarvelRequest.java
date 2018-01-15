package fr.tse.fise2.heapoverflow.marvelapi;

import com.google.gson.Gson;
import fr.tse.fise2.heapoverflow.events.RequestListener;
import okhttp3.*;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Handles the requests to Marvel API
 *
 * @author Darios DJIMADO
 */
public final class MarvelRequest extends UrlBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarvelRequest.class);
    // string that is returned when the rateLimit is reached
    private static String requestCanceled = "More than" + Authentication.getRateLimit() + "request";
    private static Set<RequestListener> requestListeners = new HashSet<>();
    private static MarvelRequest instance;
    private final OkHttpClient client;


    private MarvelRequest() {
        Cache cache = new Cache(new File("CacheResponse.tmp"), 10 * 1024 * 1024);
        this.client = new OkHttpClient
                .Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(new MarvelRequestInterceptor())
                .build();
    }

    /**
     * Gets the current instanec
     *
     * @return instance of MarvelRequest
     */
    public static MarvelRequest getInstance() {
        if (instance == null) {
            instance = new MarvelRequest();
        }
        return instance;
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
        if (json != null) {
            if (json.equals(requestCanceled)) {
                return null;
            } else {
                Gson gson = new Gson();
                return gson.fromJson(json, CreatorDataWrapper.class);
            }
        } else {
            return null;
        }
    }

    /**
     * Converts a json string to SeriesDataWrapper.
     *
     * @param json the string that we need to deserialize
     * @return SeriesDataWrapper object or null if the rateLimit is reached
     */
    public static SeriesDataWrapper deserializeSeries(String json) {
        if (json != null) {
            if (json.equals(requestCanceled)) {
                return null;
            } else {
                Gson gson = new Gson();
                return gson.fromJson(json, SeriesDataWrapper.class);
            }
        } else {
            return null;
        }
    }

    /**
     * Converts a json string to EventsDataWrapper.
     *
     * @param json the string that we need to deserialize
     * @return SeriesDataWrapper object or null if the rateLimit is reached
     */
    public static EventsDataWrapper deserializeEvents(String json) {
        if (json != null) {
            if (json.equals(requestCanceled)) {
                return null;
            } else {
                Gson gson = new Gson();
                return gson.fromJson(json, EventsDataWrapper.class);
            }
        } else {
            return null;
        }
    }

    /**
     * Converts a json string to StoriesDataWrapper.
     *
     * @param json the string that we need to deserialize
     * @return SeriesDataWrapper object or null if the rateLimit is reached
     */
    public static StoriesDataWrapper deserializeStories(String json) {
        if (json != null) {
            if (json.equals(requestCanceled)) {
                return null;
            } else {
                Gson gson = new Gson();
                return gson.fromJson(json, StoriesDataWrapper.class);
            }
        } else {
            return null;
        }
    }

    /**
     * @param image        Marvel Image
     * @param imageVariant Marvel Image Variant
     * @param tmpPath      temporary folder where images will be stored
     * @param observer     if any it will update later, for images downloaded in thread
     * @return BufferedImage
     * @throws IOException cannot read the image
     */
    public static BufferedImage getImage(Image image, ImageVariant imageVariant, String tmpPath, @Nullable Observer observer) throws Exception {
        File imageTmp = new File(tmpPath + UrlBuilder.imageCachedName(image)).getCanonicalFile();
        if (imageTmp.isFile()) {
            if (imageTmp.canRead()) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("reading file from disk");
                }
                return ImageIO.read(imageTmp);
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("File cannot be read");
                }
            }
        } else {
            Thread cacheImage = new Thread(new CacheImage(image, imageVariant, tmpPath, observer));
            cacheImage.start();
        }
        return ImageIO.read(MarvelRequest.class.getResource("loading.png"));
    }

    public static void startLoading(String url) {
        for (RequestListener requestListener : requestListeners) {
            requestListener.startLoading(url);
        }
    }

    public static void endRequest(String url) {
        for (RequestListener requestListener : requestListeners) {
            requestListener.endLoading(url);
        }
    }

    public static Set<RequestListener> getRequestListeners() {
        return requestListeners;
    }

    /**
     * This method takes the required partialUrl and makes a request from the entire url and returns the response.
     *
     * @param partialUrl the part of the url that will be concatenate with the keys and the timestamp
     * @param query      query can be null
     * @return String. If the rateLimit is reached this method will return the specified string <i>requestCanceled</i>
     * @throws IOException on request execution
     */
    public String getData(String partialUrl, String query) throws IOException {
        if (Authentication.getNumberOfRequest() < Authentication.getRateLimit()) {
            for (RequestListener requestListener : requestListeners) {
                requestListener.startLoading(partialUrl);
            }

            Request request = new Request.Builder()
                    .url(appendBaseUrl(partialUrl, query))
                    .build();
            try (Response response = client.newCall(request).execute()) {
                Authentication.setNumberOfRequest(Authentication.getNumberOfRequest() + 1);

                for (RequestListener requestListener : requestListeners) {
                    requestListener.endLoading(partialUrl);
                }
                ResponseBody body = response.body();
                return body == null ? null : body.string();
            }
        } else {
            return requestCanceled;
        }
    }

    public void asyncGetData(String partialUrl, String query, Callback callback) {
        if (Authentication.getNumberOfRequest() < Authentication.getRateLimit()) {
            for (RequestListener requestListener : requestListeners) {
                requestListener.startLoading(partialUrl);
            }
            Request request = new Request.Builder()
                    .url(appendBaseUrl(partialUrl, query))
                    .build();
            client.newCall(request).enqueue(callback);
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
