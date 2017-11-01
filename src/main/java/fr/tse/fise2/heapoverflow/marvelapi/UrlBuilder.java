package fr.tse.fise2.heapoverflow.marvelapi;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UrlBuilder {

    /**
     * @param partialUrl the part of the url that will be added.
     * @return String
     * @throws NoSuchAlgorithmException from MessageDigest.
     */
    public static String apiPlainDataUrl(String partialUrl) throws NoSuchAlgorithmException {

        // Queries
        String timestamp = Long.toString(System.currentTimeMillis());
        String privateKey = Authentication.getPrivateKey();
        String publicKey = Authentication.getPublicKey();
        String toHash = timestamp + privateKey + publicKey;
        String baseUrl = "https://gateway.marvel.com:443/v1/public/";

        // get an instance of MD5, reset it and update the new string
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(toHash.getBytes());
        byte[] digest = messageDigest.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        StringBuilder hash = new StringBuilder(bigInt.toString(16));

        // add 0 while the length of the string is less than 32
        while (hash.length() < 32) {
            hash.insert(0, "0");
        }

        // return the url
        if (partialUrl.contains("?")) {
            return baseUrl + partialUrl + "&apikey=" + publicKey + "&ts=" + timestamp + "&hash=" + hash;
        } else {
            return baseUrl + partialUrl + "?apikey=" + publicKey + "&ts=" + timestamp + "&hash=" + hash;
        }

    }

    // TODO add comment
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

    // TODO test
    public static void main(String[] args) {
        try {
            System.out.println(UrlBuilder.apiPlainDataUrl("comics"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

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
