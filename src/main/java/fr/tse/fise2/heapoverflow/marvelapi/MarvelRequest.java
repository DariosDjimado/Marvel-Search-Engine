package fr.tse.fise2.heapoverflow.marvelapi;

import com.google.gson.Gson;
import fr.tse.fise2.heapoverflow.events.RequestListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

public class MarvelRequest extends UrlBuilder {
    // string that is returned when the rateLimit is reached
    public static String requestCanceled = "More than" + Authentication.getRateLimit() + "request";
    private static Set<RequestListener> requestListeners = new HashSet<>();
    private OkHttpClient client = new OkHttpClient();

    /**
     * This method consists exclusively to convert a json string to CharacterDataWrapper
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
     * This method consists exclusively to convert a json string to ComicDataWrapper.
     *
     * @param json the string that we need to deserialize
     * @return ComicDataWrapper object or null if the rateLimit is reached
     */
    public static ComicDataWrapper deserializeComics(String json) {
        if (json.equals(requestCanceled)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(json, ComicDataWrapper.class);
        }
    }

    /**
     * This method consists exclusively to convert a json string to ComicDataWrapper.
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
     *
     */
    public static SeriesDataWrapper deserializeSeries(String json) {
        if (json.equals(requestCanceled)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(json, SeriesDataWrapper.class);
        }
    }

    public static BufferedImage getImage(Image image, ImageVariant imageVariant) throws IOException {
        for (RequestListener requestListener : requestListeners) {
            requestListener.startLoading(image.getPath());
        }
        BufferedImage bufferedReader = ImageIO.read(imageUrl(image, imageVariant));
        for (RequestListener requestListener : requestListeners) {
            requestListener.endLoading(image.getPath());
        }
        return bufferedReader;
    }

    /**
     * This method takes the required partialUrl and makes a request from the entire url and returns the response.
     *
     * @param partialUrl the part of the url that will be concatenate with the keys and the timestamp
     * @return String. If the rateLimit is reached this method will return the specified string <i>requestCanceled</i>
     * @throws IOException              from okHttp3
     * @throws NoSuchAlgorithmException from MD5
     */
    public String getData(String partialUrl) throws IOException, NoSuchAlgorithmException {
        if (Authentication.getNumberOfRequest() < Authentication.getRateLimit()) {
            for (RequestListener requestListener : requestListeners) {
                requestListener.startLoading(partialUrl);
            }

            Request request = new Request.Builder()
                    .url(apiPlainDataUrl(partialUrl))
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

    public void addRequestListener(RequestListener listener) {
        requestListeners.add(listener);
    }

    public void removeRequestListener(RequestListener listener) {
        requestListeners.remove(listener);
    }


}
