package fr.tse.fise2.heapoverflow.marvelapi;

import fr.tse.fise2.heapoverflow.database.CacheUrlsRow;
import fr.tse.fise2.heapoverflow.database.CacheUrlsTable;
import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

import static java.lang.System.nanoTime;

/**
 * Intercepts all Marvel request to perfom some tasks like appending hash, load from cache.
 *
 * @author Darios DJIMADO
 */
class MarvelRequestInterceptor implements Interceptor {
    // logging
    private static final Logger LOGGER = LoggerFactory.getLogger(MarvelRequestInterceptor.class);

    /**
     * Constructor of the interceptor
     */
    MarvelRequestInterceptor() {

    }


    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        Request newRequest = request;

        // log sending request info
        Long t1 = nanoTime();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
        }

        // try to find the url in cache


        try {
            CacheUrlsRow cacheUrlsRow = CacheUrlsTable.findCompleteUrl(request.url().toString());

            if (cacheUrlsRow != null && cacheUrlsRow.getCompleteUrl() != null) {
                // if url is found
                newRequest = request.newBuilder()
                        .url(cacheUrlsRow.getCompleteUrl())
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(newRequest.url() + " load from cache");
                }

            } else {
                // if the url is not found at runtime we add it to cacheUrlsTable dynamically
                CacheUrlsTable.insertUrls(request.url().toString(),
                        UrlBuilder.appendHash(request.url().toString()));
                newRequest = request.newBuilder()
                        .url(UrlBuilder.appendHash(request.url().toString()))
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();

            }
        } catch (SQLException e) {
            AppErrorHandler.onError(e);
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage(), e);
            }
        }


        Response response = chain.proceed(newRequest).newBuilder()
                .header("Cache-Control", "max-age=86400")
                .build();

        if (response.code() == 504) {
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
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        }

        return response;
    }
}
