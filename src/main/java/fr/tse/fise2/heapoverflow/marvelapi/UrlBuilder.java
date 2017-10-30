package fr.tse.fise2.heapoverflow.marvelapi;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.net.URL;

public class UrlBuilder {

    /**
     *
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
        if(partialUrl.contains("?")){
            return baseUrl + partialUrl + "&apikey=" + publicKey + "&ts=" + timestamp + "&hash=" + hash;
        } else{
            return baseUrl + partialUrl + "?apikey=" + publicKey + "&ts=" + timestamp + "&hash=" + hash;
        }

    }


    // TODO add comment
    public static URL imageUrl(Image image) throws MalformedURLException {
        return new URL(image.getPath()+'.'+image.getExtension());
    }

    // TODO test
    public static void main(String[] args) {
        try {
            System.out.println(UrlBuilder.apiPlainDataUrl("comics"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
