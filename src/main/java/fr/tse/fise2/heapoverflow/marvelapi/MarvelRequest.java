package fr.tse.fise2.heapoverflow.marvelapi;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class MarvelRequest extends UrlBuilder {
    // string that is returned when the rateLimit is reached
    public static String requestCanceled = "More than"+ Authentication.getRateLimit() + "request";
    private OkHttpClient client = new OkHttpClient();

    /**
     * This method consists exclusively to convert a json string to CharacterDataWrapper
     * @param json the string that we need to deserialize
     * @return CharacterDataWrapper object or null if the rateLimit is reached
     */
    private static CharacterDataWrapper deserializeCharacters(String json) {
        if (json.equals(requestCanceled)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(json, CharacterDataWrapper.class);
        }
    }

    /**
     * This method consists exclusively to convert a json string to ComicDataWrapper.
     * @param json the string that we need to deserialize
     * @return ComicDataWrapper object or null if the rateLimit is reached
     */
    private static ComicDataWrapper deserializeComics(String json) {
        if (json.equals(requestCanceled)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(json, ComicDataWrapper.class);
        }
    }

    /**
     * This method takes the required partialUrl and makes a request from the entire url and returns the response.
     * @param partialUrl the part of the url that will be concatenate with the keys and the timestamp
     * @return String. If the rateLimit is reached this method will return the specified string <i>requestCanceled</i>
     * @throws IOException from okHttp3
     * @throws NoSuchAlgorithmException from MD5
     */
    String getData(String partialUrl) throws IOException, NoSuchAlgorithmException {
        if (Authentication.getNumberOfRequest() < Authentication.getRateLimit()) {
            Request request = new Request.Builder()
                    .url(getUrl(partialUrl))
                    .build();
            try (Response response = client.newCall(request).execute()) {
                Authentication.setNumberOfRequest(Authentication.getNumberOfRequest() + 1);
                return response.body().string();
            }
        } else {
            return requestCanceled;
        }
    }

    // TODO test
    public static void main(String[] args) {
        MarvelRequest requestExample = new MarvelRequest();

        try {
            String response = requestExample.getData("characters");
            System.out.println(deserializeCharacters(response).getData().getResults()[0]);

            String response2 = requestExample.getData("comics");
            System.out.println(deserializeCharacters(response2).getData().getResults()[0]);

            System.out.println(Authentication.getNumberOfRequest());

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
