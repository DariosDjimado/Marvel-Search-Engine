package fr.tse.fise2.heapoverflow.marvelapi;

import org.apache.commons.validator.routines.UrlValidator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.TestCase.*;

/**
 * @author Darios DJIMADO
 */
public class UrlBuilderTest {

    private static UrlValidator urlValidator;
    private static String partialUrl;
    private static String query;
    private static String baseUrl;

    @BeforeClass
    public static void setupConfig() {
        String[] schemes = new String[]{"http", "https"};
        urlValidator = new UrlValidator(schemes);
        partialUrl = "comics";
        query = "titleStartsWith=spider-man&orderBy=title&limit=50&offset=2";
        baseUrl = "https://gateway.marvel.com:443/v1/public/";
    }

    @Test
    public void appendHash() {
        String url = baseUrl + partialUrl + "?" + query;
        String completeUrl = UrlBuilder.appendHash(url);
        assertTrue(urlValidator.isValid(completeUrl));
        assertTrue(completeUrl.contains("apikey"));
        assertTrue(completeUrl.contains("&ts"));
        assertTrue(completeUrl.contains("&hash"));
    }

    @Test
    public void appendBaseUrl() {
        // well formed url
        URL urlWithBaseUrl = UrlBuilder.appendBaseUrl(partialUrl, query);
        assertTrue(urlValidator.isValid(urlWithBaseUrl.toString()));
        assertEquals(urlWithBaseUrl.toString(), baseUrl + partialUrl + "?" + query);

        // query has space
        String queryWithTwoSpace = String.valueOf(new StringBuilder(query).insert(query.split("=")[0].length() + 2, "  "));
        URL urlWithBaseUrlWithSpace = UrlBuilder.appendBaseUrl(partialUrl, queryWithTwoSpace);
        assertTrue(urlValidator.isValid(urlWithBaseUrlWithSpace.toString()));
        assertEquals(urlWithBaseUrlWithSpace.toString(), baseUrl + partialUrl + "?" + new StringBuilder(query).insert(query.split("=")[0].length() + 2, "%20%20"));
    }

    @Test
    public void imageUrl() {
        String path = "http://i.annihil.us/u/prod/marvel/i/mg/9/f0/589deb5c4a277";
        String extension = "jpg";
        Image image = new Image();
        image.setPath(path);
        image.setExtension(extension);
        URL url = null;
        try {
            url = UrlBuilder.imageUrl(image, UrlBuilder.ImageVariant.PORTRAIT_FANTASTIC);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assertNotNull(url);
        assertEquals(url.toString(), "http://i.annihil.us/u/prod/marvel/i/mg/9/f0/589deb5c4a277/portrait_fantastic.jpg");
    }
}