package fr.tse.fise2.heapoverflow.marvelapi;

import fr.tse.fise2.heapoverflow.interfaces.LoggerObserver;
import fr.tse.fise2.heapoverflow.main.Controller;
import okhttp3.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;

import static java.lang.System.nanoTime;

/**
 * Intercepts all Marvel request to perfom some tasks like appending hash, load from cache.
 *
 * @author Darios DJIMADO
 */
public class MarvelRequestInterceptor implements Interceptor {
    // logging
    private static final Logger logger = Logger.getLogger(MarvelRequestInterceptor.class);
    private final LoggerObserver loggerObserver;

    private final Cache cache;

    /**
     * Constructor of the interceptor
     *
     * @param cache where to search url
     */
    MarvelRequestInterceptor(Cache cache) {
        this.loggerObserver = Controller.getLoggerObserver();
        this.cache = cache;
    }


    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        Request newRequest;

        // log sending request info
        Long t1 = nanoTime();
        this.loggerObserver.onInfo(logger, String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        // try to find the url in cache
        final Iterator<String> urlsInCache = this.cache.urls();
        boolean found = false;
        String oldUrl = null;
        while (!found && urlsInCache.hasNext()) {
            String currentUrl = urlsInCache.next();
            if (currentUrl.startsWith(request.url().toString())) {
                oldUrl = currentUrl;
                this.loggerObserver.onInfo(logger, currentUrl + " found in cache");
                found = true;
            }
        }

        // if url is found
        if (found) {
            newRequest = request.newBuilder()
                    .url(oldUrl)
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            this.loggerObserver.onInfo(logger, newRequest.url() + " load from cache");

        } else {
            newRequest = request.newBuilder()
                    .url(UrlBuilder.appendHash(request.url().toString()))
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        }


        Response response = chain.proceed(newRequest).newBuilder()
                .header("Cache-Control", "max-age=120")
                .build();

        // log end request info
        Long t2 = nanoTime();
        this.loggerObserver.onInfo(logger, String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}
