package fr.tse.fise2.heapoverflow.marvelapi;

import fr.tse.fise2.heapoverflow.database.CacheUrlsRow;
import fr.tse.fise2.heapoverflow.main.Controller;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

import static java.lang.System.nanoTime;

/**
 * Intercepts all Marvel request to perfom some tasks like appending hash, load from cache.
 *
 * @author Darios DJIMADO
 */
public class MarvelRequestInterceptor implements Interceptor {
    // logging
    private static final Logger logger = Logger.getLogger(MarvelRequestInterceptor.class);
    private final Controller controller;

    /**
     * Constructor of the interceptor
     */
    MarvelRequestInterceptor(Controller controller) {
        this.controller = controller;
    }


    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        Request newRequest = request;

        // log sending request info
        Long t1 = nanoTime();
        Controller.getLoggerObserver().onInfo(logger, String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        // try to find the url in cache


        try {
            CacheUrlsRow cacheUrlsRow = this.controller.getCacheUrlsTable().findCompleteUrl(request.url().toString());

            if (cacheUrlsRow != null && cacheUrlsRow.getCompleteUrl() != null) {
                // if url is found
                newRequest = request.newBuilder()
                        .url(cacheUrlsRow.getCompleteUrl())
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Controller.getLoggerObserver().onInfo(logger, newRequest.url() + " load from cache");

            } else {
                // if the url is not found at runtime we add it to cacheUrlsTable dynamically
                this.controller.getCacheUrlsTable().insertUrls(request.url().toString(),
                        UrlBuilder.appendHash(request.url().toString()));
                newRequest = request.newBuilder()
                        .url(UrlBuilder.appendHash(request.url().toString()))
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        Response response = chain.proceed(newRequest).newBuilder()
                .header("Cache-Control", "max-age=86400")
                .build();

        if(response.code() == 504){
            newRequest = request.newBuilder()
                    .url(UrlBuilder.appendHash(request.url().toString()))
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();

            response = chain.proceed(newRequest).newBuilder()
                    .header("Cache-Control", "max-age=86400")
                    .build();
        }

        // log end request info
        Long t2 = nanoTime();
        Controller.getLoggerObserver().onInfo(logger, String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        return response;
    }
}
