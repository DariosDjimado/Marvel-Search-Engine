package fr.tse.fise2.heapoverflow.marvelapi;

import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Handles url manipulation
 *
 * @author Darios DJIMADO
 */
public class UrlBuilder {

    /**
     * Appends hash, timestamp  and keys to the url
     *
     * @param url Original url
     * @return String
     */
    static String appendHash(String url) {

        // Queries
        String timestamp = Long.toString(System.currentTimeMillis());
        String privateKey = Authentication.getPrivateKey();
        String publicKey = Authentication.getPublicKey();

        // hash
        String hash = DigestUtils.md5Hex(timestamp + privateKey + publicKey);

        // return the url
        if (url.contains("?")) {
            return url + "&apikey=" + publicKey + "&ts=" + timestamp + "&hash=" + hash;
        } else {
            return url + "?apikey=" + publicKey + "&ts=" + timestamp + "&hash=" + hash;
        }

    }

    /**
     * Appends base url to partial url
     *
     * @param partialUrl part of url that will be added
     * @param query      query can be null
     * @return url with baseUrl appended
     */
    static URL appendBaseUrl(String partialUrl, @Nullable String query) {
        URL url = null;
        try {
            URI uri = new URI("https", null, "gateway.marvel.com", 443, "/v1/public/" + partialUrl, query, null);
            url = uri.toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Builds Url from Marvel Image and Marvel Image Variant
     *
     * @param image        Marvel Image format
     * @param imageVariant Marvel Image Variant
     * @return Url
     * @throws MalformedURLException if Url cannot be made
     */
    public static URL imageUrl(Image image, ImageVariant imageVariant) throws MalformedURLException {

        switch (imageVariant) {
            case PORTRAIT_SMALL:
                return new URL(image.getPath() + '/' + ImageVariant.PORTRAIT_SMALL.name().toLowerCase() + '.' + image.getExtension());
            case PORTRAIT_MEDIUM:
                return new URL(image.getPath() + '/' + ImageVariant.PORTRAIT_MEDIUM.name().toLowerCase() + '.' + image.getExtension());
            case PORTRAIT_XLARGE:
                return new URL(image.getPath() + '/' + ImageVariant.PORTRAIT_XLARGE.name().toLowerCase() + '.' + image.getExtension());
            case PORTRAIT_FANTASTIC:
                return new URL(image.getPath() + '/' + ImageVariant.PORTRAIT_FANTASTIC.name().toLowerCase() + '.' + image.getExtension());
            case PORTRAIT_UNCANNY:
                return new URL(image.getPath() + '/' + ImageVariant.PORTRAIT_UNCANNY.name().toLowerCase() + '.' + image.getExtension());
            case PORTRAIT_INCREDIBLE:
                return new URL(image.getPath() + '/' + ImageVariant.PORTRAIT_INCREDIBLE.name().toLowerCase() + '.' + image.getExtension());
            case STANDARD_SMALL:
                return new URL(image.getPath() + '/' + ImageVariant.STANDARD_SMALL.name().toLowerCase() + '.' + image.getExtension());
            case STANDARD_MEDIUM:
                return new URL(image.getPath() + '/' + ImageVariant.STANDARD_MEDIUM.name().toLowerCase() + '.' + image.getExtension());
            case STANDARD_XLARGE:
                return new URL(image.getPath() + '/' + ImageVariant.STANDARD_XLARGE.name().toLowerCase() + '.' + image.getExtension());
            case STANDARD_FANTASTIC:
                return new URL(image.getPath() + '/' + ImageVariant.STANDARD_FANTASTIC.name().toLowerCase() + '.' + image.getExtension());
            case STANDARD_UNCANNY:
                return new URL(image.getPath() + '/' + ImageVariant.STANDARD_UNCANNY.name().toLowerCase() + '.' + image.getExtension());
            case STANDARD_INCREDIBLE:
                return new URL(image.getPath() + '/' + ImageVariant.STANDARD_INCREDIBLE.name().toLowerCase() + '.' + image.getExtension());
            case LANDSCAPE_SMALL:
                return new URL(image.getPath() + '/' + ImageVariant.LANDSCAPE_SMALL.name().toLowerCase() + '.' + image.getExtension());
            case LANDSCAPE_MEDIUM:
                return new URL(image.getPath() + '/' + ImageVariant.LANDSCAPE_MEDIUM.name().toLowerCase() + '.' + image.getExtension());
            case LANDSCAPE_XLARGE:
                return new URL(image.getPath() + '/' + ImageVariant.LANDSCAPE_XLARGE.name().toLowerCase() + '.' + image.getExtension());
            case LANDSCAPE_FANTASTIC:
                return new URL(image.getPath() + '/' + ImageVariant.LANDSCAPE_FANTASTIC.name().toLowerCase() + '.' + image.getExtension());
            case LANDSCAPE_UNCANNY:
                return new URL(image.getPath() + '/' + ImageVariant.LANDSCAPE_UNCANNY.name().toLowerCase() + '.' + image.getExtension());
            case LANDSCAPE_INCREDIBLE:
                return new URL(image.getPath() + '/' + ImageVariant.LANDSCAPE_INCREDIBLE.name().toLowerCase() + '.' + image.getExtension());
            default:
                return new URL(image.getPath() + '.' + image.getExtension());
        }


    }

    /**
     * Formats image's path
     *
     * @param image Marvel Image
     * @return the name given to the file
     */
    public static String imageCachedName(Image image) {
        return image.getPath().replaceAll("http://i.annihil.us/u/prod/marvel/i/mg/", "")
                .replaceAll("/", "_") + "." + image.getExtension();
    }

    /**
     * Marvel Image Variant
     */
    public enum ImageVariant {
        PORTRAIT_SMALL,
        PORTRAIT_MEDIUM,
        PORTRAIT_XLARGE,
        PORTRAIT_FANTASTIC,
        PORTRAIT_UNCANNY,
        PORTRAIT_INCREDIBLE,
        STANDARD_SMALL,
        STANDARD_MEDIUM,
        STANDARD_XLARGE,
        STANDARD_FANTASTIC,
        STANDARD_UNCANNY,
        STANDARD_INCREDIBLE,
        LANDSCAPE_SMALL,
        LANDSCAPE_MEDIUM,
        LANDSCAPE_XLARGE,
        LANDSCAPE_FANTASTIC,
        LANDSCAPE_UNCANNY,
        LANDSCAPE_INCREDIBLE
    }
}
